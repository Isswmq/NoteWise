package org.isswqm.notewise;

import org.isswqm.notewise.config.NoteWiseConfig;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
public class NoteWiseRunner {
    public static void main(String[] args) throws TelegramApiException, IOException {

        NoteWiseConfig bot = new NoteWiseConfig();
        String botToken = bot.getBotToken();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new NoteWise(new DefaultBotOptions(), botToken));
    }
}
