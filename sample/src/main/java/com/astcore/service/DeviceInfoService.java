package com.astcore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.astcore.service.dto.DeviceInfoDTO;

/**
 * Service Interface for managing DeviceInfo.
 */
public interface DeviceInfoService {

    /**
     * Save a deviceInfo.
     *
     * @param deviceInfoDTO the entity to save
     * @return the persisted entity
     */
    DeviceInfoDTO save(DeviceInfoDTO deviceInfoDTO);

    /**
     *  Get all the deviceInfos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DeviceInfoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" deviceInfo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DeviceInfoDTO findOne(Long id);

    /**
     *  Delete the "id" deviceInfo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the deviceInfo corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DeviceInfoDTO> search(String query, Pageable pageable);
    
    /**
     *  Get all the devices.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DeviceInfoDTO> getDevicesByUserLoged(Pageable pageable);
}
