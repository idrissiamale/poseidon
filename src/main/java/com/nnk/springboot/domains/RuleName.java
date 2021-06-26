package com.nnk.springboot.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 125, message = "Description should have maximum 125 characters")
    private String description;

    @NotBlank(message = "Json is mandatory")
    @Size(max = 125, message = "Json should have maximum 125 characters")
    private String json;

    @NotBlank(message = "Template is mandatory")
    @Size(max = 512, message = "Template should have maximum 512 characters")
    private String template;

    @NotBlank(message = "sql is mandatory")
    @Size(max = 125, message = "sql should have maximum 125 characters")
    private String sqlStr;

    @NotBlank(message = "sqlPart is mandatory")
    @Size(max = 125, message = "sqlPart should have maximum 125 characters")
    private String sqlPart;
}
