package org.isswqm.notewise;

import org.isswqm.notewise.command.HelpCommand;

import org.isswqm.notewise.config.Statement;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.view.NoteWiseUI;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteWise extends DefaultAbsSender implements LongPollingBot {
    protected NoteWise(DefaultBotOptions options, String botToken){
        super(options, botToken);
    }

    List<Statements> statementsList = List.of(Statements.REMIND_IS_SAVING);

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText() && !update.getMessage().getText().isEmpty()) {
            ArrayList<String> buttons = new ArrayList<>();
            String chatId = update.getMessage().getChatId().toString();
            String text = update.getMessage().getText();

            try {
                Statement.checkStatement(Statement.statement,text,chatId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            SendMessage mainMenu = NoteWiseUI.createButtons(chatId, buttons);
            if(Statement.statement.equals(Statements.WAITING)){
                if(buttons.contains(text)){
                    try {
                        checkButton(chatId, text, buttons);
                    } catch (TelegramApiException | SQLException e) {
                        throw new RuntimeException(e);
                    }

                }else {
                    try {
                        execute(mainMenu);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            }

            if(statementsList.contains(Statement.statement)){
                try {
                    sendMessage(Statement.statement, chatId);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
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
                Statement.statement = Statements.WAITING_FOR_REMIND_TEXT_INPUT;
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

    public void sendMessage(Statements statement, String chatId) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        switch(statement){
            case REMIND_IS_SAVING -> message.setText("Заметка сохранена");
        }
        if(!message.getText().isEmpty()){
            Statement.statement = Statements.WAITING;
            execute(message);
        }
    }

    @Override
    public String getBotUsername() {
        return "NoteWise_bot";
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {
    }
}
