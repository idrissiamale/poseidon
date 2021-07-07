package com.nnk.springboot.services.ruleName;

import com.nnk.springboot.domains.RuleName;

import java.util.List;

/**
 * An interface which provides some CRUD methods to implement on RuleName service class.
 */
public interface RuleNameService {
    /**
     * Retrieves a rule by its id.
     *
     * @param id - must not be null.
     * @return the RuleName entity with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException  if id is null or not found.
     * @see com.nnk.springboot.repositories.RuleNameRepository
     */
    RuleName findById(Integer id) throws IllegalArgumentException;

    /**
     * Retrieves all rules.
     *
     * @return all RuleName entities.
     * @see com.nnk.springboot.repositories.RuleNameRepository
     */
    List<RuleName> findAll();

    /**
     * Saves a rule.
     *
     * @param ruleName - must not be null.
     * @return the saved rule.
     * @throws IllegalArgumentException if RuleName entity is null.
     * @see com.nnk.springboot.repositories.RuleNameRepository
     */
    RuleName save(RuleName ruleName) throws IllegalArgumentException;

    /**
     * Updates rule's data.
     *
     * @param id - rule's id. Must not be null.
     * @param ruleName - must not be null.
     * @return the updated rule.
     * @throws IllegalArgumentException if RuleName entity/id is null or not found.
     * @see com.nnk.springboot.repositories.RuleNameRepository
     */
    RuleName update(Integer id, RuleName ruleName) throws IllegalArgumentException;

    /**
     * Deletes a rule.
     *
     * @param id - rule's id. Must not be null.
     * @throws IllegalArgumentException if rule's id is null or not found.
     * @see com.nnk.springboot.repositories.RuleNameRepository
     */
    void delete(Integer id) throws IllegalArgumentException;
}
