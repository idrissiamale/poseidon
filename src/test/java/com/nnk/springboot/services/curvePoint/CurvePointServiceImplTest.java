package com.nnk.springboot.services.curvePoint;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class CurvePointServiceImplTest {
    @Mock
    private CurvePointRepository curvePointRepository;
    private CurvePointServiceImpl curvePointServiceImpl;
    private CurvePoint curvePoint;
    private List<CurvePoint> curvePoints;

    @BeforeEach
    public void setUp() {
        curvePointServiceImpl = new CurvePointServiceImpl(curvePointRepository);
        curvePoint = new CurvePoint(1, 10, 15.7, 25.0);
        CurvePoint curvePoint2 = new CurvePoint(2, 11, 20.0, 15.15);
        curvePoints = new ArrayList<>();
        curvePoints.add(curvePoint);
        curvePoints.add(curvePoint2);
    }

    @Test
    @DisplayName("Checking that the curve point user is correctly saved")
    public void shouldReturnNewCurvePointWhenSaved() {
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        CurvePoint curvePointToSave = curvePointServiceImpl.save(curvePoint);

        verify(curvePointRepository).save(any(CurvePoint.class));
        assertNotNull(curvePointToSave);
    }

    @Test
    @DisplayName("Comparing expected curve id and actual curve id to check that the new curve point is correctly saved ")
    public void shouldGetTheSameCurveIdWhenNewCurvePointIsCorrectlySaved() {
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        CurvePoint curvePointToSave = curvePointServiceImpl.save(curvePoint);

        verify(curvePointRepository).save(any(CurvePoint.class));
        assertEquals(curvePoint.getCurveId(), curvePointToSave.getCurveId());
    }

    @Test
    @DisplayName("Checking that the curve point is updated with the new curve id")
    public void shouldReturnCurvePointWithNewCurveIdWhenCurvePointUpdated() {
        curvePoint.setCurveId(7);
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        CurvePoint curvePointUpdated = curvePointServiceImpl.update(1, curvePoint);

        verify(curvePointRepository).save(any(CurvePoint.class));
        assertNotNull(curvePointUpdated);
    }

    @Test
    @DisplayName("Comparing expected and actual curve id to check that the curve point was correctly updated ")
    public void shouldGetTheSameCurveIdWhenCurvePointUpdated() {
        curvePoint.setCurveId(7);
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        CurvePoint curvePointUpdated = curvePointServiceImpl.update(1, curvePoint);

        verify(curvePointRepository).save(any(CurvePoint.class));
        assertEquals(curvePoint.getCurveId(), curvePointUpdated.getCurveId());
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when the curve point we want to update is not found")
    public void shouldThrowExceptionWhenCurvePointToUpdateIsNotFound() {
        doThrow(new IllegalArgumentException()).when(curvePointRepository).findById(7);

        assertThrows(IllegalArgumentException.class, () -> curvePointServiceImpl.update(7, curvePoint));
        verify(curvePointRepository).findById(7);
    }

    @Test
    @DisplayName("Checking that the curve point is correctly fetched by its id")
    public void shouldFindCurvePointByItsId() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        CurvePoint curvePointToFind = curvePointServiceImpl.findById(1);

        assertEquals(curvePoint.getTerm(), curvePointToFind.getTerm());
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when curve point's id is null")
    public void shouldThrowExceptionWhenCurvePointIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> curvePointServiceImpl.findById(null));
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when curve point's id does not exist")
    public void shouldThrowExceptionWhenCurvePointIdDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> curvePointServiceImpl.findById(7));
    }

    @Test
    @DisplayName("Checking that curve points are correctly fetched")
    public void shouldGetAllCurvePoints() {
        when(curvePointRepository.findAll()).thenReturn(curvePoints);

        List<CurvePoint> curvePointList = curvePointServiceImpl.findAll();

        verify(curvePointRepository).findAll();
        assertEquals(curvePoints, curvePointList);
    }

    @Test
    @DisplayName("Checking that curve point, if found by its id, is correctly deleted")
    public void shouldDeleteCurvePointWhenFoundByItsId() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.ofNullable(curvePoint));
        doNothing().when(curvePointRepository).delete(curvePoint);

        curvePointServiceImpl.delete(curvePoint.getId());

        verify(curvePointRepository).delete(curvePoint);
    }

    @Test
    @DisplayName("Checking that IllegalArgumentException is thrown when the curve point we want to delete is not found")
    public void shouldThrowExceptionWhenCurvePointToDeleteIsNotFound() {
        doThrow(new IllegalArgumentException()).when(curvePointRepository).findById(7);

        assertThrows(IllegalArgumentException.class, () -> curvePointServiceImpl.delete(7));
        verify(curvePointRepository).findById(7);
    }
}
