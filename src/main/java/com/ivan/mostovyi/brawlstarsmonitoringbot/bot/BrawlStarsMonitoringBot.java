package com.ivan.mostovyi.brawlstarsmonitoringbot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class BrawlStarsMonitoringBot extends TelegramLongPollingBot {

  @Value("${bot.name}")
  private String botName;

  @Value("${bot.token}")
  private String botToken;

  private final UpdateReceiver updateReceiver;

  private final CommandInitializer commandInitializer;

  public BrawlStarsMonitoringBot(UpdateReceiver updateReceiver, CommandInitializer commandInitializer) {
    this.updateReceiver = updateReceiver;
    this.commandInitializer = commandInitializer;
  }


  @PostConstruct
  private void registerCommands() {
    commandInitializer.initializeCommands(this);
  }

  @Override
  public void onUpdateReceived(Update update) {
    log.info(update.toString());
    updateReceiver.handle(update)
        .forEach(response -> {
          if (response instanceof SendMessage) {
            executeWithExceptionCheck((SendMessage) response);
          }
        });
  }

  public void executeWithExceptionCheck(SendMessage sendMessage) {
    try {
      log.info(sendMessage.toString());
      execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error(e.getMessage(), e);
    }
  }

  @Override
  public String getBotUsername() {
    return botName;
  }

  @Override
  public String getBotToken() {
    return botToken;
  }

}
