package com.nnk.springboot.services.curvePoint;

import com.nnk.springboot.domains.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the CurvePointService interface.
 *
 * @see CurvePointService
 */
@Service
public class CurvePointServiceImpl implements CurvePointService {
    private static final Logger logger = LogManager.getLogger("CurvePointServiceImpl");
    private CurvePointRepository curvePointRepository;

    @Autowired
    public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CurvePoint findById(Integer id) throws IllegalArgumentException {
        logger.info("CurvePoint was successfully fetched.");
        return curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CurvePoint> findAll() {
        logger.info("CurvePoints were successfully fetched.");
        return curvePointRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CurvePoint save(CurvePoint curvePoint) throws IllegalArgumentException {
        logger.info("CurvePoint was saved successfully.");
        return curvePointRepository.save(curvePoint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CurvePoint update(Integer id, CurvePoint curvePoint) throws IllegalArgumentException {
        return curvePointRepository.findById(id).map(curvePointToUpdate -> {
            curvePointToUpdate.setCurveId(curvePoint.getCurveId());
            curvePointToUpdate.setTerm(curvePoint.getTerm());
            curvePointToUpdate.setValue(curvePoint.getValue());
            logger.info("CurvePoint was updated successfully.");
            return curvePointRepository.save(curvePointToUpdate);
        }).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) throws IllegalArgumentException {
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
        logger.info("CurvePoint was deleted successfully.");
        curvePointRepository.delete(curvePoint);
    }
}
