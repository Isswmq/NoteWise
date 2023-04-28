package org.isswqm.notewise;

import org.isswqm.notewise.handlers.HelpHandler;
import org.isswqm.notewise.handlers.StickerHandler;
import org.isswqm.notewise.view.NoteWiseUI;
import org.telegram.telegrambots.bots.DefaultAbsSender;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.util.WebhookUtils;

public class NoteWise extends DefaultAbsSender implements LongPollingBot {

    final String stickerId = "CAACAgIAAxkBAAMwZDwgAz3UPAYlnD-XqbBOTspDkAsAAjkEAALpVQUYOLOBVDvue3kvBA";


    protected NoteWise(DefaultBotOptions options, String botToken) {
        super(options, botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if(update.hasMessage() && update.getMessage().hasText() && !update.getMessage().getText().isEmpty()){
                String chatId = String.valueOf(update.getMessage().getChatId());
                SendMessage options = NoteWiseUI.options(chatId);
                SendMessage mainMenu = NoteWiseUI.mainMenu(chatId);

                try {
                    execute(mainMenu);
                    execute(options);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }

                if (update.hasCallbackQuery()) {
                    CallbackQuery callbackQuery = update.getCallbackQuery();
                    String data = callbackQuery.getData().toLowerCase();
                    System.out.println(data);
                    System.out.println(data.equals("help"));
                    if (data.equals("help")) {
                        System.out.println("data equals help");
                        try {
                            SendMessage help = HelpHandler.help(chatId);
                            execute(help);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {
        WebhookUtils.clearWebhook(this);
    }

    @Override
    public String getBotUsername() {
        return "NoteWise_bot";
    }

    @Override
    public void onClosing() {
        exe.shutdown();
    }
}
