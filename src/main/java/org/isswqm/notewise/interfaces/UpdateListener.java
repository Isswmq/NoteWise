package org.isswqm.notewise.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateListener {
    void listenUpdate(Update update);
}
