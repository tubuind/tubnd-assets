package com.astcore.service;

import com.astcore.service.dto.CifAreaDTO;
import com.astcore.service.dto.DeviceInfoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CifArea.
 */
public interface CifAreaService {

    /**
     * Save a cifArea.
     *
     * @param cifAreaDTO the entity to save
     * @return the persisted entity
     */
    CifAreaDTO save(CifAreaDTO cifAreaDTO);

    /**
     *  Get all the cifAreas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CifAreaDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" cifArea.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CifAreaDTO findOne(Long id);

    /**
     *  Delete the "id" cifArea.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the cifArea corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CifAreaDTO> search(String query, Pageable pageable);
    
    /**
     *  Get all the devices.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CifAreaDTO> getAreasByUserLoged(Pageable pageable);
}
