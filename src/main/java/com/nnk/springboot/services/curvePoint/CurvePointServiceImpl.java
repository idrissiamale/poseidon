package com.nnk.springboot.services.curvePoint;

import com.nnk.springboot.domains.CurvePoint;
import com.nnk.springboot.domains.Trade;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurvePointServiceImpl implements CurvePointService {
    @Autowired
    private CurvePointRepository curvePointRepository;

    @Override
    public CurvePoint findById(Integer id) throws IllegalArgumentException {
        return curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
    }

    @Override
    public List<CurvePoint> findAll() {
        return curvePointRepository.findAll();
    }

    @Override
    public CurvePoint save(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    @Override
    public CurvePoint update(Integer id, CurvePoint curvePoint) {
        return curvePointRepository.findById(id).map(curvePointToUpdate -> {
            curvePointToUpdate.setCurveId(curvePoint.getCurveId());
            curvePointToUpdate.setTerm(curvePoint.getTerm());
           curvePointToUpdate.setValue(curvePoint.getValue());
            return curvePointRepository.save(curvePointToUpdate);
        }).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
    }

    @Override
    public void delete(Integer id) {
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
        curvePointRepository.delete(curvePoint);
    }
}
