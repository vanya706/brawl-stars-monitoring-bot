package com.ivan.mostovyi.brawlstarsmonitoringbot.repository;

import com.ivan.mostovyi.brawlstarsmonitoringbot.domain.Settings;
import org.springframework.data.repository.CrudRepository;

public interface SettingsRepository extends CrudRepository<Settings, String> {}
