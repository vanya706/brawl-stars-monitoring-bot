package com.ivan.mostovyi.brawlstarsmonitoringbot.bot;

import com.ivan.mostovyi.brawlstarsmonitoringbot.util.CommandUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Component
public class CommandInitializer {

  public void initializeCommands(AbsSender sender) {
    try {
      sender.execute(new SetMyCommands() {{
        setCommands(List.of(new BotCommand() {{
          setCommand(CommandUtil.getCommandText(Command.START));
          setDescription("Hello! I will simplify your life!");
        }}, new BotCommand() {{
          setCommand(CommandUtil.getCommandText(Command.HELP));
          setDescription("Ask for help!");
        }}, new BotCommand() {{
          setCommand(CommandUtil.getCommandText(Command.SETTINGS));
          setDescription(String.format("To set Club Tag. Example: %s #club_tag", CommandUtil.getCommandText(Command.SETTINGS)));
        }}, new BotCommand() {{
          setCommand(CommandUtil.getCommandText(Command.MEMBERS_NOT_IN_CHAT));
          setDescription("Show which members is not in Telegram chat");
        }}));
      }});
    } catch (TelegramApiException e) {
      log.error(e.getMessage(), e);
    }
  }

}
