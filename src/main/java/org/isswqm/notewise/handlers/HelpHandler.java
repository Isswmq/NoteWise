package org.isswqm.notewise.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class HelpHandler {

    public static SendMessage help(String chatID){
        SendMessage message = new SendMessage();
        message.setChatId(chatID);

        String text = "Здравствуйте! Если у вас возникли вопросы или проблемы с использованием моего бота, " +
                "пожалуйста, свяжитесь с нашей службой поддержки. " +
                "Вы можете написать нам на почту isswmqwork@gmail.com." +
                "Мы постараемся ответить на все ваши вопросы в кратчайшие сроки. " +
                "Спасибо, что выбрали нашего бота!";

        message.setText(text);
        return message;
    }
}
