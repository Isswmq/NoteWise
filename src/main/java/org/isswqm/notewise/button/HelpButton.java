package org.isswqm.notewise.button;

import org.isswqm.notewise.command.HelpCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class HelpButton implements Button{

    @Override
    public SendMessage execute(String chatId, String text) {
        return HelpCommand.help(chatId);
    }
}
