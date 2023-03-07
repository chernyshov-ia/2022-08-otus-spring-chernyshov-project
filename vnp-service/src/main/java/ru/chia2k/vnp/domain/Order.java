package ru.chia2k.vnp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "req")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id")
    OrderAddress sender;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipient_id")
    OrderAddress recipient;

    @Column(name = "seal")
    private String seal;

    @Column(name = "cargo_category_id")
    private Integer cargoCategoryId;

    @Column(name = "cargo_category_name")
    private String cargoCategoryName;

    @Column(name = "recipient_person_name")
    private String recipientPersonName;

    @Column(name = "recipient_person_phone")
    private String recipientPersonPhone;

    @Column(name = "description")
    private String description;

    @Column(name = "user_comment_text")
    private String userCommentText;

    @Column(name = "volume", columnDefinition = "Float8")
    private BigDecimal volume;

    @Column(name = "weight", columnDefinition = "Float8")
    private BigDecimal weight;

    @Column(name = "value", columnDefinition = "numeric(12,2)")
    private BigDecimal value;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_full_name")
    private String userName;

    @Column(name = "user_email")
    private String userEmail;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "parcel_id")
    private Integer parcelId;

    @Column(name = "parcel_barcode")
    private String parcelBarcode;
}