package com.ivan.mostovyi.brawlstarsmonitoringbot.util;

import com.ivan.mostovyi.brawlstarsmonitoringbot.bot.Command;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.stream.Collectors;

public class CommandUtil {

  private static final String COMMAND_PREFIX = "/";

  public static Command getCommand(String text) {
    if (StringUtils.isBlank(text)) {
      return Command.NONE;
    }
    return Arrays.stream(Command.values())
        .filter(command -> isKnownCommand(command, text))
        .findFirst()
        .orElse(Command.NONE);
  }

  private static boolean isKnownCommand(Command command, @NotBlank String text) {
    return StringUtils.startsWithIgnoreCase(text, getCommandText(command));
  }

  public static List<String> getCommandArgs(Command command, String message) {
    String commandText = getCommandText(command);
    return Arrays.stream(message.split(StringUtils.SPACE))
        .filter(arg -> !StringUtils.startsWithIgnoreCase(arg, commandText))
        .filter(StringUtils::isNotBlank)
        .collect(Collectors.toList());
  }

  public static String getCommandText(Command command) {
    return (COMMAND_PREFIX + command.name()).toLowerCase();
  }

}
