package com.astcore.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astcore.domain.MtdCountry;
import com.astcore.repository.MtdCountryRepository;
import com.astcore.repository.search.MtdCountrySearchRepository;
import com.astcore.security.SecurityUtils;
import com.astcore.service.MtdCountryService;
import com.astcore.service.dto.MtdCountryDTO;
import com.astcore.service.mapper.MtdCountryMapper;

/**
 * Service Implementation for managing MtdCountry.
 */
@Service
@Transactional
public class MtdCountryServiceImpl implements MtdCountryService{

    private final Logger log = LoggerFactory.getLogger(MtdCountryServiceImpl.class);

    private final MtdCountryRepository mtdCountryRepository;

    private final MtdCountryMapper mtdCountryMapper;

    private final MtdCountrySearchRepository mtdCountrySearchRepository;

    public MtdCountryServiceImpl(MtdCountryRepository mtdCountryRepository, MtdCountryMapper mtdCountryMapper, MtdCountrySearchRepository mtdCountrySearchRepository) {
        this.mtdCountryRepository = mtdCountryRepository;
        this.mtdCountryMapper = mtdCountryMapper;
        this.mtdCountrySearchRepository = mtdCountrySearchRepository;
    }

    /**
     * Save a mtdCountry.
     *
     * @param mtdCountryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MtdCountryDTO save(MtdCountryDTO mtdCountryDTO) {
        log.debug("Request to save MtdCountry : {}", mtdCountryDTO);
        MtdCountry mtdCountry = mtdCountryMapper.toEntity(mtdCountryDTO);
        
        //set UserId, CreateDate
        if (mtdCountry.getId() == null) {
        	mtdCountry.setIsdel(0);
        	mtdCountry.setCreateby(SecurityUtils.getCurrentUserLogin());
        	mtdCountry.setCreatedate(new Date());
        }
        mtdCountry.setLastmodifyby(SecurityUtils.getCurrentUserLogin());
        mtdCountry.setLastmodifydate(new Date());
        
        mtdCountry = mtdCountryRepository.save(mtdCountry);
        MtdCountryDTO result = mtdCountryMapper.toDto(mtdCountry);
        mtdCountrySearchRepository.save(mtdCountry);
        return result;
    }

    /**
     *  Get all the mtdCountries.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdCountryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MtdCountries");
        return mtdCountryRepository.findAll(pageable)
            .map(mtdCountryMapper::toDto);
    }

    /**
     *  Get one mtdCountry by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MtdCountryDTO findOne(Long id) {
        log.debug("Request to get MtdCountry : {}", id);
        MtdCountry mtdCountry = mtdCountryRepository.findOne(id);
        return mtdCountryMapper.toDto(mtdCountry);
    }

    /**
     *  Delete the  mtdCountry by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MtdCountry : {}", id);
        
        //Only update Isdel
        MtdCountry item = mtdCountryRepository.getOne(id);
        item.setIsdel(1);
        mtdCountryRepository.save(item);
        
        //mtdCountryRepository.delete(id);
        //mtdCountrySearchRepository.delete(id);
    }

    /**
     * Search for the mtdCountry corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdCountryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MtdCountries for query {}", query);
        //Page<MtdCountry> result = mtdCountrySearchRepository.search(queryStringQuery(query), pageable);
        Page<MtdCountry> result = mtdCountryRepository.findByCountrynameLikeIgnoreCaseAndIsdel(pageable, String.format("%s%s", query, "%"), 0);
        return result.map(mtdCountryMapper::toDto);
    }
}
