package com.astcore.service;

import com.astcore.service.dto.MtdProvinceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MtdProvince.
 */
public interface MtdProvinceService {

    /**
     * Save a mtdProvince.
     *
     * @param mtdProvinceDTO the entity to save
     * @return the persisted entity
     */
    MtdProvinceDTO save(MtdProvinceDTO mtdProvinceDTO);

    /**
     *  Get all the mtdProvinces.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdProvinceDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" mtdProvince.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MtdProvinceDTO findOne(Long id);

    /**
     *  Delete the "id" mtdProvince.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the mtdProvince corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdProvinceDTO> search(String query, Pageable pageable);
    
    /**
     * Search for the mtdProvince corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdProvinceDTO> getProvinceByCountry(Long idCountry, Pageable pageable);
}
