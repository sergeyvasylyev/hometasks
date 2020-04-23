package com.vasylyev.hometasks.repository;

import com.vasylyev.hometasks.model.AppSettings;
import com.vasylyev.hometasks.model.enums.SettingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppSettingsRepository extends JpaRepository<AppSettings, Long> {

    Optional<AppSettings> findBySettingType(SettingType settingType);

    @Query("SELECT app FROM AppSettings app "
            + "JOIN FETCH app.account acc "
            + "WHERE acc.name = ?1 AND app.settingType = ?2")
    Optional<AppSettings> findByAccountIdSettingType(String accountName, SettingType settingType);

    @Query("SELECT app FROM AppSettings app "
            + "JOIN FETCH app.account acc "
            + "WHERE acc.isDefault = TRUE AND app.settingType = ?1")
    Optional<AppSettings> findBySettingTypeDefaultAccount(SettingType settingType);

    @Query("SELECT app FROM AppSettings app "
            + "JOIN FETCH app.account acc "
            + "WHERE acc.name = ?1")
    List<AppSettings> findByAccountId(String accountName);
}