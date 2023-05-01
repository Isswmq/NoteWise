package org.isswqm.notewise.view;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class NoteWiseUI {
    public static SendMessage options(String chatID){
        SendMessage message = new SendMessage(chatID, "Выбери опцию");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();

        button1.setText("send sticker");
        button1.setCallbackData("button1");

        row.add(button1);

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Кнопка 2");
        button2.setCallbackData("button2");
        row.add(button2);

        rows.add(row);

        markup.setKeyboard(rows);
        message.setReplyMarkup(markup);

        return message;
    }

        public static SendMessage mainMenu(String chatID){
            SendMessage message = new SendMessage();
            message.setChatId(chatID);
            message.setText("Hello! Please select an option:");
    
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            keyboardMarkup.setResizeKeyboard(true);
            keyboardMarkup.setOneTimeKeyboard(true);
            List<KeyboardRow> keyboardRows = new ArrayList<>();
    
            KeyboardRow row = new KeyboardRow();

            row.add("Add Note");
            row.add("View Notes");
            row.add("Edit Note");
            keyboardRows.add(row);

            row = new KeyboardRow();
            row.add("Delete Note");
            row.add("Search Note");
            row.add("Reminders");
            keyboardRows.add(row);

            row = new KeyboardRow();
            row.add("Categories");
            row.add("Settings");
            row.add("Help");
            keyboardRows.add(row);
    
            keyboardMarkup.setKeyboard(keyboardRows);
            message.setReplyMarkup(keyboardMarkup);
    
            return message;
        }
}
