package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.ruleName.RuleNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(MockitoExtension.class)
public class RuleNameControllerTest {
    private MockMvc mockMvc;
    private RuleName ruleName;
    private RuleName ruleNameUpdated;
    private List<RuleName> rules;

    @Mock
    RuleNameService ruleNameService;

    @BeforeEach
    public void setUp() {
        RuleNameController ruleNameController = new RuleNameController(ruleNameService);
        ruleName = new RuleName(1, "Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        ruleNameUpdated = new RuleName(1, "name", "Description", "Json", "Template", "SQL", "SQL Part");
        RuleName ruleName2 = new RuleName(2, "Rule Name 2", "Description 2", "Json 2", "Template 2", "SQL 2", "SQL Part 2");
        rules = new ArrayList<>();
        rules.add(ruleName);
        rules.add(ruleName2);
        this.mockMvc = MockMvcBuilders.standaloneSetup(ruleNameController).build();
    }


    @Test
    @DisplayName("Checking that the ruleName/list page is returned when the user makes a GET request to the /rulename/list URL")
    public void shouldReturnRuleNameListView() throws Exception {
        when(ruleNameService.findAll()).thenReturn(rules);

        this.mockMvc.perform(get("/rulename/list").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attribute("rules", rules))
                .andExpect(model().attribute("rules", hasSize(2)))
                .andExpect(model().attribute("rules", hasItem(
                        allOf(
                                hasProperty("name", is("Rule Name")),
                                hasProperty("description", is("Description")),
                                hasProperty("json", is("Json")),
                                hasProperty("template", is("Template")),
                                hasProperty("sqlStr", is("SQL")),
                                hasProperty("sqlPart", is("SQL Part"))
                        )
                )))
                .andExpect(model().attribute("rules", hasItem(
                        allOf(
                                hasProperty("name", is("Rule Name 2")),
                                hasProperty("description", is("Description 2")),
                                hasProperty("json", is("Json 2")),
                                hasProperty("template", is("Template 2")),
                                hasProperty("sqlStr", is("SQL 2")),
                                hasProperty("sqlPart", is("SQL Part 2"))
                        )
                )));

        verify(ruleNameService).findAll();
    }

    @Test
    @DisplayName("Checking that the ruleName/add page is returned when the user makes a GET request to the /rulename/add URL")
    public void shouldReturnRuleNameAddPageView() throws Exception {
        this.mockMvc.perform(get("/rulename/add").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @DisplayName("Checking that the user is redirected to the ruleName/list page when ruleName's data are correctly saved")
    public void shouldReturnRuleNameListPageViewWhenRuleNameDataAreCorrectlySaved() throws Exception {
        when(ruleNameService.save(any(RuleName.class))).thenReturn(ruleName);

        this.mockMvc.perform(post("/rulename/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Rule Name")
                .param("description", "Description")
                .param("json", "Json")
                .param("template", "Template")
                .param("sqlStr", "SQL")
                .param("sqlPart", "SQL Part")
                .sessionAttr("ruleName", ruleName)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/rulename/list"))
                .andExpect(redirectedUrl("/rulename/list"))
                .andExpect(model().hasNoErrors())
                .andDo(print());

        verify(ruleNameService).save(any(RuleName.class));
        assertEquals("Rule Name", ruleName.getName());
    }

    @Test
    @DisplayName("Checking that the 'Add New Rule' form (ruleName/add page) is returned with error message when there are errors on the form fields")
    public void shouldReturnAddNewRuleFormViewWhenErrorsOnNameField() throws Exception {
        String name = " ";

        this.mockMvc.perform(post("/rulename/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", name)
                .param("description", "Description")
                .param("json", "Json")
                .param("template", "Template")
                .param("sqlStr", "SQL")
                .param("sqlPart", "SQL Part")
                .sessionAttr("ruleName", ruleName)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeHasFieldErrors("ruleName", "name"))
                .andExpect(model().attribute("ruleName", hasProperty("name", is(name))))
                .andDo(print());

        verifyZeroInteractions(ruleNameService);
    }

    @Test
    @DisplayName("Checking that the 'Update Rule' form (ruleName/update page) is returned when the user makes a GET request to the /rulename/update/{id} URL")
    public void shouldReturnUpdateRuleFormView() throws Exception {
        when(ruleNameService.findById(1)).thenReturn(ruleName);

        this.mockMvc.perform(get("/rulename/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attribute("ruleName", ruleName))
                .andDo(print());

        verify(ruleNameService).findById(1);
    }

    @Test
    @DisplayName("Checking that the user is redirected to the ruleName/list page when ruleName's data are correctly updated")
    public void shouldReturnRuleNameListPageViewWhenRuleNameDataAreCorrectlyUpdated() throws Exception {
        when(ruleNameService.update(anyInt(), any(RuleName.class))).thenReturn(ruleNameUpdated);

        this.mockMvc.perform(post("/rulename/update/{id}", 1).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", ruleNameUpdated.getName())
                .param("description", "Description")
                .param("json", "Json")
                .param("template", "Template")
                .param("sqlStr", "SQL")
                .param("sqlPart", "SQL Part")
                .sessionAttr("ruleName", ruleNameUpdated)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/rulename/list"))
                .andExpect(redirectedUrl("/rulename/list"))
                .andExpect(model().hasNoErrors())
                .andDo(print());

        verify(ruleNameService).update(anyInt(), any(RuleName.class));
        assertEquals("name", ruleNameUpdated.getName());
    }

    @Test
    @DisplayName("Checking that the 'Update Rule' form (ruleName/update page) is returned with error message when there are errors on the form fields")
    public void shouldReturnUpdateRuleFormViewWhenErrorsOnNameField() throws Exception {
        String name = " ";

        this.mockMvc.perform(post("/rulename/update/{id}", 1).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", name)
                .param("description", "Description")
                .param("json", "Json")
                .param("template", "Template")
                .param("sqlStr", "SQL")
                .param("sqlPart", "SQL Part")
                .sessionAttr("ruleName", ruleNameUpdated)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeHasFieldErrors("ruleName", "name"))
                .andExpect(model().attribute("ruleName", hasProperty("name", is(name))))
                .andDo(print());

        verifyZeroInteractions(ruleNameService);
    }

    @Test
    @DisplayName("Checking that the user is redirected to the ruleName/list page when ruleName's data are correctly deleted")
    public void shouldReturnRuleNameListPageViewWhenRuleNameDataAreCorrectlyDeleted() throws Exception {
        doNothing().when(ruleNameService).delete(1);

        this.mockMvc.perform(get("/rulename/delete/{id}", 1))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/rulename/list"))
                .andExpect(redirectedUrl("/rulename/list"))
                .andExpect(model().attribute("ruleName", nullValue()))
                .andDo(print());

        verify(ruleNameService).delete(1);
    }
}
