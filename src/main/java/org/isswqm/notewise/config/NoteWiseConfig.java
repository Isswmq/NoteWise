package org.isswqm.notewise.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class NoteWiseConfig {
    private static final String CONFIG_PATH = "src/main/resources/config.properties";
    private final String botToken;

    public NoteWiseConfig() throws IOException {
        Properties properties = new Properties();
        FileInputStream inputStream = new FileInputStream(CONFIG_PATH);
        properties.load(inputStream);
        this.botToken = properties.getProperty("bot_token");
        inputStream.close();
    }
    public String getBotToken() {
        return this.botToken;
    }
}
