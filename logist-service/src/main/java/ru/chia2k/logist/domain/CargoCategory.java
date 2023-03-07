package ru.chia2k.logist.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cargo_category")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CargoCategory {
    @Id
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "group_name")
    private String groupName;
}
