package com.astcore.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astcore.domain.CifArea;
import com.astcore.domain.CifMaster;
import com.astcore.repository.CifAreaRepository;
import com.astcore.repository.CifMasterRepository;
import com.astcore.repository.search.CifAreaSearchRepository;
import com.astcore.security.SecurityUtils;
import com.astcore.service.CifAreaService;
import com.astcore.service.dto.CifAreaDTO;
import com.astcore.service.mapper.CifAreaMapper;

/**
 * Service Implementation for managing CifArea.
 */
@Service
@Transactional
public class CifAreaServiceImpl implements CifAreaService{

    private final Logger log = LoggerFactory.getLogger(CifAreaServiceImpl.class);

    private final CifAreaRepository cifAreaRepository;
    
    private final CifMasterRepository cifMasterRepository;

    private final CifAreaMapper cifAreaMapper;

    private final CifAreaSearchRepository cifAreaSearchRepository;

    public CifAreaServiceImpl(CifAreaRepository cifAreaRepository, CifMasterRepository cifMasterRepository, CifAreaMapper cifAreaMapper, CifAreaSearchRepository cifAreaSearchRepository) {
        this.cifAreaRepository = cifAreaRepository;
        this.cifMasterRepository = cifMasterRepository;
        this.cifAreaMapper = cifAreaMapper;
        this.cifAreaSearchRepository = cifAreaSearchRepository;
    }

    /**
     * Save a cifArea.
     *
     * @param cifAreaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CifAreaDTO save(CifAreaDTO cifAreaDTO) {
        log.debug("Request to save CifArea : {}", cifAreaDTO);
        
        CifMaster cifMaster = cifMasterRepository.findOneByUserLoginUname(SecurityUtils.getCurrentUserLogin());
        
        if (cifMaster != null) {
        	cifAreaDTO.setCifMasterId(cifMaster.getId());
        	CifArea cifArea = cifAreaMapper.toEntity(cifAreaDTO);
        	
        	//set UserId, CreateDate
            if (cifArea.getId() == null) {
            	cifArea.setIsdel(0);
            	cifArea.setCreateby(SecurityUtils.getCurrentUserLogin());
            	cifArea.setCreatedate(new Date());
            }
            cifArea.setLastmodifyby(SecurityUtils.getCurrentUserLogin());
            cifArea.setLastmodifydate(new Date());
            
            cifArea = cifAreaRepository.save(cifArea);
            CifAreaDTO result = cifAreaMapper.toDto(cifArea);
            cifAreaSearchRepository.save(cifArea);
            return result;
        }
 
        return null;
    }

    /**
     *  Get all the cifAreas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CifAreaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CifAreas");
        return cifAreaRepository.findAll(pageable)
            .map(cifAreaMapper::toDto);
    }

    /**
     *  Get one cifArea by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CifAreaDTO findOne(Long id) {
        log.debug("Request to get CifArea : {}", id);
        CifArea cifArea = cifAreaRepository.findOne(id);
        return cifAreaMapper.toDto(cifArea);
    }

    /**
     *  Delete the  cifArea by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CifArea : {}", id);
        cifAreaRepository.delete(id);
        cifAreaSearchRepository.delete(id);
    }

    /**
     * Search for the cifArea corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CifAreaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CifAreas for query {}", query);
        //Page<CifArea> result = cifAreaSearchRepository.search(queryStringQuery(query), pageable);
        Page<CifArea> result = cifAreaRepository.findByCifareanameLikeIgnoreCaseAndIsdel(pageable, String.format("%s%s", query, "%"), 0);
        return result.map(cifAreaMapper::toDto);
    }
    
    /**
     *  Get all the devices.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
	public Page<CifAreaDTO> getAreasByUserLoged(Pageable pageable) {
    	log.debug("Request to get all Areas : getAreasByUserLoged");
    	Page<CifArea> result = cifAreaRepository.getAreasByUname(SecurityUtils.getCurrentUserLogin(), pageable);
        return result.map(cifAreaMapper::toDto);
	}
}
