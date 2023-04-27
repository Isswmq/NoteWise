package org.isswqm.notewise.view;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class NoteWiseUI {
    public static SendMessage mainMenu(String chatId){
        SendMessage message = new SendMessage(chatId, "");

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
}
