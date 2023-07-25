package org.isswqm.notewise;

import org.isswqm.notewise.button.*;

import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.impl.Command;
import org.isswqm.notewise.impl.deleteremind.RemindDeleted;
import org.isswqm.notewise.impl.deleteremind.WaitingForDeleteRemind;
import org.isswqm.notewise.impl.viewremind.ViewRemindCommand;
import org.isswqm.notewise.impl.addnote.NoteSaving;
import org.isswqm.notewise.impl.addnote.WaitingForNoteTextInput;
import org.isswqm.notewise.impl.deletenote.NoteIsDeleted;
import org.isswqm.notewise.impl.deletenote.WaitingForDeleteNote;
import org.isswqm.notewise.impl.editnote.NoteIsEdited;
import org.isswqm.notewise.impl.editnote.WaitingForEditingDateInput;
import org.isswqm.notewise.impl.editnote.WaitingForEditingTextInput;
import org.isswqm.notewise.impl.reminders.RemindSaving;
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
        setupButtons();
        setupCommands();
    }

    public static Statements statement = Statements.WAITING;
    public static HashMap<String, String> reminderInfoMap = new HashMap<>();
    public static HashMap<String, String> noteInfoMap = new HashMap<>();
    public static HashMap<String, String> deleteNoteMap = new HashMap<>();
    public static HashMap<String, String> editNoteMap = new HashMap<>();
    public static HashMap<String, String> searchNoteMap = new HashMap<>();
    public static HashMap<String, String> deleteRemindMap = new HashMap<>();
    private final ArrayList<String> buttons = new ArrayList<>();
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
         buttonHashMap.put("Help", new HelpButton());
         buttonHashMap.put("Add Note", new AddNoteButton());
         buttonHashMap.put("View Notes", new ViewNoteButton());
         buttonHashMap.put("Edit Note", new EditNoteButton());
         buttonHashMap.put("Delete Note", new DeleteNoteButton());
         buttonHashMap.put("Find Notes", new SearchNoteButton());
         buttonHashMap.put("Add Reminder", new RemindersButton());
         buttonHashMap.put("View Reminders", new ViewRemindButton());
         buttonHashMap.put("Delete Reminder", new DeleteRemindButton());
    }

    public void setupCommands() throws SQLException {
        commandHashMap.put(Statements.WAITING_FOR_ADD_REMIND_TEXT_INPUT, new WaitingForRemindText());
        commandHashMap.put(Statements.WAITING_FOR_ADD_REMIND_DATE_INPUT, new WaitingForRemindDate());
        commandHashMap.put(Statements.REMIND_SAVING, new RemindSaving());
        commandHashMap.put(Statements.WAITING_FOR_NOTE_TEXT_INPUT, new WaitingForNoteTextInput());
        commandHashMap.put(Statements.NOTE_SAVING, new NoteSaving());
        commandHashMap.put(Statements.WAITING_FOR_VIEW_NOTES, new ViewNoteCommand());
        commandHashMap.put(Statements.WAITING_FOR_DELETE_NOTE, new WaitingForDeleteNote());
        commandHashMap.put(Statements.NOTE_DELETED, new NoteIsDeleted());
        commandHashMap.put(Statements.WAITING_FOR_EDITING_NOTE_DATE_INPUT, new WaitingForEditingDateInput());
        commandHashMap.put(Statements.WAITING_FOR_EDITING_NOTE_TEXT_INPUT, new WaitingForEditingTextInput());
        commandHashMap.put(Statements.NOTE_EDITED, new NoteIsEdited());
        commandHashMap.put(Statements.WAITING_FOR_SEARCH_NOTE_DATE_INPUT, new WaitingForSearchNoteDateInput());
        commandHashMap.put(Statements.NOTE_FOUND, new NoteFound());
        commandHashMap.put(Statements.WAITING_FOR_VIEW_REMIND, new ViewRemindCommand());
        commandHashMap.put(Statements.WAITING_FOR_DELETE_REMIND, new WaitingForDeleteRemind());
        commandHashMap.put(Statements.REMIND_DELETED, new RemindDeleted());
    }

    @Override
    public String getBotUsername() {
        return "NoteWise_bot";
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {
    }
}
