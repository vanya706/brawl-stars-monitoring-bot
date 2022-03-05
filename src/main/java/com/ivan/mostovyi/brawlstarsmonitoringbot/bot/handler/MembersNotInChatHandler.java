package com.ivan.mostovyi.brawlstarsmonitoringbot.bot.handler;

import brawljars.model.ClubMember;
import com.ivan.mostovyi.brawlstarsmonitoringbot.bot.Command;
import com.ivan.mostovyi.brawlstarsmonitoringbot.client.BrawlStarsClient;
import com.ivan.mostovyi.brawlstarsmonitoringbot.domain.Settings;
import com.ivan.mostovyi.brawlstarsmonitoringbot.util.TelegramUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberAdministrator;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MembersNotInChatHandler implements Handler {

  private final BrawlStarsClient brawlStarsClient;

  private final AbsSender absSender;

  public MembersNotInChatHandler(BrawlStarsClient brawlStarsClient, @Lazy AbsSender absSender) {
    this.brawlStarsClient = brawlStarsClient;
    this.absSender = absSender;
  }


  @Override
  public List<PartialBotApiMethod<? extends Serializable>> handle(Settings settings, String message) {
    Collection<ClubMember> clubMembers = brawlStarsClient.getClubMembers(settings.getClubTag());
    try {
      GetChatAdministrators request = new GetChatAdministrators();
      request.setChatId(settings.getChatId());
      List<ChatMember> chatMembers = absSender.execute(request);
      List<String> clubMemberNames = clubMembers.stream()
          .map(ClubMember::getName)
          .collect(Collectors.toList());
      List<String> chatMemberTitles = chatMembers.stream()
          .filter(chatMember -> chatMember instanceof ChatMemberAdministrator)
          .map(ChatMemberAdministrator.class::cast)
          .map(ChatMemberAdministrator::getCustomTitle)
          .collect(Collectors.toList());
      log.info("club member names: " + String.join(", ", clubMemberNames));
      log.info("chat member titles: " + String.join(", ", chatMemberTitles));
      clubMemberNames.removeAll(chatMemberTitles);
      log.info("response: " + String.join(", ", clubMemberNames));
      SendMessage answer = TelegramUtil.createMessageTemplate(settings);
      answer.setText("The club members not in the chat are: " + String.join("\n", clubMemberNames));
      return List.of(answer);
    } catch (TelegramApiException e) {
      log.error(e.getMessage(), e);
      return List.of();
    }
  }

  @Override
  public Command operatedBotCommand() {
    return Command.MEMBERS_NOT_IN_CHAT;
  }

}
