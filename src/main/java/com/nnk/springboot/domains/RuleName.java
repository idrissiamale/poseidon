package com.nnk.springboot.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rulename")
public class RuleName implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private String json;

    private String template;

    private String sqlStr;

    private String sqlPart;
}
