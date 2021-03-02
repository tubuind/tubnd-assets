package com.astcore.service;

import com.astcore.service.dto.MtdDevicetypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MtdDevicetype.
 */
public interface MtdDevicetypeService {

    /**
     * Save a mtdDevicetype.
     *
     * @param mtdDevicetypeDTO the entity to save
     * @return the persisted entity
     */
    MtdDevicetypeDTO save(MtdDevicetypeDTO mtdDevicetypeDTO);

    /**
     *  Get all the mtdDevicetypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdDevicetypeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" mtdDevicetype.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MtdDevicetypeDTO findOne(Long id);

    /**
     *  Delete the "id" mtdDevicetype.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the mtdDevicetype corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdDevicetypeDTO> search(String query, Pageable pageable);
}
