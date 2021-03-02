package com.astcore.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.Date;

import org.elasticsearch.common.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astcore.domain.MtdEcosectors;
import com.astcore.repository.MtdEcosectorsRepository;
import com.astcore.repository.search.MtdEcosectorsSearchRepository;
import com.astcore.security.SecurityUtils;
import com.astcore.service.MtdEcosectorsService;
import com.astcore.service.UserService;
import com.astcore.service.dto.MtdEcosectorsDTO;
import com.astcore.service.mapper.MtdEcosectorsMapper;

/**
 * Service Implementation for managing MtdEcosectors.
 */
@Service
@Transactional
public class MtdEcosectorsServiceImpl implements MtdEcosectorsService{

    private final Logger log = LoggerFactory.getLogger(MtdEcosectorsServiceImpl.class);

    private final MtdEcosectorsRepository mtdEcosectorsRepository;

    private final MtdEcosectorsMapper mtdEcosectorsMapper;

    private final MtdEcosectorsSearchRepository mtdEcosectorsSearchRepository;
    
    @Inject
    private UserService userService;

    public MtdEcosectorsServiceImpl(MtdEcosectorsRepository mtdEcosectorsRepository, MtdEcosectorsMapper mtdEcosectorsMapper, MtdEcosectorsSearchRepository mtdEcosectorsSearchRepository) {
        this.mtdEcosectorsRepository = mtdEcosectorsRepository;
        this.mtdEcosectorsMapper = mtdEcosectorsMapper;
        this.mtdEcosectorsSearchRepository = mtdEcosectorsSearchRepository;
    }

    /**
     * Save a mtdEcosectors.
     *
     * @param mtdEcosectorsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MtdEcosectorsDTO save(MtdEcosectorsDTO mtdEcosectorsDTO) {
        log.debug("Request to save MtdEcosectors : {}", mtdEcosectorsDTO);
        MtdEcosectors mtdEcosectors = mtdEcosectorsMapper.toEntity(mtdEcosectorsDTO);
        
        //set UserId, CreateDate
        if (mtdEcosectors.getId() == null) {
        	mtdEcosectors.setIsdel(0);
        	mtdEcosectors.setCreateby(SecurityUtils.getCurrentUserLogin());
            mtdEcosectors.setCreatedate(new Date());
        }
        mtdEcosectors.setLastmodifyby(SecurityUtils.getCurrentUserLogin());
        mtdEcosectors.setLastmodifydate(new Date());
        
        mtdEcosectors = mtdEcosectorsRepository.save(mtdEcosectors);
        MtdEcosectorsDTO result = mtdEcosectorsMapper.toDto(mtdEcosectors);
        mtdEcosectorsSearchRepository.save(mtdEcosectors);
        return result;
    }

    /**
     *  Get all the mtdEcosectors.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdEcosectorsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MtdEcosectors");
        return mtdEcosectorsRepository.findAll(pageable)
            .map(mtdEcosectorsMapper::toDto);
    }

    /**
     *  Get one mtdEcosectors by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MtdEcosectorsDTO findOne(Long id) {
        log.debug("Request to get MtdEcosectors : {}", id);
        MtdEcosectors mtdEcosectors = mtdEcosectorsRepository.findOne(id);
        return mtdEcosectorsMapper.toDto(mtdEcosectors);
    }

    /**
     *  Delete the  mtdEcosectors by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MtdEcosectors : {}", id);
        
        //Only update Isdel
        MtdEcosectors item = mtdEcosectorsRepository.getOne(id);
        item.setIsdel(1);
        mtdEcosectorsRepository.save(item);

        //mtdEcosectorsRepository.delete(id);
        //mtdEcosectorsSearchRepository.delete(id);
    }

    /**
     * Search for the mtdEcosectors corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MtdEcosectorsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MtdEcosectors for query {}", query);
        //Page<MtdEcosectors> result = mtdEcosectorsSearchRepository.search(queryStringQuery(query), pageable);
        Page<MtdEcosectors> result = mtdEcosectorsRepository.findByEconameLikeIgnoreCaseAndIsdel(pageable, String.format("%s%s", query, "%"), 0);
        return result.map(mtdEcosectorsMapper::toDto);
    }
}
