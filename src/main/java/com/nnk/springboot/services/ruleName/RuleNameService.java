package com.nnk.springboot.services.ruleName;

import com.nnk.springboot.domains.RuleName;

import java.util.List;

public interface RuleNameService {
    RuleName findById(Integer id) throws IllegalArgumentException;

    List<RuleName> findAll();

    RuleName save(RuleName ruleName);

    RuleName update(Integer id, RuleName ruleName);

    void delete(Integer id);
}
