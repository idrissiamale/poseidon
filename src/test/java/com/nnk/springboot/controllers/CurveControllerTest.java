package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.CurvePoint;
import com.nnk.springboot.services.curvePoint.CurvePointService;
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
public class CurveControllerTest {
    private MockMvc mockMvc;
    private CurvePoint curvePoint;
    private CurvePoint curvePointUpdated;
    private List<CurvePoint> curvePoints;

    @Mock
    CurvePointService curvePointService;

    @BeforeEach
    public void setUp() {
        CurveController curveController = new CurveController(curvePointService);
        curvePoint = new CurvePoint(1, 10, 15.7, 25.0);
        curvePointUpdated = new CurvePoint(1, 9, 15.7, 25.0);
        CurvePoint curvePoint2 = new CurvePoint(2, 11, 20.0, 15.15);
        curvePoints = new ArrayList<>();
        curvePoints.add(curvePoint);
        curvePoints.add(curvePoint2);
        this.mockMvc = MockMvcBuilders.standaloneSetup(curveController).build();
    }

    @Test
    @DisplayName("Checking that the curvePoint/list page is returned when the user makes a GET request to the /curvepoint/list URL")
    public void shouldReturnCurvePointListView() throws Exception {
        when(curvePointService.findAll()).thenReturn(curvePoints);

        this.mockMvc.perform(get("/curvepoint/list").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attribute("curvePoints", curvePoints))
                .andExpect(model().attribute("curvePoints", hasSize(2)))
                .andExpect(model().attribute("curvePoints", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("curveId", is(10)),
                                hasProperty("term", is(15.7)),
                                hasProperty("value", is(25.0))
                        )
                )))
                .andExpect(model().attribute("curvePoints", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("curveId", is(11)),
                                hasProperty("term", is(20.0)),
                                hasProperty("value", is(15.15))
                        )
                )));

        verify(curvePointService).findAll();
    }

    @Test
    @DisplayName("Checking that the curvePoint/add page is returned when the user makes a GET request to the /curvepoint/add URL")
    public void shouldReturnCurvePointAddPageView() throws Exception {
        this.mockMvc.perform(get("/curvepoint/add").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @DisplayName("Checking that the user is redirected to the curvePoint/list page when curvePoint's data are correctly saved")
    public void shouldReturnCurvePointListPageViewWhenCurvePointDataAreCorrectlySaved() throws Exception {
        Integer curveId = 10;
        when(curvePointService.save(any(CurvePoint.class))).thenReturn(curvePoint);

        this.mockMvc.perform(post("/curvepoint/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("curveId", String.valueOf(10))
                .param("term", String.valueOf(15.7))
                .param("value", String.valueOf(25.0))
                .sessionAttr("curvePoint", curvePoint)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/curvepoint/list"))
                .andExpect(redirectedUrl("/curvepoint/list"))
                .andExpect(model().hasNoErrors())
                .andDo(print());

        verify(curvePointService).save(any(CurvePoint.class));
        assertEquals(curveId, curvePoint.getCurveId());
    }

    @Test
    @DisplayName("Checking that the 'Add New Curve Point' form (curvePoint/add page) is returned with error message when there are errors on the form fields")
    public void shouldReturnAddNewTradeFormViewWhenErrorsOnCurveIdAndTermFields() throws Exception {
        Integer curveId = 0;
        Double term = null;

        this.mockMvc.perform(post("/curvepoint/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("curveId", String.valueOf(curveId))
                .param("term", String.valueOf(term))
                .param("value", String.valueOf(25.0))
                .sessionAttr("curvePoint", curvePoint)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "curveId"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "term"))
                .andExpect(model().attribute("curvePoint", hasProperty("curveId", is(curveId))))
                .andExpect(model().attribute("curvePoint", hasProperty("term", is(term))))
                .andDo(print());

        verifyZeroInteractions(curvePointService);
    }

    @Test
    @DisplayName("Checking that the 'Update Curve Point' form (curvePoint/update page) is returned when the user makes a GET request to the /curvepoint/update/{id} URL")
    public void shouldReturnUpdateProfileFormView() throws Exception {
        when(curvePointService.findById(1)).thenReturn(curvePoint);

        this.mockMvc.perform(get("/curvepoint/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attribute("curvePoint", curvePoint))
                .andDo(print());

        verify(curvePointService).findById(1);
    }

    @Test
    @DisplayName("Checking that the user is redirected to the curvePoint/list page when curvePoint's data are correctly updated")
    public void shouldReturnCurvePointListPageViewWhenCurvePointDataAreCorrectlyUpdated() throws Exception {
        Integer curveIdUpdated = 9;
        when(curvePointService.update(anyInt(), any(CurvePoint.class))).thenReturn(curvePointUpdated);

        this.mockMvc.perform(post("/curvepoint/update/{id}", 1).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("curveId", String.valueOf(9))
                .param("term", String.valueOf(15.7))
                .param("value", String.valueOf(25.0))
                .sessionAttr("curvePoint", curvePointUpdated)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/curvepoint/list"))
                .andExpect(redirectedUrl("/curvepoint/list"))
                .andExpect(model().hasNoErrors())
                .andDo(print());

        verify(curvePointService).update(anyInt(), any(CurvePoint.class));
        assertEquals(curveIdUpdated, curvePointUpdated.getCurveId());
    }

    @Test
    @DisplayName("Checking that the 'Update Curve Point' form (curvePoint/update page) is returned with error message when there are errors on the form fields")
    public void shouldReturnUpdateCurvePointFormViewWhenErrorsOnCurveIdField() throws Exception {
        Integer curveId = null;

        this.mockMvc.perform(post("/curvepoint/update/{id}", 1).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("curveId", String.valueOf(curveId))
                .param("term", String.valueOf(15.7))
                .param("value", String.valueOf(25.0))
                .sessionAttr("curvePoint", curvePointUpdated)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "curveId"))
                .andExpect(model().attribute("curvePoint", hasProperty("curveId", is(curveId))))
                .andDo(print());

        verifyZeroInteractions(curvePointService);
    }

    @Test
    @DisplayName("Checking that the user is redirected to the curvePoint/list page when curvePoint's data are correctly deleted")
    public void shouldReturnCurvePointListPageViewWhenCurvePointDataAreCorrectlyDeleted() throws Exception {
        doNothing().when(curvePointService).delete(1);

        this.mockMvc.perform(get("/curvepoint/delete/{id}", 1))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/curvepoint/list"))
                .andExpect(redirectedUrl("/curvepoint/list"))
                .andExpect(model().attribute("curvePoint", nullValue()))
                .andDo(print());

        verify(curvePointService).delete(1);
    }
}
