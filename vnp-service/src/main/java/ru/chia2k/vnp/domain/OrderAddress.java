package ru.chia2k.vnp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
public class OrderAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "address_id")
    private String code;

    @Column(name = "address_name")
    private String name;

    @Column(name = "address")
    private String address;
}
