package com.astcore.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astcore.domain.MtdDevicetype;
import com.astcore.repository.MtdDevicetypeRepository;
import com.astcore.repository.search.MtdDevicetypeSearchRepository;
import com.astcore.security.SecurityUtils;
import com.astcore.service.MtdDevicetypeService;
import com.astcore.service.dto.MtdDevicetypeDTO;
import com.astcore.service.mapper.MtdDevicetypeMapper;

/**
 * Service Implementation for managing MtdDevicetype.
 */
@Service
@Transactional
public class MtdDevicetypeServiceImpl implements MtdDevicetypeService{

    private final Logger log = LoggerFactory.getLogger(MtdDevicetypeServiceImpl.class);

    private final MtdDevicetypeRepository mtdDevicetypeRepository;

    private final MtdDevicetypeMapper mtdDevicetypeMapper;

    private final MtdDevicetypeSearchRepository mtdDevicetypeSearchRepository;

    public MtdDevicetypeServiceImpl(MtdDevicetypeRepository mtdDevicetypeRepository, MtdDevicetypeMapper mtdDevicetypeMapper, MtdDevicetypeSearchRepository mtdDevicetypeSearchRepository) {
        this.mtdDevicetypeRepository = mtdDevicetypeRepository;
        this.mtdDevicetypeMapper = mtdDevicetypeMapper;
        this.mtdDevicetypeSearchRepository = mtdDevicetypeSearchRepository;
    }

    /**
     * Save a mtdDevicetype.
     *
     * @param mtdDevicetypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MtdDevicetypeDTO save(MtdDevicetypeDTO mtdDevicetypeDTO) {
        log.debug("Request to save MtdDevicetype : {}", mtdDevicetypeDTO);
        MtdDevicetype mtdDevicetype = mtdDevicetypeMapper.toEntity(mtdDevicetypeDTO);
        
        //set UserId, CreateDate
        if (mtdDevicetype.getId() == null) {
        	mtdDevicetype.setIsdel(0);
        	mtdDevicetype.setCreateby(SecurityUtils.getCurrentUserLogin());
        	mtdDevicetype.setCreatedate(new Date());
        }
        mtdDevicetype.setLastmodifyby(SecurityUtils.getCurrentUserLogin());
        mtdDevicetype.setLastmodifydate(new Date());
        
        mtdDevicetype = mtdDevicetypeRepository.save(mtdDevicetype);
        MtdDevicetypeDTO result = mtdDevicetypeMapper.toDto(mtdDevicetype);
        mtdDevicetypeSearchRepository.save(mtdDevicetype);
        return result;
    }

    /**
     *  Get all the mtdDevicetypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdDevicetypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MtdDevicetypes");
        return mtdDevicetypeRepository.findAll(pageable)
            .map(mtdDevicetypeMapper::toDto);
    }

    /**
     *  Get one mtdDevicetype by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MtdDevicetypeDTO findOne(Long id) {
        log.debug("Request to get MtdDevicetype : {}", id);
        MtdDevicetype mtdDevicetype = mtdDevicetypeRepository.findOne(id);
        return mtdDevicetypeMapper.toDto(mtdDevicetype);
    }

    /**
     *  Delete the  mtdDevicetype by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MtdDevicetype : {}", id);
        
        //Only update Isdel
        MtdDevicetype item = mtdDevicetypeRepository.getOne(id);
        item.setIsdel(1);
        mtdDevicetypeRepository.save(item);
        
        //mtdDevicetypeRepository.delete(id);
        //mtdDevicetypeSearchRepository.delete(id);
    }

    /**
     * Search for the mtdDevicetype corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdDevicetypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MtdDevicetypes for query {}", query);
        //Page<MtdDevicetype> result = mtdDevicetypeSearchRepository.search(queryStringQuery(query), pageable);
        Page<MtdDevicetype> result = mtdDevicetypeRepository.findByDevicetypenameLikeIgnoreCaseAndIsdel(pageable, String.format("%s%s", query, "%"), 0);
        return result.map(mtdDevicetypeMapper::toDto);
    }
}
