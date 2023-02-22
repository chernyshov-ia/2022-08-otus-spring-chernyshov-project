package ru.chia2k.logist.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Table(name = "address_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressType {
    @Id
    private String id;

    @Column(name = "name")
    private String name;
}
