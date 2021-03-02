package com.astcore.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astcore.domain.MtdWard;
import com.astcore.repository.MtdWardRepository;
import com.astcore.repository.search.MtdWardSearchRepository;
import com.astcore.security.SecurityUtils;
import com.astcore.service.MtdWardService;
import com.astcore.service.dto.MtdWardDTO;
import com.astcore.service.mapper.MtdWardMapper;

/**
 * Service Implementation for managing MtdWard.
 */
@Service
@Transactional
public class MtdWardServiceImpl implements MtdWardService{

    private final Logger log = LoggerFactory.getLogger(MtdWardServiceImpl.class);

    private final MtdWardRepository mtdWardRepository;

    private final MtdWardMapper mtdWardMapper;

    private final MtdWardSearchRepository mtdWardSearchRepository;

    public MtdWardServiceImpl(MtdWardRepository mtdWardRepository, MtdWardMapper mtdWardMapper, MtdWardSearchRepository mtdWardSearchRepository) {
        this.mtdWardRepository = mtdWardRepository;
        this.mtdWardMapper = mtdWardMapper;
        this.mtdWardSearchRepository = mtdWardSearchRepository;
    }

    /**
     * Save a mtdWard.
     *
     * @param mtdWardDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MtdWardDTO save(MtdWardDTO mtdWardDTO) {
        log.debug("Request to save MtdWard : {}", mtdWardDTO);
        MtdWard mtdWard = mtdWardMapper.toEntity(mtdWardDTO);
        
        //set UserId, CreateDate
        if (mtdWard.getId() == null) {
        	mtdWard.setIsdel(0);
        	mtdWard.setCreateby(SecurityUtils.getCurrentUserLogin());
        	mtdWard.setCreatedate(new Date());
        }
        mtdWard.setLastmodifyby(SecurityUtils.getCurrentUserLogin());
        mtdWard.setLastmodifydate(new Date());
        
        mtdWard = mtdWardRepository.save(mtdWard);
        MtdWardDTO result = mtdWardMapper.toDto(mtdWard);
        mtdWardSearchRepository.save(mtdWard);
        return result;
    }

    /**
     *  Get all the mtdWards.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdWardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MtdWards");
        return mtdWardRepository.findAll(pageable)
            .map(mtdWardMapper::toDto);
    }

    /**
     *  Get one mtdWard by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MtdWardDTO findOne(Long id) {
        log.debug("Request to get MtdWard : {}", id);
        MtdWard mtdWard = mtdWardRepository.findOne(id);
        return mtdWardMapper.toDto(mtdWard);
    }

    /**
     *  Delete the  mtdWard by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MtdWard : {}", id);
        
        //Only update Isdel
        MtdWard item = mtdWardRepository.getOne(id);
        item.setIsdel(1);
        mtdWardRepository.save(item);
        
        //mtdWardRepository.delete(id);
        //mtdWardSearchRepository.delete(id);
    }

    /**
     * Search for the mtdWard corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdWardDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MtdWards for query {}", query);
        //Page<MtdWard> result = mtdWardSearchRepository.search(queryStringQuery(query), pageable);
        Page<MtdWard> result = mtdWardRepository.findByWardnameLikeIgnoreCaseAndIsdel(pageable, String.format("%s%s", query, "%"), 0);
        return result.map(mtdWardMapper::toDto);
    }
    
    /**
     * Search for the mtdWard corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdWardDTO> getWardByDistrict(Long idDistrict, Pageable pageable) {
        log.debug("Request to search for a page of MtdWards for idDistrict {}", idDistrict);
        Page<MtdWard> result = mtdWardRepository.getWardByDistrict(idDistrict, pageable);
        return result.map(mtdWardMapper::toDto);
    }
    
}
