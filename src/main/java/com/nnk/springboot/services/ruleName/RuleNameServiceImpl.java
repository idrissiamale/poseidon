package com.nnk.springboot.services.ruleName;

import com.nnk.springboot.domains.CurvePoint;
import com.nnk.springboot.domains.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameServiceImpl implements RuleNameService {
    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Override
    public RuleName findById(Integer id) throws IllegalArgumentException {
        return ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
    }

    @Override
    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    @Override
    public RuleName save(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    @Override
    public RuleName update(Integer id, RuleName ruleName) {
        return ruleNameRepository.findById(id).map(ruleNameToUpdate -> {
            ruleNameToUpdate.setName(ruleName.getName());
            ruleNameToUpdate.setDescription(ruleName.getDescription());
            ruleNameToUpdate.setJson(ruleName.getJson());
            ruleNameToUpdate.setTemplate(ruleName.getTemplate());
            ruleNameToUpdate.setSqlStr(ruleName.getSqlStr());
            ruleNameToUpdate.setSqlPart(ruleName.getSqlPart());
            return ruleNameRepository.save(ruleNameToUpdate);
        }).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
    }

    @Override
    public void delete(Integer id) {
        RuleName ruleName = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        ruleNameRepository.delete(ruleName);
    }
}
