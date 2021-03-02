package com.astcore.service;

import com.astcore.service.dto.MtdDistrictDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MtdDistrict.
 */
public interface MtdDistrictService {

    /**
     * Save a mtdDistrict.
     *
     * @param mtdDistrictDTO the entity to save
     * @return the persisted entity
     */
    MtdDistrictDTO save(MtdDistrictDTO mtdDistrictDTO);

    /**
     *  Get all the mtdDistricts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdDistrictDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" mtdDistrict.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MtdDistrictDTO findOne(Long id);

    /**
     *  Delete the "id" mtdDistrict.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the mtdDistrict corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdDistrictDTO> search(String query, Pageable pageable);
    
    /**
     * Search for the mtdDistrict corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MtdDistrictDTO> getDistrictByProvince(Long idProvince, Pageable pageable);
}
