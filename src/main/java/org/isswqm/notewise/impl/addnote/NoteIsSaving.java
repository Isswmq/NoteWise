package org.isswqm.notewise.impl.addnote;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.helper.NoteHelper;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class NoteIsSaving implements Command {
    @Override
    public SendMessage execute(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if(text.equalsIgnoreCase("да")){
            NoteHelper helper = new NoteHelper();
            helper.note(NoteWise.noteInfoList);
            message.setText("Заметка сохранена!");
        }else {
            message.setText("Добавление заметки отменено.");
        }
        NoteWise.statement = Statements.WAITING;
        return message;
    }
}

