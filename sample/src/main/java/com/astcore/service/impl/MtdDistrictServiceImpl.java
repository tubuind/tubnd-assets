package com.astcore.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astcore.domain.MtdDistrict;
import com.astcore.repository.MtdDistrictRepository;
import com.astcore.repository.search.MtdDistrictSearchRepository;
import com.astcore.security.SecurityUtils;
import com.astcore.service.MtdDistrictService;
import com.astcore.service.dto.MtdDistrictDTO;
import com.astcore.service.mapper.MtdDistrictMapper;

/**
 * Service Implementation for managing MtdDistrict.
 */
@Service
@Transactional
public class MtdDistrictServiceImpl implements MtdDistrictService{

    private final Logger log = LoggerFactory.getLogger(MtdDistrictServiceImpl.class);

    private final MtdDistrictRepository mtdDistrictRepository;

    private final MtdDistrictMapper mtdDistrictMapper;

    private final MtdDistrictSearchRepository mtdDistrictSearchRepository;

    public MtdDistrictServiceImpl(MtdDistrictRepository mtdDistrictRepository, MtdDistrictMapper mtdDistrictMapper, MtdDistrictSearchRepository mtdDistrictSearchRepository) {
        this.mtdDistrictRepository = mtdDistrictRepository;
        this.mtdDistrictMapper = mtdDistrictMapper;
        this.mtdDistrictSearchRepository = mtdDistrictSearchRepository;
    }

    /**
     * Save a mtdDistrict.
     *
     * @param mtdDistrictDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MtdDistrictDTO save(MtdDistrictDTO mtdDistrictDTO) {
        log.debug("Request to save MtdDistrict : {}", mtdDistrictDTO);
        MtdDistrict mtdDistrict = mtdDistrictMapper.toEntity(mtdDistrictDTO);
        
        //set UserId, CreateDate
        if (mtdDistrict.getId() == null) {
        	mtdDistrict.setIsdel(0);
        	mtdDistrict.setCreateby(SecurityUtils.getCurrentUserLogin());
        	mtdDistrict.setCreatedate(new Date());
        }
        mtdDistrict.setLastmodifyby(SecurityUtils.getCurrentUserLogin());
        mtdDistrict.setLastmodifydate(new Date());
        
        mtdDistrict = mtdDistrictRepository.save(mtdDistrict);
        MtdDistrictDTO result = mtdDistrictMapper.toDto(mtdDistrict);
        mtdDistrictSearchRepository.save(mtdDistrict);
        return result;
    }

    /**
     *  Get all the mtdDistricts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdDistrictDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MtdDistricts");
        return mtdDistrictRepository.findAll(pageable)
            .map(mtdDistrictMapper::toDto);
    }

    /**
     *  Get one mtdDistrict by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MtdDistrictDTO findOne(Long id) {
        log.debug("Request to get MtdDistrict : {}", id);
        MtdDistrict mtdDistrict = mtdDistrictRepository.findOne(id);
        return mtdDistrictMapper.toDto(mtdDistrict);
    }

    /**
     *  Delete the  mtdDistrict by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MtdDistrict : {}", id);
        
        //Only update Isdel
        MtdDistrict item = mtdDistrictRepository.getOne(id);
        item.setIsdel(1);
        mtdDistrictRepository.save(item);
        
        //mtdDistrictRepository.delete(id);
        //mtdDistrictSearchRepository.delete(id);
    }

    /**
     * Search for the mtdDistrict corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdDistrictDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MtdDistricts for query {}", query);
        //Page<MtdDistrict> result = mtdDistrictSearchRepository.search(queryStringQuery(query), pageable);
        Page<MtdDistrict> result = mtdDistrictRepository.findByDistrictnameLikeIgnoreCaseAndIsdel(pageable, String.format("%s%s", query, "%"), 0);
        return result.map(mtdDistrictMapper::toDto);
    }
    
    /**
     * Search for the mtdDistrict corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdDistrictDTO> getDistrictByProvince(Long idProvince, Pageable pageable) {
        log.debug("Request to search for a page of MtdDistricts for idProvince {}", idProvince);
        Page<MtdDistrict> result = mtdDistrictRepository.getDistrictByProvince(idProvince, pageable);
        return result.map(mtdDistrictMapper::toDto);
    }
}
