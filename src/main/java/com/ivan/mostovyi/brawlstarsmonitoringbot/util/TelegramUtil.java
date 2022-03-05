package com.ivan.mostovyi.brawlstarsmonitoringbot.util;

import com.ivan.mostovyi.brawlstarsmonitoringbot.domain.Settings;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class TelegramUtil {

  public static SendMessage createMessageTemplate(Settings settings) {
    return new SendMessage() {{
      setChatId(settings.getChatId());
      enableMarkdown(true);
    }};
  }

}
