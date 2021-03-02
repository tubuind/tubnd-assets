package com.astcore.service.impl;

import com.astcore.service.CifDeviceService;
import com.astcore.domain.CifDevice;
import com.astcore.repository.CifDeviceRepository;
import com.astcore.repository.search.CifDeviceSearchRepository;
import com.astcore.service.dto.CifDeviceDTO;
import com.astcore.service.mapper.CifDeviceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CifDevice.
 */
@Service
@Transactional
public class CifDeviceServiceImpl implements CifDeviceService{

    private final Logger log = LoggerFactory.getLogger(CifDeviceServiceImpl.class);

    private final CifDeviceRepository cifDeviceRepository;

    private final CifDeviceMapper cifDeviceMapper;

    private final CifDeviceSearchRepository cifDeviceSearchRepository;

    public CifDeviceServiceImpl(CifDeviceRepository cifDeviceRepository, CifDeviceMapper cifDeviceMapper, CifDeviceSearchRepository cifDeviceSearchRepository) {
        this.cifDeviceRepository = cifDeviceRepository;
        this.cifDeviceMapper = cifDeviceMapper;
        this.cifDeviceSearchRepository = cifDeviceSearchRepository;
    }

    /**
     * Save a cifDevice.
     *
     * @param cifDeviceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CifDeviceDTO save(CifDeviceDTO cifDeviceDTO) {
        log.debug("Request to save CifDevice : {}", cifDeviceDTO);
        CifDevice cifDevice = cifDeviceMapper.toEntity(cifDeviceDTO);
        cifDevice = cifDeviceRepository.save(cifDevice);
        CifDeviceDTO result = cifDeviceMapper.toDto(cifDevice);
        cifDeviceSearchRepository.save(cifDevice);
        return result;
    }

    /**
     *  Get all the cifDevices.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CifDeviceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CifDevices");
        return cifDeviceRepository.findAll(pageable)
            .map(cifDeviceMapper::toDto);
    }

    /**
     *  Get one cifDevice by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CifDeviceDTO findOne(Long id) {
        log.debug("Request to get CifDevice : {}", id);
        CifDevice cifDevice = cifDeviceRepository.findOne(id);
        return cifDeviceMapper.toDto(cifDevice);
    }

    /**
     *  Delete the  cifDevice by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CifDevice : {}", id);
        cifDeviceRepository.delete(id);
        cifDeviceSearchRepository.delete(id);
    }

    /**
     * Search for the cifDevice corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CifDeviceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CifDevices for query {}", query);
        Page<CifDevice> result = cifDeviceSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(cifDeviceMapper::toDto);
    }
}
