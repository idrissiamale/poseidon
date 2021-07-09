package com.nnk.springboot.services.ruleName;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the RuleNameService interface.
 *
 * @see RuleNameService
 */
@Service
public class RuleNameServiceImpl implements RuleNameService {
    private static final Logger logger = LogManager.getLogger("RuleNameServiceImpl");
    private RuleNameRepository ruleNameRepository;

    @Autowired
    public RuleNameServiceImpl(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RuleName findById(Integer id) throws IllegalArgumentException {
        logger.info("RuleName was successfully fetched.");
        return ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RuleName> findAll() {
        logger.info("Rules were successfully fetched.");
        return ruleNameRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RuleName save(RuleName ruleName) throws IllegalArgumentException {
        logger.info("RuleName was saved successfully.");
        return ruleNameRepository.save(ruleName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RuleName update(Integer id, RuleName ruleName) throws IllegalArgumentException {
        return ruleNameRepository.findById(id).map(ruleNameToUpdate -> {
            ruleNameToUpdate.setName(ruleName.getName());
            ruleNameToUpdate.setDescription(ruleName.getDescription());
            ruleNameToUpdate.setJson(ruleName.getJson());
            ruleNameToUpdate.setTemplate(ruleName.getTemplate());
            ruleNameToUpdate.setSqlStr(ruleName.getSqlStr());
            ruleNameToUpdate.setSqlPart(ruleName.getSqlPart());
            logger.info("RuleName was updated successfully.");
            return ruleNameRepository.save(ruleNameToUpdate);
        }).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) throws IllegalArgumentException {
        RuleName ruleName = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        logger.info("RuleName was deleted successfully.");
        ruleNameRepository.delete(ruleName);
    }
}
