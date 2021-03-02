package com.astcore.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astcore.domain.MtdDevicegroup;
import com.astcore.repository.MtdDevicegroupRepository;
import com.astcore.repository.search.MtdDevicegroupSearchRepository;
import com.astcore.security.SecurityUtils;
import com.astcore.service.MtdDevicegroupService;
import com.astcore.service.dto.MtdDevicegroupDTO;
import com.astcore.service.mapper.MtdDevicegroupMapper;

/**
 * Service Implementation for managing MtdDevicegroup.
 */
@Service
@Transactional
public class MtdDevicegroupServiceImpl implements MtdDevicegroupService{

    private final Logger log = LoggerFactory.getLogger(MtdDevicegroupServiceImpl.class);

    private final MtdDevicegroupRepository mtdDevicegroupRepository;

    private final MtdDevicegroupMapper mtdDevicegroupMapper;

    private final MtdDevicegroupSearchRepository mtdDevicegroupSearchRepository;

    public MtdDevicegroupServiceImpl(MtdDevicegroupRepository mtdDevicegroupRepository, MtdDevicegroupMapper mtdDevicegroupMapper, MtdDevicegroupSearchRepository mtdDevicegroupSearchRepository) {
        this.mtdDevicegroupRepository = mtdDevicegroupRepository;
        this.mtdDevicegroupMapper = mtdDevicegroupMapper;
        this.mtdDevicegroupSearchRepository = mtdDevicegroupSearchRepository;
    }

    /**
     * Save a mtdDevicegroup.
     *
     * @param mtdDevicegroupDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MtdDevicegroupDTO save(MtdDevicegroupDTO mtdDevicegroupDTO) {
        log.debug("Request to save MtdDevicegroup : {}", mtdDevicegroupDTO);
        MtdDevicegroup mtdDevicegroup = mtdDevicegroupMapper.toEntity(mtdDevicegroupDTO);
        
        //set UserId, CreateDate
        if (mtdDevicegroup.getId() == null) {
        	mtdDevicegroup.setIsdel(0);
        	mtdDevicegroup.setCreateby(SecurityUtils.getCurrentUserLogin());
        	mtdDevicegroup.setCreatedate(new Date());
        }
        mtdDevicegroup.setLastmodifyby(SecurityUtils.getCurrentUserLogin());
        mtdDevicegroup.setLastmodifydate(new Date());
        
        mtdDevicegroup = mtdDevicegroupRepository.save(mtdDevicegroup);
        MtdDevicegroupDTO result = mtdDevicegroupMapper.toDto(mtdDevicegroup);
        mtdDevicegroupSearchRepository.save(mtdDevicegroup);
        return result;
    }

    /**
     *  Get all the mtdDevicegroups.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdDevicegroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MtdDevicegroups");
        return mtdDevicegroupRepository.findAll(pageable)
            .map(mtdDevicegroupMapper::toDto);
    }

    /**
     *  Get one mtdDevicegroup by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MtdDevicegroupDTO findOne(Long id) {
        log.debug("Request to get MtdDevicegroup : {}", id);
        MtdDevicegroup mtdDevicegroup = mtdDevicegroupRepository.findOne(id);
        return mtdDevicegroupMapper.toDto(mtdDevicegroup);
    }

    /**
     *  Delete the  mtdDevicegroup by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MtdDevicegroup : {}", id);
        
        //Only update Isdel
        MtdDevicegroup item = mtdDevicegroupRepository.getOne(id);
        item.setIsdel(1);
        mtdDevicegroupRepository.save(item);
        
        //mtdDevicegroupRepository.delete(id);
        //mtdDevicegroupSearchRepository.delete(id);
    }

    /**
     * Search for the mtdDevicegroup corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdDevicegroupDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MtdDevicegroups for query {}", query);
        //Page<MtdDevicegroup> result = mtdDevicegroupSearchRepository.search(queryStringQuery(query), pageable);
        Page<MtdDevicegroup> result = mtdDevicegroupRepository.findByDevicegroupnameLikeIgnoreCaseAndIsdel(pageable, String.format("%s%s", query, "%"), 0);
        return result.map(mtdDevicegroupMapper::toDto);
    }
}
