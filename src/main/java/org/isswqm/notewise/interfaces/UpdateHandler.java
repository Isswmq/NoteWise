package org.isswqm.notewise.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateHandler {
    void handlerUpdate(Update update);
}
