package com.astcore.service;

import com.astcore.service.dto.MtdOrganizationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MtdOrganization.
 */
public interface MtdOrganizationService {

    /**
     * Save a mtdOrganization.
     *
     * @param mtdOrganizationDTO the entity to save
     * @return the persisted entity
     */
    MtdOrganizationDTO save(MtdOrganizationDTO mtdOrganizationDTO);

    /**
     *  Get all the mtdOrganizations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdOrganizationDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" mtdOrganization.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MtdOrganizationDTO findOne(Long id);

    /**
     *  Delete the "id" mtdOrganization.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the mtdOrganization corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdOrganizationDTO> search(String query, Pageable pageable);
}
