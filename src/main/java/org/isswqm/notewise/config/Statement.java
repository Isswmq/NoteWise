package org.isswqm.notewise.config;

import org.isswqm.notewise.handlers.ReminderHandler;

import java.sql.SQLException;

public class Statement {

    public static Statements statement = Statements.WAITING;

    public static void checkStatement(Statements statement, String text, String chatId) throws SQLException {
        switch (statement){
            case WAITING:
                System.out.println("is waiting");
                break;
            case WAITING_FOR_REMIND_TEXT_INPUT:
                ReminderHandler handler = new ReminderHandler();
                handler.remind(chatId, text);
                System.out.println("text is getting");
                Statement.statement = Statements.REMIND_IS_SAVING;
                break;
            default:
                System.out.println("statement not found");
        }
    }
}