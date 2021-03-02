package com.astcore.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astcore.domain.DeviceInfo;
import com.astcore.repository.DeviceInfoRepository;
import com.astcore.repository.search.DeviceInfoSearchRepository;
import com.astcore.security.SecurityUtils;
import com.astcore.service.DeviceInfoService;
import com.astcore.service.dto.DeviceInfoDTO;
import com.astcore.service.mapper.DeviceInfoMapper;

/**
 * Service Implementation for managing DeviceInfo.
 */
@Service
@Transactional
public class DeviceInfoServiceImpl implements DeviceInfoService{

    private final Logger log = LoggerFactory.getLogger(DeviceInfoServiceImpl.class);

    private final DeviceInfoRepository deviceInfoRepository;

    private final DeviceInfoMapper deviceInfoMapper;

    private final DeviceInfoSearchRepository deviceInfoSearchRepository;

    public DeviceInfoServiceImpl(DeviceInfoRepository deviceInfoRepository, DeviceInfoMapper deviceInfoMapper, DeviceInfoSearchRepository deviceInfoSearchRepository) {
        this.deviceInfoRepository = deviceInfoRepository;
        this.deviceInfoMapper = deviceInfoMapper;
        this.deviceInfoSearchRepository = deviceInfoSearchRepository;
    }

    /**
     * Save a deviceInfo.
     *
     * @param deviceInfoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DeviceInfoDTO save(DeviceInfoDTO deviceInfoDTO) {
        log.debug("Request to save DeviceInfo : {}", deviceInfoDTO);
        DeviceInfo deviceInfo = deviceInfoMapper.toEntity(deviceInfoDTO);
        
        //set UserId, CreateDate
        if (deviceInfo.getId() == null) {
        	deviceInfo.setIsdel(0);
        	deviceInfo.setCreateby(SecurityUtils.getCurrentUserLogin());
        	deviceInfo.setCreatedate(new Date());
        }
        deviceInfo.setLastmodifyby(SecurityUtils.getCurrentUserLogin());
        deviceInfo.setLastmodifydate(new Date());
        
        deviceInfo = deviceInfoRepository.save(deviceInfo);
        DeviceInfoDTO result = deviceInfoMapper.toDto(deviceInfo);
        deviceInfoSearchRepository.save(deviceInfo);
        return result;
    }

    /**
     *  Get all the deviceInfos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeviceInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DeviceInfos");
        return deviceInfoRepository.findAll(pageable)
            .map(deviceInfoMapper::toDto);
    }

    /**
     *  Get one deviceInfo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DeviceInfoDTO findOne(Long id) {
        log.debug("Request to get DeviceInfo : {}", id);
        DeviceInfo deviceInfo = deviceInfoRepository.findOne(id);
        return deviceInfoMapper.toDto(deviceInfo);
    }

    /**
     *  Delete the  deviceInfo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeviceInfo : {}", id);
        
        //Only update Isdel
        DeviceInfo item = deviceInfoRepository.getOne(id);
        item.setIsdel(1);
        deviceInfoRepository.save(item);
        
        //deviceInfoRepository.delete(id);
        //deviceInfoSearchRepository.delete(id);
    }

    /**
     * Search for the deviceInfo corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeviceInfoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DeviceInfos for query {}", query);
        //Page<DeviceInfo> result = deviceInfoSearchRepository.search(queryStringQuery(query), pageable);
        Page<DeviceInfo> result = deviceInfoRepository.findByDevicenameLikeIgnoreCaseAndIsdel(pageable, String.format("%s%s", query, "%"), 0);
        return result.map(deviceInfoMapper::toDto);
    }
    
    /**
     *  Get all the devices.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
	public Page<DeviceInfoDTO> getDevicesByUserLoged(Pageable pageable) {
    	log.debug("Request to get all Devices : getDevicesByUserLoged");
    	Page<DeviceInfo> result = deviceInfoRepository.getDevicesByUname(SecurityUtils.getCurrentUserLogin(), pageable);
        return result.map(deviceInfoMapper::toDto);
	}
}
