package com.vasylyev.hometasks.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@Entity
@Table(name = "Subscriber")
@AllArgsConstructor
@RequiredArgsConstructor
public class Subscriber {
    @Id
    private Long id;
    private String name;
    private String description;
    private String chatId;
    private Boolean active;
}
