package ru.chia2k.logist.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "hu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parcel {
    @Id
    private Integer id;

    @Column(name = "hu_num")
    private String number;

    @Column(name = "seal")
    private String seal;

    @Column(name = "barcode")
    private String barcode;

    @ManyToOne(cascade = CascadeType.PERSIST, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    @Fetch(FetchMode.JOIN)
    private Address sender;

    @ManyToOne(cascade = CascadeType.PERSIST, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient_id")
    @Fetch(FetchMode.JOIN)
    private Address recipient;

    @Column(name = "descr")
    private String description;

    @Column(name = "ctime")
    private LocalDateTime creationDate;

    @Column(name = "volume", columnDefinition = "Float8")
    private BigDecimal volume;

    @Column(name = "weight", columnDefinition = "Float8")
    private BigDecimal weight;
}
