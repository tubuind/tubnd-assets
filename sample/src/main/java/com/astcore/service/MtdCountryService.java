package com.astcore.service;

import com.astcore.service.dto.MtdCountryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MtdCountry.
 */
public interface MtdCountryService {

    /**
     * Save a mtdCountry.
     *
     * @param mtdCountryDTO the entity to save
     * @return the persisted entity
     */
    MtdCountryDTO save(MtdCountryDTO mtdCountryDTO);

    /**
     *  Get all the mtdCountries.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdCountryDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" mtdCountry.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MtdCountryDTO findOne(Long id);

    /**
     *  Delete the "id" mtdCountry.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the mtdCountry corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdCountryDTO> search(String query, Pageable pageable);
}
