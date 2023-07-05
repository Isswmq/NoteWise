package org.isswqm.notewise.view;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class NoteWiseUI {
    public static SendMessage createButtons(String chatID, ArrayList<String> buttons) {
        SendMessage message = new SendMessage();
        message.setChatId(chatID);
        message.setText("Select Function");

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
        row.add("Find Notes");
        row.add("Add Reminder");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("View Reminders");
        row.add("Delete Reminder");
        row.add("Help");
        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboardMarkup);

        for (KeyboardRow row1 : keyboardRows) {
            for (KeyboardButton button : row1) {
                buttons.add(button.getText());
            }
        }
        return message;
    }
}
