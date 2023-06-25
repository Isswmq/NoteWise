package org.isswqm.notewise;

import org.isswqm.notewise.button.*;

import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.impl.Command;
import org.isswqm.notewise.impl.addnote.NoteIsSaving;
import org.isswqm.notewise.impl.addnote.WaitingForNoteText;
import org.isswqm.notewise.impl.remind.RemindIsSaving;
import org.isswqm.notewise.impl.remind.WaitingForRemindDate;
import org.isswqm.notewise.impl.remind.WaitingForRemindText;
import org.isswqm.notewise.impl.viewnotes.ViewNoteCommand;
import org.isswqm.notewise.view.NoteWiseUI;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class NoteWise extends DefaultAbsSender implements LongPollingBot {
    protected NoteWise(DefaultBotOptions options, String botToken) throws SQLException {
        super(options, botToken);
        setupCommands();
        setupButtons();
    }
    public static Statements statement = Statements.WAITING;
    public static ArrayList<String> reminderInfoList = new ArrayList<>();

    public static ArrayList<String> noteInfoList = new ArrayList<>();
    ArrayList<String> buttons = new ArrayList<>();
    private HashMap<String, Button> buttonHashMap = new HashMap<>();
    private HashMap<Statements, Command> commandHashMap = new HashMap<>();


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText() && !update.getMessage().getText().isEmpty()) {
            String chatId = update.getMessage().getChatId().toString();
            String text = update.getMessage().getText();

            if(!statement.equals(Statements.WAITING)){
                try {
                    checkStatement(text, chatId);
                } catch (SQLException | TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }else {
                if(buttonHashMap.containsKey(text)){
                    try {
                        checkButton(chatId, text);
                    } catch (TelegramApiException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    try {
                        SendMessage mainMenu = NoteWiseUI.createButtons(chatId, buttons);
                        execute(mainMenu);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
    public void checkButton(String chatId, String text) throws TelegramApiException, SQLException {
        try {
            Button button = Optional.ofNullable(buttonHashMap.get(text))
                    .orElseThrow(() -> new IllegalStateException("button not found"));
            execute(button.execute(chatId, text));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void checkStatement(String text, String chatId) throws SQLException, TelegramApiException {
        try {
            Command command = Optional.ofNullable(commandHashMap.get(statement))
                    .orElseThrow(() -> new IllegalStateException("statement not found"));
            execute(command.execute(chatId, text));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setupButtons(){
         buttonHashMap.put("Help", new HelpButton());
         buttonHashMap.put("Add Note", new AddNoteButton());
         buttonHashMap.put("View Notes", new ViewNoteButton());
         buttonHashMap.put("Edit Note", new EditNoteButton());
         buttonHashMap.put("Delete Note", new DeleteNoteButton());
         buttonHashMap.put("Search Note", new SearchNoteButton());
         buttonHashMap.put("Reminders", new RemindersButton());
         buttonHashMap.put("Categories", new CategoriesButton());
         buttonHashMap.put("Settings", new SettingsButton());
    }

    public void setupCommands() throws SQLException {
        commandHashMap.put(Statements.WAITING_FOR_REMIND_TEXT_INPUT, new WaitingForRemindText());
        commandHashMap.put(Statements.WAITING_FOR_REMIND_DATE_INPUT, new WaitingForRemindDate());
        commandHashMap.put(Statements.REMIND_IS_SAVING, new RemindIsSaving());
        commandHashMap.put(Statements.WAITING_FOR_NOTE_TEXT_INPUT, new WaitingForNoteText());
        commandHashMap.put(Statements.NOTE_IS_SAVING, new NoteIsSaving());
        commandHashMap.put(Statements.WAITING_FOR_NOTES_VIEW, new ViewNoteCommand());
    }

    @Override
    public String getBotUsername() {
        return "NoteWise_bot";
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {
    }
}
