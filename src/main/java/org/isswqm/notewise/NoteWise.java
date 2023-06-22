package org.isswqm.notewise;

import org.isswqm.notewise.command.HelpCommand;

import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.helper.ReminderHelper;
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

public class NoteWise extends DefaultAbsSender implements LongPollingBot {
    protected NoteWise(DefaultBotOptions options, String botToken){
        super(options, botToken);
    }
    public static Statements statement = Statements.WAITING;

    ArrayList<String> reminderInfoList = new ArrayList<>();
    ArrayList<String> buttons = new ArrayList<>();
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
                if(buttons.contains(text)){
                    try {
                        checkButton(chatId, text, buttons);
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

    public void checkButton(String chatId, String text, ArrayList<String> buttons) throws TelegramApiException, SQLException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        switch (text) {
            case "Help":
                SendMessage help = HelpCommand.help(chatId);
                try {
                    execute(help);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "Add Note" :
                message.setText("кнопка Add Note еще не добавлена ");
                break;
            case "View Notes" :
                message.setText("кнопка View Notes еще не добавлена");
                break;
            case "Edit Note" :
                message.setText("кнопка Edit Note еще не добавлена");
                break;
            case "Delete Note" :
                message.setText("Кнопка Delete Notes еще не добавлена");
                break;
            case "Search Note" :
                message.setText("Кнопка Search Note еще не добавлена");
                break;
            case "Reminders" :
                statement = Statements.WAITING_FOR_REMIND_TEXT_INPUT;
                message.setText("Введите текст заметки");
                break;
            case "Categories" :
                message.setText("Кнопка Categories еще не добавлена");
                break;
            case "Settings" :
                message.setText("Кнопка Settings еще не добавлена");
            default:
                System.out.println("Кнопка не найдена");
        }

        execute(message);
    }
    public void checkStatement(String text, String chatId) throws SQLException, TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        switch (statement){
            case WAITING_FOR_REMIND_TEXT_INPUT:
                if(reminderInfoList.size() == 0){
                    reminderInfoList.add(chatId);
                }

                if (reminderInfoList.size() == 1) {
                    reminderInfoList.add(text);
                }

                message.setText("Введите дату и время напоминания. <2001-01-01 16:30>");
                statement = Statements.WAITING_FOR_REMIND_DATE_INPUT;
                break;
            case WAITING_FOR_REMIND_DATE_INPUT:
                if(reminderInfoList.size() == 2){
                    reminderInfoList.add(text);
                    ReminderHelper handler = new ReminderHelper();
                    handler.remind(reminderInfoList);
                    statement = Statements.REMIND_IS_SAVING;
                    checkStatement(text, chatId);
                }
                break;
            case REMIND_IS_SAVING:
                message.setText("Заметка сохранена");
                reminderInfoList.clear();
                statement = Statements.WAITING;
                break;
            default:
                System.out.println("statement not found");
        }
        execute(message);
    }

    @Override
    public String getBotUsername() {
        return "NoteWise_bot";
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {
    }
}
