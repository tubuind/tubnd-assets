package com.astcore.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astcore.domain.MtdOrganization;
import com.astcore.repository.MtdOrganizationRepository;
import com.astcore.repository.search.MtdOrganizationSearchRepository;
import com.astcore.security.SecurityUtils;
import com.astcore.service.MtdOrganizationService;
import com.astcore.service.dto.MtdOrganizationDTO;
import com.astcore.service.mapper.MtdOrganizationMapper;

/**
 * Service Implementation for managing MtdOrganization.
 */
@Service
@Transactional
public class MtdOrganizationServiceImpl implements MtdOrganizationService{

    private final Logger log = LoggerFactory.getLogger(MtdOrganizationServiceImpl.class);

    private final MtdOrganizationRepository mtdOrganizationRepository;

    private final MtdOrganizationMapper mtdOrganizationMapper;

    private final MtdOrganizationSearchRepository mtdOrganizationSearchRepository;

    public MtdOrganizationServiceImpl(MtdOrganizationRepository mtdOrganizationRepository, MtdOrganizationMapper mtdOrganizationMapper, MtdOrganizationSearchRepository mtdOrganizationSearchRepository) {
        this.mtdOrganizationRepository = mtdOrganizationRepository;
        this.mtdOrganizationMapper = mtdOrganizationMapper;
        this.mtdOrganizationSearchRepository = mtdOrganizationSearchRepository;
    }

    /**
     * Save a mtdOrganization.
     *
     * @param mtdOrganizationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MtdOrganizationDTO save(MtdOrganizationDTO mtdOrganizationDTO) {
        log.debug("Request to save MtdOrganization : {}", mtdOrganizationDTO);
        MtdOrganization mtdOrganization = mtdOrganizationMapper.toEntity(mtdOrganizationDTO);
        
        //set UserId, CreateDate
        if (mtdOrganization.getId() == null) {
        	mtdOrganization.setIsdel(0);
        	mtdOrganization.setCreateby(SecurityUtils.getCurrentUserLogin());
        	mtdOrganization.setCreatedate(new Date());
        }
        mtdOrganization.setLastmodifyby(SecurityUtils.getCurrentUserLogin());
        mtdOrganization.setLastmodifydate(new Date());
        
        mtdOrganization = mtdOrganizationRepository.save(mtdOrganization);
        MtdOrganizationDTO result = mtdOrganizationMapper.toDto(mtdOrganization);
        mtdOrganizationSearchRepository.save(mtdOrganization);
        return result;
    }

    /**
     *  Get all the mtdOrganizations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdOrganizationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MtdOrganizations");
        return mtdOrganizationRepository.findAll(pageable)
            .map(mtdOrganizationMapper::toDto);
    }

    /**
     *  Get one mtdOrganization by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MtdOrganizationDTO findOne(Long id) {
        log.debug("Request to get MtdOrganization : {}", id);
        MtdOrganization mtdOrganization = mtdOrganizationRepository.findOne(id);
        return mtdOrganizationMapper.toDto(mtdOrganization);
    }

    /**
     *  Delete the  mtdOrganization by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MtdOrganization : {}", id);
        
        //Only update Isdel
        MtdOrganization item = mtdOrganizationRepository.getOne(id);
        item.setIsdel(1);
        mtdOrganizationRepository.save(item);
        
        //mtdOrganizationRepository.delete(id);
        //mtdOrganizationSearchRepository.delete(id);
    }

    /**
     * Search for the mtdOrganization corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdOrganizationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MtdOrganizations for query {}", query);
        //Page<MtdOrganization> result = mtdOrganizationSearchRepository.search(queryStringQuery(query), pageable);
        Page<MtdOrganization> result = mtdOrganizationRepository.findByOrganizationnameLikeIgnoreCaseAndIsdel(pageable, String.format("%s%s", query, "%"), 0);
        return result.map(mtdOrganizationMapper::toDto);
    }
}
