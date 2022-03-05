package com.ivan.mostovyi.brawlstarsmonitoringbot.bot;

import com.ivan.mostovyi.brawlstarsmonitoringbot.bot.handler.Handler;
import com.ivan.mostovyi.brawlstarsmonitoringbot.domain.Settings;
import com.ivan.mostovyi.brawlstarsmonitoringbot.repository.SettingsRepository;
import com.ivan.mostovyi.brawlstarsmonitoringbot.util.CommandUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Component
public class UpdateReceiver {

  private final List<Handler> handlers;

  private final SettingsRepository settingsRepository;

  public UpdateReceiver(List<Handler> handlers, SettingsRepository settingsRepository) {
    this.handlers = handlers;
    this.settingsRepository = settingsRepository;
  }

  public List<PartialBotApiMethod<? extends Serializable>> handle(Update update) {
    try {
      if (isMessageWithText(update)) {
        final Message message = update.getMessage();
        final String chatId = message.getChatId().toString();
        final Settings settings = settingsRepository.findById(chatId)
            .orElseGet(() -> settingsRepository.save(new Settings(chatId)));
        return getHandlerByMessage(message).handle(settings, message.getText());
      }
      throw new UnsupportedOperationException();
    } catch (UnsupportedOperationException e) {
      return Collections.emptyList();
    }
  }

  private Handler getHandlerByMessage(Message message) {
    Command command = CommandUtil.getCommand(message.getText());
    return handlers.stream()
        .filter(handler -> handler.operatedBotCommand().equals(command))
        .findAny()
        .orElseThrow(UnsupportedOperationException::new);
  }

  private boolean isMessageWithText(Update update) {
    return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
  }

}
