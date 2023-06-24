package org.isswqm.notewise.helper;

import org.isswqm.notewise.command.NoteCommand;
import org.isswqm.notewise.config.NoteConfig;
import java.util.ArrayList;

public class NoteHelper {

    public void note(ArrayList<String> noteInfoList){
        try {
            NoteCommand noteCommand = new NoteCommand();

            Long chatId = Long.valueOf(noteInfoList.get(0));

            String message = noteInfoList.get(1);

            NoteConfig noteConfig = new NoteConfig(chatId, message);

            noteCommand.saveNote(noteConfig);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
