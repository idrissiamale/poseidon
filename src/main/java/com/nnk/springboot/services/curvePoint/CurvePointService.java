package com.nnk.springboot.services.curvePoint;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;

/**
 * An interface which provides some CRUD methods to implement on CurvePoint service class.
 */
public interface CurvePointService {
    /**
     * Retrieves a curve point by its id.
     *
     * @param id - must not be null.
     * @return the CurvePoint entity with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException  if id is null or not found.
     * @see com.nnk.springboot.repositories.CurvePointRepository
     */
    CurvePoint findById(Integer id) throws IllegalArgumentException;

    /**
     * Retrieves all curve points.
     *
     * @return all CurvePoint entities.
     * @see com.nnk.springboot.repositories.CurvePointRepository
     */
    List<CurvePoint> findAll();

    /**
     * Saves a curve point.
     *
     * @param curvePoint - must not be null.
     * @return the saved curve point.
     * @throws IllegalArgumentException if CurvePoint entity is null.
     * @see com.nnk.springboot.repositories.CurvePointRepository
     */
    CurvePoint save(CurvePoint curvePoint) throws IllegalArgumentException;

    /**
     * Updates curve point's data.
     *
     * @param id - curvePoint's id. Must not be null.
     * @param curvePoint - must not be null.
     * @return the updated curve point.
     * @throws IllegalArgumentException if CurvePoint entity/id is null or not found.
     * @see com.nnk.springboot.repositories.CurvePointRepository
     */
    CurvePoint update(Integer id, CurvePoint curvePoint) throws IllegalArgumentException;

    /**
     * Deletes a curve point.
     *
     * @param id - curvePoint's id. Must not be null.
     * @throws IllegalArgumentException if curvePoint's id is null or not found.
     * @see com.nnk.springboot.repositories.CurvePointRepository
     */
    void delete(Integer id) throws IllegalArgumentException;
}
