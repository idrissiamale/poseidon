package com.nnk.springboot.services.ruleName;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceImplTest {
    @Mock
    private RuleNameRepository ruleNameRepository;
    private RuleNameServiceImpl ruleNameServiceImpl;
    private RuleName ruleName;
    private List<RuleName> rules;

    @BeforeEach
    public void setUp() {
        ruleNameServiceImpl = new RuleNameServiceImpl(ruleNameRepository);
        ruleName = new RuleName(1, "Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        RuleName ruleName2 = new RuleName(2, "Rule Name 2", "Description 2", "Json 2", "Template 2", "SQL 2", "SQL Part 2");
        rules = new ArrayList<>();
        rules.add(ruleName);
        rules.add(ruleName2);
    }

    @Test
    @DisplayName("Checking that the new rule is correctly saved")
    public void shouldReturnNewRuleWhenSaved() {
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

        RuleName ruleNameToSave = ruleNameServiceImpl.save(ruleName);

        verify(ruleNameRepository).save(any(RuleName.class));
        assertNotNull(ruleNameToSave);
    }

    @Test
    @DisplayName("Comparing expected and actual name to check that the new rule is correctly saved")
    public void shouldGetTheSameNameWhenNewRuleIsCorrectlySaved() {
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

        RuleName ruleNameToSave = ruleNameServiceImpl.save(ruleName);

        verify(ruleNameRepository).save(any(RuleName.class));
        assertEquals(ruleName.getName(), ruleNameToSave.getName());
    }

    @Test
    @DisplayName("Checking that the rule is updated with the new name")
    public void shouldReturnRuleWithNewNameWhenRuleUpdated() {
        ruleName.setName("name");
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

        RuleName ruleNameUpdated = ruleNameServiceImpl.update(1, ruleName);

        verify(ruleNameRepository).save(any(RuleName.class));
        assertNotNull(ruleNameUpdated);
    }

    @Test
    @DisplayName("Comparing expected and actual name to check that the rule was correctly updated")
    public void shouldGetTheSameNameWhenRuleUpdated() {
        ruleName.setName("name");
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

        RuleName ruleNameUpdated = ruleNameServiceImpl.update(1, ruleName);

        verify(ruleNameRepository).save(any(RuleName.class));
        assertEquals("name", ruleNameUpdated.getName());
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when the rule we want to update is not found")
    public void shouldThrowExceptionWhenRuleToUpdateIsNotFound() {
        doThrow(new IllegalArgumentException()).when(ruleNameRepository).findById(7);

        assertThrows(IllegalArgumentException.class, () -> ruleNameServiceImpl.update(7, ruleName));
        verify(ruleNameRepository).findById(7);
    }

    @Test
    @DisplayName("Checking that the rule is correctly fetched by its id")
    public void shouldFindRuleByItsId() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.ofNullable(ruleName));

        RuleName ruleNameToFind = ruleNameServiceImpl.findById(1);

        assertEquals("Template", ruleNameToFind.getTemplate());
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when rule's id is null")
    public void shouldThrowExceptionWhenRuleIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> ruleNameServiceImpl.findById(null));
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when rule's id does not exist")
    public void shouldThrowExceptionWhenRuleIdDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> ruleNameServiceImpl.findById(7));
    }

    @Test
    @DisplayName("Checking that rules are correctly fetched")
    public void shouldGetAllRules() {
        when(ruleNameRepository.findAll()).thenReturn(rules);

        List<RuleName> ruleNames = ruleNameServiceImpl.findAll();

        verify(ruleNameRepository).findAll();
        assertEquals(rules, ruleNames);
    }

    @Test
    @DisplayName("Checking that rule, if found by its id, is correctly deleted")
    public void shouldDeleteRuleWhenFoundByItsId() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.ofNullable(ruleName));
        doNothing().when(ruleNameRepository).delete(ruleName);

        ruleNameServiceImpl.delete(ruleName.getId());

        verify(ruleNameRepository).delete(ruleName);
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when the rule we want to delete is not found")
    public void shouldThrowExceptionWhenRuleToDeleteIsNotFound() {
        doThrow(new IllegalArgumentException()).when(ruleNameRepository).findById(7);

        assertThrows(IllegalArgumentException.class, () -> ruleNameServiceImpl.delete(7));
        verify(ruleNameRepository).findById(7);
    }
}
