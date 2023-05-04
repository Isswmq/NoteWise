package org.isswqm.notewise;

import org.isswqm.notewise.handlers.HelpHandler;
import org.isswqm.notewise.handlers.ReminderHandler;
import org.isswqm.notewise.view.NoteWiseUI;
import org.telegram.telegrambots.bots.DefaultAbsSender;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.util.WebhookUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteWise extends DefaultAbsSender implements LongPollingBot {

    private int updateId = 0;
    protected NoteWise(DefaultBotOptions options, String botToken){
        super(options, botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText() && !update.getMessage().getText().isEmpty()) {
            ArrayList<String> buttons = new ArrayList<>();
            String chatId = update.getMessage().getChatId().toString();
            String text = update.getMessage().getText();
            SendMessage mainMenu = NoteWiseUI.mainMenu(chatId, buttons);

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
    }

    public void checkButton(String chatId, String text, ArrayList<String> buttons) throws TelegramApiException, SQLException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        switch (text) {
            case "Help":
                SendMessage help = HelpHandler.help(chatId);
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
                ReminderHandler remind = new ReminderHandler();
                SendMessage note = new SendMessage();
                note.setChatId(chatId);
                note.setText("Введите текст напоминания:");
                execute(note);

                String reminderText = getMessageFromUser(chatId);

                SendMessage date = new SendMessage();
                date.setChatId(chatId);
                date.setText("Введите дату и время напоминания в формате yyyy-MM-dd HH:mm:ss:");
                execute(date);

                String datetimeString = getMessageFromUser(chatId);

                remind.remind(chatId, reminderText, datetimeString);
                message.setText("Напоминание успешно сохранено!");
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

    public String getMessageFromUser(String chatId) throws TelegramApiException {
        GetUpdates request = new GetUpdates();
        request.setLimit(1);
        // Получаем только те обновления, которые были получены после последнего update_id + 1
        request.setOffset(updateId + 1);
        request.setTimeout(30);
        List<Update> updates = execute(request);

        if (updates != null && !updates.isEmpty()) {
            // Обновляем update_id до последнего полученного обновления + 1
            updateId = updates.get(0).getUpdateId() + 1;
            Message message = updates.get(0).getMessage();
            if (message != null && message.hasText() && message.getChatId().toString().equals(chatId)) {
                return message.getText();
            }
        }
        return null;
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {
        WebhookUtils.clearWebhook(this);
    }

    @Override
    public String getBotUsername() {
        return "NoteWise_bot";
    }

    @Override
    public void onClosing() {
        exe.shutdown();
    }
}
