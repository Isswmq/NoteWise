package org.isswqm.notewise;

import org.isswqm.notewise.impl.reminders.RemindChecker;
import org.isswqm.notewise.config.NoteWiseConfig;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.sql.SQLException;

public class NoteWiseRunner {
    public static void main(String[] args) throws TelegramApiException, IOException, SQLException {
        NoteWiseConfig bot = new NoteWiseConfig();

        String botToken = bot.getBotToken();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new NoteWise(new DefaultBotOptions(), botToken));

        RemindChecker checker = new RemindChecker(new NoteWise(new DefaultBotOptions(), botToken));
        Thread thread = new Thread(checker);
        thread.start();
    }
}
