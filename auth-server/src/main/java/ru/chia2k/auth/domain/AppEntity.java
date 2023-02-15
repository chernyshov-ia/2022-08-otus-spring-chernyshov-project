package ru.chia2k.auth.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "APPS")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppEntity {
    @Id
    private String id;
}

