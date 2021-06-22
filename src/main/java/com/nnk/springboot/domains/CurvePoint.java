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
@Table(name = "curvepoint")
public class CurvePoint implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Integer curveId;

    @CreationTimestamp
    private Timestamp asOfDate;

    private Double term;

    private Double value;

    @CreationTimestamp
    private Timestamp creationDate;
}
