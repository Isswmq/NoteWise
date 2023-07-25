package org.isswqm.notewise.impl.deleteremind;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class WaitingForDeleteRemind implements Command {

    @Override
    public SendMessage execute(String chatId, String text) {
        NoteWise.deleteRemindMap.put("Date", text);
        SendMessage message = new SendMessage(chatId, "Delete the remind? <yes/no>");
        NoteWise.statement = Statements.REMIND_DELETED;
        return message;
    }
}
