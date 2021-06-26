package com.nnk.springboot.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "Account is mandatory")
    @Size(max = 30, message = "Account should have maximum 30 characters")
    @Column(nullable = false)
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Size(max = 30, message = "Type should have maximum 30 characters")
    @Column(nullable = false)
    private String type;

    @NotNull(message = "Buy quantity is mandatory")
    @Digits(message="Buy quantity should have maximum 5 digits and three decimals", integer = 5, fraction = 3)
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
