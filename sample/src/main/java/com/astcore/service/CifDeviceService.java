package com.astcore.service;

import com.astcore.service.dto.CifDeviceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CifDevice.
 */
public interface CifDeviceService {

    /**
     * Save a cifDevice.
     *
     * @param cifDeviceDTO the entity to save
     * @return the persisted entity
     */
    CifDeviceDTO save(CifDeviceDTO cifDeviceDTO);

    /**
     *  Get all the cifDevices.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CifDeviceDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" cifDevice.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CifDeviceDTO findOne(Long id);

    /**
     *  Delete the "id" cifDevice.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the cifDevice corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CifDeviceDTO> search(String query, Pageable pageable);
}
