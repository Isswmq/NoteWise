package org.isswqm.notewise.config;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.handlers.ReminderHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.SQLException;

public class Statement {

    public static Statements statement = Statements.WAITING;

    public static void checkStatement(Statements statement, String text, String chatId) throws SQLException {
        switch (statement){
            case WAITING:
                System.out.println("is waiting");
                break;
            case WAITING_ENTERING_TEXT:
                ReminderHandler handler = new ReminderHandler();
                handler.remind(chatId, text);
                System.out.println("text is getting");
                Statement.statement = Statements.TEXT_IS_SAVING;
                break
            case TEXT_IS_SAVING:

            default:
                System.out.println("statement not found");
        }
    }
}