package org.isswqm.notewise.button;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class DeleteNoteButton implements Button{

    @Override
    public SendMessage execute(String chatId, String text) {
        return new SendMessage(chatId, "кнопка Delete Note еще не добавлена");
    }
}
