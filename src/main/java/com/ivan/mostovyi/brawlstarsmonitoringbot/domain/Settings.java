package com.ivan.mostovyi.brawlstarsmonitoringbot.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@RedisHash
public class Settings {

  @Id
  private String chatId;

  @Indexed
  private String clubTag;

  public Settings(String chatId) {
    this.chatId = chatId;
  }

}
