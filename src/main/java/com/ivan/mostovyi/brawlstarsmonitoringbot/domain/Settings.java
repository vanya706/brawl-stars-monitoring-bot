package com.ivan.mostovyi.brawlstarsmonitoringbot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Settings {

  @Id
  @Column(nullable = false, updatable = false, length = 40)
  @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private String chatId;

  private String clubTag;

  public Settings(String chatId) {
    this.chatId = chatId;
  }

}
