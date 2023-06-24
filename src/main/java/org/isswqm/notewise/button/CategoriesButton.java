package org.isswqm.notewise.button;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class CategoriesButton implements Button{
    @Override
    public SendMessage execute(String chatId, String text) {
        return new SendMessage(chatId, "Кнопка Categories еще не добавлена");
    }
}
