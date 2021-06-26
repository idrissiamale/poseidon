package com.nnk.springboot.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "curvepoint")
public class CurvePoint implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "must not be null")
    @Range(min = 1, message = "Curve id should be greater than zero")
    private Integer curveId;

    @CreationTimestamp
    private Timestamp asOfDate;

    @NotNull(message = "Term is mandatory")
    @Digits(message="Term should have maximum 5 digits and three decimals", integer = 5, fraction = 3)
    private Double term;

    @NotNull(message = "Value is mandatory")
    @Digits(message="Value should have maximum 5 digits and three decimals", integer = 5, fraction = 3)
    private Double value;

    @CreationTimestamp
    private Timestamp creationDate;
}
