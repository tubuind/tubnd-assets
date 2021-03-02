package com.astcore.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astcore.domain.MtdUnit;
import com.astcore.repository.MtdUnitRepository;
import com.astcore.repository.search.MtdUnitSearchRepository;
import com.astcore.security.SecurityUtils;
import com.astcore.service.MtdUnitService;
import com.astcore.service.dto.MtdUnitDTO;
import com.astcore.service.mapper.MtdUnitMapper;

/**
 * Service Implementation for managing MtdUnit.
 */
@Service
@Transactional
public class MtdUnitServiceImpl implements MtdUnitService{

    private final Logger log = LoggerFactory.getLogger(MtdUnitServiceImpl.class);

    private final MtdUnitRepository mtdUnitRepository;

    private final MtdUnitMapper mtdUnitMapper;

    private final MtdUnitSearchRepository mtdUnitSearchRepository;

    public MtdUnitServiceImpl(MtdUnitRepository mtdUnitRepository, MtdUnitMapper mtdUnitMapper, MtdUnitSearchRepository mtdUnitSearchRepository) {
        this.mtdUnitRepository = mtdUnitRepository;
        this.mtdUnitMapper = mtdUnitMapper;
        this.mtdUnitSearchRepository = mtdUnitSearchRepository;
    }

    /**
     * Save a mtdUnit.
     *
     * @param mtdUnitDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MtdUnitDTO save(MtdUnitDTO mtdUnitDTO) {
        log.debug("Request to save MtdUnit : {}", mtdUnitDTO);
        MtdUnit mtdUnit = mtdUnitMapper.toEntity(mtdUnitDTO);
        
        //set UserId, CreateDate
        if (mtdUnit.getId() == null) {
        	mtdUnit.setIsdel(0);
        	mtdUnit.setCreateby(SecurityUtils.getCurrentUserLogin());
        	mtdUnit.setCreatedate(new Date());
        }
        mtdUnit.setLastmodifyby(SecurityUtils.getCurrentUserLogin());
        mtdUnit.setLastmodifydate(new Date());
        
        mtdUnit = mtdUnitRepository.save(mtdUnit);
        MtdUnitDTO result = mtdUnitMapper.toDto(mtdUnit);
        mtdUnitSearchRepository.save(mtdUnit);
        return result;
    }

    /**
     *  Get all the mtdUnits.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdUnitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MtdUnits");
        return mtdUnitRepository.findAll(pageable)
            .map(mtdUnitMapper::toDto);
    }

    /**
     *  Get one mtdUnit by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MtdUnitDTO findOne(Long id) {
        log.debug("Request to get MtdUnit : {}", id);
        MtdUnit mtdUnit = mtdUnitRepository.findOne(id);
        return mtdUnitMapper.toDto(mtdUnit);
    }

    /**
     *  Delete the  mtdUnit by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MtdUnit : {}", id);
        
        //Only update Isdel
        MtdUnit item = mtdUnitRepository.getOne(id);
        item.setIsdel(1);
        mtdUnitRepository.save(item);
        
        //mtdUnitRepository.delete(id);
        //mtdUnitSearchRepository.delete(id);
    }

    /**
     * Search for the mtdUnit corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdUnitDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MtdUnits for query {}", query);
        //Page<MtdUnit> result = mtdUnitSearchRepository.search(queryStringQuery(query), pageable);
        Page<MtdUnit> result = mtdUnitRepository.findByUnitnameLikeIgnoreCaseAndIsdel(pageable, String.format("%s%s", query, "%"), 0);
        return result.map(mtdUnitMapper::toDto);
    }
}
