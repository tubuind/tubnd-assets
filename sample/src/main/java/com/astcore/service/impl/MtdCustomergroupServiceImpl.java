package com.astcore.service.impl;

import com.astcore.service.MtdCustomergroupService;
import com.astcore.domain.MtdCustomergroup;
import com.astcore.domain.MtdOrganization;
import com.astcore.repository.MtdCustomergroupRepository;
import com.astcore.repository.search.MtdCustomergroupSearchRepository;
import com.astcore.security.SecurityUtils;
import com.astcore.service.dto.MtdCustomergroupDTO;
import com.astcore.service.mapper.MtdCustomergroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.Date;

/**
 * Service Implementation for managing MtdCustomergroup.
 */
@Service
@Transactional
public class MtdCustomergroupServiceImpl implements MtdCustomergroupService{

    private final Logger log = LoggerFactory.getLogger(MtdCustomergroupServiceImpl.class);

    private final MtdCustomergroupRepository mtdCustomergroupRepository;

    private final MtdCustomergroupMapper mtdCustomergroupMapper;

    private final MtdCustomergroupSearchRepository mtdCustomergroupSearchRepository;

    public MtdCustomergroupServiceImpl(MtdCustomergroupRepository mtdCustomergroupRepository, MtdCustomergroupMapper mtdCustomergroupMapper, MtdCustomergroupSearchRepository mtdCustomergroupSearchRepository) {
        this.mtdCustomergroupRepository = mtdCustomergroupRepository;
        this.mtdCustomergroupMapper = mtdCustomergroupMapper;
        this.mtdCustomergroupSearchRepository = mtdCustomergroupSearchRepository;
    }

    /**
     * Save a mtdCustomergroup.
     *
     * @param mtdCustomergroupDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MtdCustomergroupDTO save(MtdCustomergroupDTO mtdCustomergroupDTO) {
        log.debug("Request to save MtdCustomergroup : {}", mtdCustomergroupDTO);
        MtdCustomergroup mtdCustomergroup = mtdCustomergroupMapper.toEntity(mtdCustomergroupDTO);
        
        //set UserId, CreateDate
        if (mtdCustomergroup.getId() == null) {
        	mtdCustomergroup.setIsdel(0);
        	mtdCustomergroup.setCreateby(SecurityUtils.getCurrentUserLogin());
        	mtdCustomergroup.setCreatedate(new Date());
        }
        mtdCustomergroup.setLastmodifyby(SecurityUtils.getCurrentUserLogin());
        mtdCustomergroup.setLastmodifydate(new Date());
        
        mtdCustomergroup = mtdCustomergroupRepository.save(mtdCustomergroup);
        MtdCustomergroupDTO result = mtdCustomergroupMapper.toDto(mtdCustomergroup);
        mtdCustomergroupSearchRepository.save(mtdCustomergroup);
        return result;
    }

    /**
     *  Get all the mtdCustomergroups.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdCustomergroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MtdCustomergroups");
        return mtdCustomergroupRepository.findAll(pageable)
            .map(mtdCustomergroupMapper::toDto);
    }

    /**
     *  Get one mtdCustomergroup by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MtdCustomergroupDTO findOne(Long id) {
        log.debug("Request to get MtdCustomergroup : {}", id);
        MtdCustomergroup mtdCustomergroup = mtdCustomergroupRepository.findOne(id);
        return mtdCustomergroupMapper.toDto(mtdCustomergroup);
    }

    /**
     *  Delete the  mtdCustomergroup by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MtdCustomergroup : {}", id);
        
        //Only update Isdel
        MtdCustomergroup item = mtdCustomergroupRepository.getOne(id);
        item.setIsdel(1);
        mtdCustomergroupRepository.save(item);
        
        //mtdCustomergroupRepository.delete(id);
        //mtdCustomergroupSearchRepository.delete(id);
    }

    /**
     * Search for the mtdCustomergroup corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdCustomergroupDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MtdCustomergroups for query {}", query);
        //Page<MtdCustomergroup> result = mtdCustomergroupSearchRepository.search(queryStringQuery(query), pageable);
        Page<MtdCustomergroup> result = mtdCustomergroupRepository.findByCustgroupnameLikeIgnoreCaseAndIsdel(pageable, String.format("%s%s", query, "%"), 0);
        return result.map(mtdCustomergroupMapper::toDto);
    }
}
