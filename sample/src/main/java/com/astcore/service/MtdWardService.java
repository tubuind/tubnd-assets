package com.astcore.service;

import com.astcore.service.dto.MtdDistrictDTO;
import com.astcore.service.dto.MtdWardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MtdWard.
 */
public interface MtdWardService {

    /**
     * Save a mtdWard.
     *
     * @param mtdWardDTO the entity to save
     * @return the persisted entity
     */
    MtdWardDTO save(MtdWardDTO mtdWardDTO);

    /**
     *  Get all the mtdWards.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdWardDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" mtdWard.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MtdWardDTO findOne(Long id);

    /**
     *  Delete the "id" mtdWard.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the mtdWard corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdWardDTO> search(String query, Pageable pageable);
    
    /**
     * Search for the mtdDistrict corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdWardDTO> getWardByDistrict(Long idDistrict, Pageable pageable);
}
