package com.vasylyev.hometasks.model;

import com.vasylyev.hometasks.model.enums.SettingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Builder
@Entity
@Table(name = "app_settings")
@NoArgsConstructor
@AllArgsConstructor
public class AppSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private SettingType settingType;
    private String settingData;
    @ManyToOne
    private Account account;
}
