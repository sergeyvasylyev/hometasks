package com.vasylyev.hometasks.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    private String name;
    private Boolean isDefault;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppSettings> appSettings;
    private Boolean active;
}
