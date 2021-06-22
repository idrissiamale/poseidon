package com.nnk.springboot.services.curvePoint;

import com.nnk.springboot.domains.CurvePoint;

import java.util.List;

public interface CurvePointService {
    CurvePoint findById(Integer id) throws IllegalArgumentException;

    List<CurvePoint> findAll();

    CurvePoint save(CurvePoint curvePoint);

    CurvePoint update(Integer id, CurvePoint curvePoint);

    void delete(Integer id);
}
