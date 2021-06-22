package com.nnk.springboot.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trade")
public class Trade implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer tradeId;

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String type;

    private Double buyQuantity;

    private Double sellQuantity;

    private Double buyPrice;

    private Double sellPrice;

    private String benchmark;

    @CreationTimestamp
    private Timestamp tradeDate;

    private String security;

    private String status;

    private String trader;

    private String book;

    private String creationName;

    @CreationTimestamp
    private Timestamp creationDate;

    private String revisionName;

    @CreationTimestamp
    private Timestamp revisionDate;

    private String dealName;

    private String dealType;

    private String sourceListId;

    private String side;
}
