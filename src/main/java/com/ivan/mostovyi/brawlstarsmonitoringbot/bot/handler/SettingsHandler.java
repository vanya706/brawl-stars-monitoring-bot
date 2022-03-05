package com.ivan.mostovyi.brawlstarsmonitoringbot.bot.handler;

import com.ivan.mostovyi.brawlstarsmonitoringbot.bot.Command;
import com.ivan.mostovyi.brawlstarsmonitoringbot.domain.Settings;
import com.ivan.mostovyi.brawlstarsmonitoringbot.repository.SettingsRepository;
import com.ivan.mostovyi.brawlstarsmonitoringbot.util.CommandUtil;
import com.ivan.mostovyi.brawlstarsmonitoringbot.util.TelegramUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.List;

@Component
public class SettingsHandler implements Handler {

  private final SettingsRepository settingsRepository;

  public SettingsHandler(SettingsRepository settingsRepository) {
    this.settingsRepository = settingsRepository;
  }


  @Override
  public List<PartialBotApiMethod<? extends Serializable>> handle(Settings settings, String message) {
    List<String> commandArgs = CommandUtil.getCommandArgs(operatedBotCommand(), message);
    if (commandArgs.size() != 1) {
      SendMessage answer = TelegramUtil.createMessageTemplate(settings);
      answer.setText(String.format("Need Club Tag as an argument! Example: %s #clubTag", CommandUtil.getCommandText(Command.SETTINGS)));
      return List.of(answer);
    }
    String clubTag = commandArgs.get(0);
    // todo add or remove "#" from the clubTag
    if (!isValidClubTag(clubTag)) {
      SendMessage answer = TelegramUtil.createMessageTemplate(settings);
      answer.setText(String.format("Club Tag %s is invalid!", clubTag));
      return List.of(answer);
    }
    settings.setClubTag(clubTag);
    settingsRepository.save(settings);
    SendMessage answer = TelegramUtil.createMessageTemplate(settings);
    answer.setText(String.format("Club Tag %s successfully set!", clubTag));
    return List.of(answer);
  }

  private boolean isValidClubTag(String clubTag) {
    // todo add validation
    return true;
  }

  @Override
  public Command operatedBotCommand() {
    return Command.SETTINGS;
  }

}
