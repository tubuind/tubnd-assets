package com.astcore.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astcore.domain.MtdProvince;
import com.astcore.repository.MtdProvinceRepository;
import com.astcore.repository.search.MtdProvinceSearchRepository;
import com.astcore.security.SecurityUtils;
import com.astcore.service.MtdProvinceService;
import com.astcore.service.dto.MtdProvinceDTO;
import com.astcore.service.mapper.MtdProvinceMapper;

/**
 * Service Implementation for managing MtdProvince.
 */
@Service
@Transactional
public class MtdProvinceServiceImpl implements MtdProvinceService{

    private final Logger log = LoggerFactory.getLogger(MtdProvinceServiceImpl.class);

    private final MtdProvinceRepository mtdProvinceRepository;

    private final MtdProvinceMapper mtdProvinceMapper;

    private final MtdProvinceSearchRepository mtdProvinceSearchRepository;

    public MtdProvinceServiceImpl(MtdProvinceRepository mtdProvinceRepository, MtdProvinceMapper mtdProvinceMapper, MtdProvinceSearchRepository mtdProvinceSearchRepository) {
        this.mtdProvinceRepository = mtdProvinceRepository;
        this.mtdProvinceMapper = mtdProvinceMapper;
        this.mtdProvinceSearchRepository = mtdProvinceSearchRepository;
    }

    /**
     * Save a mtdProvince.
     *
     * @param mtdProvinceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MtdProvinceDTO save(MtdProvinceDTO mtdProvinceDTO) {
        log.debug("Request to save MtdProvince : {}", mtdProvinceDTO);
        MtdProvince mtdProvince = mtdProvinceMapper.toEntity(mtdProvinceDTO);
        
        //set UserId, CreateDate
        if (mtdProvince.getId() == null) {
        	mtdProvince.setIsdel(0);
        	mtdProvince.setCreateby(SecurityUtils.getCurrentUserLogin());
        	mtdProvince.setCreatedate(new Date());
        }
        mtdProvince.setLastmodifyby(SecurityUtils.getCurrentUserLogin());
        mtdProvince.setLastmodifydate(new Date());
        
        mtdProvince = mtdProvinceRepository.save(mtdProvince);
        MtdProvinceDTO result = mtdProvinceMapper.toDto(mtdProvince);
        mtdProvinceSearchRepository.save(mtdProvince);
        return result;
    }

    /**
     *  Get all the mtdProvinces.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdProvinceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MtdProvinces");
        return mtdProvinceRepository.findAll(pageable)
            .map(mtdProvinceMapper::toDto);
    }

    /**
     *  Get one mtdProvince by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MtdProvinceDTO findOne(Long id) {
        log.debug("Request to get MtdProvince : {}", id);
        MtdProvince mtdProvince = mtdProvinceRepository.findOne(id);
        return mtdProvinceMapper.toDto(mtdProvince);
    }

    /**
     *  Delete the  mtdProvince by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MtdProvince : {}", id);
        
        //Only update Isdel
        MtdProvince item = mtdProvinceRepository.getOne(id);
        item.setIsdel(1);
        mtdProvinceRepository.save(item);
        
        //mtdProvinceRepository.delete(id);
        //mtdProvinceSearchRepository.delete(id);
    }

    /**
     * Search for the mtdProvince corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdProvinceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MtdProvinces for query {}", query);
        //Page<MtdProvince> result = mtdProvinceSearchRepository.search(queryStringQuery(query), pageable);
        Page<MtdProvince> result = mtdProvinceRepository.findByProvincenameLikeIgnoreCaseAndIsdel(pageable, String.format("%s%s", query, "%"), 0);
        return result.map(mtdProvinceMapper::toDto);
    }
    
    /**
     * Search for the mtdProvince corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdProvinceDTO> getProvinceByCountry(Long idCountry, Pageable pageable) {
        log.debug("Request to search for a page of MtdProvinces for idCountry {}", idCountry);
        Page<MtdProvince> result = mtdProvinceRepository.getProvinceByCountry(idCountry, pageable);
        return result.map(mtdProvinceMapper::toDto);
    }
}
