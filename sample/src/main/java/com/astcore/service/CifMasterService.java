package com.astcore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.astcore.service.dto.CifMasterDTO;

/**
 * Service Interface for managing CifMaster.
 */
public interface CifMasterService {

    /**
     * Save a cifMaster.
     *
     * @param cifMasterDTO the entity to save
     * @return the persisted entity
     */
    CifMasterDTO save(CifMasterDTO cifMasterDTO);

    /**
     *  Get all the cifMasters.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CifMasterDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" cifMaster.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CifMasterDTO findOne(Long id);

    /**
     *  Delete the "id" cifMaster.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the cifMaster corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CifMasterDTO> search(String query, Pageable pageable);
    
    /**
     *  Get the "user login" customer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CifMasterDTO findOneByUserLoged();
}
