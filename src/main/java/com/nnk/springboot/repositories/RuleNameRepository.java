package com.nnk.springboot.repositories;

import com.nnk.springboot.domains.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface which provides, by extending JpaRepository, generic CRUD operations to implement on RuleName repository.
 *
 * @see JpaRepository
 */
@Repository
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}
