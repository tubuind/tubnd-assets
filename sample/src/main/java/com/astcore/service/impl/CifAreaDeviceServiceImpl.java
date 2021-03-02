package com.astcore.service.impl;

import com.astcore.service.CifAreaDeviceService;
import com.astcore.domain.CifAreaDevice;
import com.astcore.repository.CifAreaDeviceRepository;
import com.astcore.repository.search.CifAreaDeviceSearchRepository;
import com.astcore.service.dto.CifAreaDeviceDTO;
import com.astcore.service.mapper.CifAreaDeviceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CifAreaDevice.
 */
@Service
@Transactional
public class CifAreaDeviceServiceImpl implements CifAreaDeviceService{

    private final Logger log = LoggerFactory.getLogger(CifAreaDeviceServiceImpl.class);

    private final CifAreaDeviceRepository cifAreaDeviceRepository;

    private final CifAreaDeviceMapper cifAreaDeviceMapper;

    private final CifAreaDeviceSearchRepository cifAreaDeviceSearchRepository;

    public CifAreaDeviceServiceImpl(CifAreaDeviceRepository cifAreaDeviceRepository, CifAreaDeviceMapper cifAreaDeviceMapper, CifAreaDeviceSearchRepository cifAreaDeviceSearchRepository) {
        this.cifAreaDeviceRepository = cifAreaDeviceRepository;
        this.cifAreaDeviceMapper = cifAreaDeviceMapper;
        this.cifAreaDeviceSearchRepository = cifAreaDeviceSearchRepository;
    }

    /**
     * Save a cifAreaDevice.
     *
     * @param cifAreaDeviceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CifAreaDeviceDTO save(CifAreaDeviceDTO cifAreaDeviceDTO) {
        log.debug("Request to save CifAreaDevice : {}", cifAreaDeviceDTO);
        CifAreaDevice cifAreaDevice = cifAreaDeviceMapper.toEntity(cifAreaDeviceDTO);
        cifAreaDevice = cifAreaDeviceRepository.save(cifAreaDevice);
        CifAreaDeviceDTO result = cifAreaDeviceMapper.toDto(cifAreaDevice);
        cifAreaDeviceSearchRepository.save(cifAreaDevice);
        return result;
    }

    /**
     *  Get all the cifAreaDevices.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CifAreaDeviceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CifAreaDevices");
        return cifAreaDeviceRepository.findAll(pageable)
            .map(cifAreaDeviceMapper::toDto);
    }

    /**
     *  Get one cifAreaDevice by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CifAreaDeviceDTO findOne(Long id) {
        log.debug("Request to get CifAreaDevice : {}", id);
        CifAreaDevice cifAreaDevice = cifAreaDeviceRepository.findOne(id);
        return cifAreaDeviceMapper.toDto(cifAreaDevice);
    }

    /**
     *  Delete the  cifAreaDevice by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CifAreaDevice : {}", id);
        cifAreaDeviceRepository.delete(id);
        cifAreaDeviceSearchRepository.delete(id);
    }

    /**
     * Search for the cifAreaDevice corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CifAreaDeviceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CifAreaDevices for query {}", query);
        Page<CifAreaDevice> result = cifAreaDeviceSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(cifAreaDeviceMapper::toDto);
    }
}
