package com.ivan.mostovyi.brawlstarsmonitoringbot.bot.handler;

import com.ivan.mostovyi.brawlstarsmonitoringbot.bot.Command;
import com.ivan.mostovyi.brawlstarsmonitoringbot.domain.Settings;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.util.List;

public interface Handler {

  List<PartialBotApiMethod<? extends Serializable>> handle(Settings settings, String message);

  Command operatedBotCommand();

}
