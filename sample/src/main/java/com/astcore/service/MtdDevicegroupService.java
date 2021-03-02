package com.astcore.service;

import com.astcore.service.dto.MtdDevicegroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MtdDevicegroup.
 */
public interface MtdDevicegroupService {

    /**
     * Save a mtdDevicegroup.
     *
     * @param mtdDevicegroupDTO the entity to save
     * @return the persisted entity
     */
    MtdDevicegroupDTO save(MtdDevicegroupDTO mtdDevicegroupDTO);

    /**
     *  Get all the mtdDevicegroups.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdDevicegroupDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" mtdDevicegroup.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MtdDevicegroupDTO findOne(Long id);

    /**
     *  Delete the "id" mtdDevicegroup.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the mtdDevicegroup corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdDevicegroupDTO> search(String query, Pageable pageable);
}
