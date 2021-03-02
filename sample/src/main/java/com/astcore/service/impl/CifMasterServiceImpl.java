package com.astcore.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astcore.domain.CifMaster;
import com.astcore.repository.CifMasterRepository;
import com.astcore.repository.search.CifMasterSearchRepository;
import com.astcore.security.SecurityUtils;
import com.astcore.service.CifMasterService;
import com.astcore.service.dto.CifMasterDTO;
import com.astcore.service.mapper.CifMasterMapper;

/**
 * Service Implementation for managing CifMaster.
 */
@Service
@Transactional
public class CifMasterServiceImpl implements CifMasterService{

    private final Logger log = LoggerFactory.getLogger(CifMasterServiceImpl.class);

    private final CifMasterRepository cifMasterRepository;

    private final CifMasterMapper cifMasterMapper;

    private final CifMasterSearchRepository cifMasterSearchRepository;

    public CifMasterServiceImpl(CifMasterRepository cifMasterRepository, CifMasterMapper cifMasterMapper, CifMasterSearchRepository cifMasterSearchRepository) {
        this.cifMasterRepository = cifMasterRepository;
        this.cifMasterMapper = cifMasterMapper;
        this.cifMasterSearchRepository = cifMasterSearchRepository;
    }

    /**
     * Save a cifMaster.
     *
     * @param cifMasterDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CifMasterDTO save(CifMasterDTO cifMasterDTO) {
        log.debug("Request to save CifMaster : {}", cifMasterDTO);
        CifMaster cifMaster = cifMasterMapper.toEntity(cifMasterDTO);
        
        //set UserId, CreateDate
        if (cifMaster.getId() == null) {
        	cifMaster.setIsdel(0);
        	cifMaster.setCreateby(SecurityUtils.getCurrentUserLogin());
        	cifMaster.setCreatedate(new Date());
        }
        cifMaster.setLastmodifyby(SecurityUtils.getCurrentUserLogin());
        cifMaster.setLastmodifydate(new Date());
        
        cifMaster = cifMasterRepository.save(cifMaster);
        CifMasterDTO result = cifMasterMapper.toDto(cifMaster);
        cifMasterSearchRepository.save(cifMaster);
        return result;
    }

    /**
     *  Get all the cifMasters.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CifMasterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CifMasters");
        return cifMasterRepository.findAll(pageable)
            .map(cifMasterMapper::toDto);
    }

    /**
     *  Get one cifMaster by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CifMasterDTO findOne(Long id) {
        log.debug("Request to get CifMaster : {}", id);
        CifMaster cifMaster = cifMasterRepository.findOne(id);
        return cifMasterMapper.toDto(cifMaster);
    }

    /**
     *  Delete the  cifMaster by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CifMaster : {}", id);
        
        //Only update Isdel
        CifMaster item = cifMasterRepository.getOne(id);
        item.setIsdel(1);
        cifMasterRepository.save(item);
        
        //cifMasterRepository.delete(id);
        //cifMasterSearchRepository.delete(id);
    }

    /**
     * Search for the cifMaster corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CifMasterDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CifMasters for query {}", query);
        //Page<CifMaster> result = cifMasterSearchRepository.search(queryStringQuery(query), pageable);
        Page<CifMaster> result = cifMasterRepository.findByCustomernameLikeIgnoreCaseAndIsdel(pageable, String.format("%s%s", query, "%"), 0);
        return result.map(cifMasterMapper::toDto);
    }
    
    /**
     *  Get one customer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
	public CifMasterDTO findOneByUserLoged() {
    	log.debug("Request to get Customer by findOneByUserLoged : {}", SecurityUtils.getCurrentUserLogin());
    	CifMaster cifMaster = cifMasterRepository.findOneByUserLoginUname(SecurityUtils.getCurrentUserLogin());
        return cifMasterMapper.toDto(cifMaster);
	}
}
