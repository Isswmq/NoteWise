package org.isswqm.notewise.button;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class HelpButton implements Button{

    @Override
    public SendMessage execute(String chatId, String text) {
        String errorMessage = "Hello! If you have any questions or issues regarding the use of our bot," +
                "please contact our support team. " +
                "You can reach us via email at isswmqwork@gmail.com. " +
                "We will strive to respond to all your inquiries as soon as possible. " +
                "Thank you for choosing our bot!";
        return new SendMessage(chatId, errorMessage);
    }
}
