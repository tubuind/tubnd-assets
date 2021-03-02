package com.astcore.service;

import com.astcore.service.dto.MtdEcosectorsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MtdEcosectors.
 */
public interface MtdEcosectorsService {

    /**
     * Save a mtdEcosectors.
     *
     * @param mtdEcosectorsDTO the entity to save
     * @return the persisted entity
     */
    MtdEcosectorsDTO save(MtdEcosectorsDTO mtdEcosectorsDTO);

    /**
     *  Get all the mtdEcosectors.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdEcosectorsDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" mtdEcosectors.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MtdEcosectorsDTO findOne(Long id);

    /**
     *  Delete the "id" mtdEcosectors.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the mtdEcosectors corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdEcosectorsDTO> search(String query, Pageable pageable);
}
