package com.astcore.service;

import com.astcore.service.dto.MtdCustomergroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MtdCustomergroup.
 */
public interface MtdCustomergroupService {

    /**
     * Save a mtdCustomergroup.
     *
     * @param mtdCustomergroupDTO the entity to save
     * @return the persisted entity
     */
    MtdCustomergroupDTO save(MtdCustomergroupDTO mtdCustomergroupDTO);

    /**
     *  Get all the mtdCustomergroups.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdCustomergroupDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" mtdCustomergroup.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MtdCustomergroupDTO findOne(Long id);

    /**
     *  Delete the "id" mtdCustomergroup.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the mtdCustomergroup corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdCustomergroupDTO> search(String query, Pageable pageable);
}
