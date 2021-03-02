package com.astcore.service;

import com.astcore.service.dto.MtdUnitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MtdUnit.
 */
public interface MtdUnitService {

    /**
     * Save a mtdUnit.
     *
     * @param mtdUnitDTO the entity to save
     * @return the persisted entity
     */
    MtdUnitDTO save(MtdUnitDTO mtdUnitDTO);

    /**
     *  Get all the mtdUnits.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdUnitDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" mtdUnit.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MtdUnitDTO findOne(Long id);

    /**
     *  Delete the "id" mtdUnit.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the mtdUnit corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdUnitDTO> search(String query, Pageable pageable);
}
