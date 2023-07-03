package org.isswqm.notewise;

import org.isswqm.notewise.button.*;

import org.isswqm.notewise.command.Remind;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.impl.Command;
import org.isswqm.notewise.impl.addnote.NoteIsSaving;
import org.isswqm.notewise.impl.addnote.WaitingForNoteText;
import org.isswqm.notewise.impl.deletenote.NoteIsDeleted;
import org.isswqm.notewise.impl.deletenote.WaitingForDeleteNote;
import org.isswqm.notewise.impl.editnote.NoteIsEdited;
import org.isswqm.notewise.impl.editnote.WaitingForEditingDateInput;
import org.isswqm.notewise.impl.editnote.WaitingForEditingTextInput;
import org.isswqm.notewise.impl.reminders.RemindIsSaving;
import org.isswqm.notewise.impl.reminders.WaitingForRemindDate;
import org.isswqm.notewise.impl.reminders.WaitingForRemindText;
import org.isswqm.notewise.impl.searchnote.NoteFound;
import org.isswqm.notewise.impl.searchnote.WaitingForSearchNoteDateInput;
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
    public static ArrayList<String> deleteNoteList = new ArrayList<>();
    public static ArrayList<String> editNoteList = new ArrayList<>();
    public static ArrayList<String> searchNoteList = new ArrayList<>();
    ArrayList<String> buttons = new ArrayList<>();
    private final HashMap<String, Button> buttonHashMap = new HashMap<>();
    private final HashMap<Statements, Command> commandHashMap = new HashMap<>();


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
         buttonHashMap.put("Помощь", new HelpButton());
         buttonHashMap.put("Добавить Заметку", new AddNoteButton());
         buttonHashMap.put("Просмотреть Заметки", new ViewNoteButton());
         buttonHashMap.put("Изменить Заметку", new EditNoteButton());
         buttonHashMap.put("Удалить Заметку", new DeleteNoteButton());
         buttonHashMap.put("Найти Заметки", new SearchNoteButton());
         buttonHashMap.put("Добавить Напоминание", new RemindersButton());
         //buttonHashMap.put("Просмотреть Напоминания", new CategoriesButton());
         //buttonHashMap.put("Удалить Напоминание", new SettingsButton());
    }

    public void setupCommands() throws SQLException {
        commandHashMap.put(Statements.WAITING_FOR_REMIND_TEXT_INPUT, new WaitingForRemindText());
        commandHashMap.put(Statements.WAITING_FOR_REMIND_DATE_INPUT, new WaitingForRemindDate());
        commandHashMap.put(Statements.REMIND_IS_SAVING, new RemindIsSaving());
        commandHashMap.put(Statements.WAITING_FOR_NOTE_TEXT_INPUT, new WaitingForNoteText());
        commandHashMap.put(Statements.NOTE_IS_SAVING, new NoteIsSaving());
        commandHashMap.put(Statements.WAITING_FOR_NOTES_VIEW, new ViewNoteCommand());
        commandHashMap.put(Statements.WAITING_FOR_DELETE_NOTE, new WaitingForDeleteNote());
        commandHashMap.put(Statements.NOTE_IS_DELETED, new NoteIsDeleted());
        commandHashMap.put(Statements.WAITING_FOR_EDITING_NOTE_DATE_INPUT, new WaitingForEditingDateInput());
        commandHashMap.put(Statements.WAITING_FOR_EDITING_NOTE_TEXT_INPUT, new WaitingForEditingTextInput());
        commandHashMap.put(Statements.NOTE_IS_EDITED, new NoteIsEdited());
        commandHashMap.put(Statements.WAITING_FOR_SEARCH_NOTE_DATE_INPUT, new WaitingForSearchNoteDateInput());
        commandHashMap.put(Statements.NoteFound, new NoteFound());
    }

    @Override
    public String getBotUsername() {
        return "NoteWise_bot";
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {
    }
}
