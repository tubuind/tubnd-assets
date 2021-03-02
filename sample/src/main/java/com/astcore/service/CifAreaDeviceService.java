package com.astcore.service;

import com.astcore.service.dto.CifAreaDeviceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CifAreaDevice.
 */
public interface CifAreaDeviceService {

    /**
     * Save a cifAreaDevice.
     *
     * @param cifAreaDeviceDTO the entity to save
     * @return the persisted entity
     */
    CifAreaDeviceDTO save(CifAreaDeviceDTO cifAreaDeviceDTO);

    /**
     *  Get all the cifAreaDevices.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CifAreaDeviceDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" cifAreaDevice.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CifAreaDeviceDTO findOne(Long id);

    /**
     *  Delete the "id" cifAreaDevice.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the cifAreaDevice corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CifAreaDeviceDTO> search(String query, Pageable pageable);
}
