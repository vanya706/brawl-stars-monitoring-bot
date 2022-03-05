package com.ivan.mostovyi.brawlstarsmonitoringbot.bot.handler;


import com.ivan.mostovyi.brawlstarsmonitoringbot.bot.Command;
import com.ivan.mostovyi.brawlstarsmonitoringbot.domain.Settings;
import com.ivan.mostovyi.brawlstarsmonitoringbot.util.TelegramUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.List;

@Component
public class StartHandler implements Handler {

  @Value("${bot.name}")
  private String botUsername;

  @Override
  public List<PartialBotApiMethod<? extends Serializable>> handle(Settings settings, String message) {
    SendMessage welcomeMessage = TelegramUtil.createMessageTemplate(settings);
    welcomeMessage.setText(String.format(
        "Hola! I'm *%s*%nI am here to help you manage Telegram chat.", botUsername
    ));
    return List.of(welcomeMessage);
  }

  @Override
  public Command operatedBotCommand() {
    return Command.START;
  }

}
