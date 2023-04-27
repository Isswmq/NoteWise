package org.isswqm.notewise.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class StickerHandler {
    public static SendDocument sendSadSticker(String chatId) throws TelegramApiException {
        final String stickerId = "CAACAgIAAxkBAAMwZDwgAz3UPAYlnD-XqbBOTspDkAsAAjkEAALpVQUYOLOBVDvue3kvBA";
        InputFile sticker = new InputFile(stickerId);
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId);
        sendDocument.setDocument(sticker);
        return sendDocument;
    }
}
