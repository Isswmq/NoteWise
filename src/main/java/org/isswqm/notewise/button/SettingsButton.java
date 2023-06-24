package org.isswqm.notewise.button;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class SettingsButton implements Button{
    @Override
    public SendMessage execute(String chatId, String text) {
        return new SendMessage(chatId, "Кнопка Settings еще не добавлена");
    }
}
