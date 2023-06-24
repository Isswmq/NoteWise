package org.isswqm.notewise.button;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface Button {

    SendMessage execute(String chatId, String text);
}
