package com.astcore.service.mapper;

import com.astcore.domain.*;
import com.astcore.service.dto.CifAreaDeviceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CifAreaDevice and its DTO CifAreaDeviceDTO.
 */
@Mapper(componentModel = "spring", uses = {CifAreaMapper.class, CifDeviceMapper.class, })
public interface CifAreaDeviceMapper extends EntityMapper <CifAreaDeviceDTO, CifAreaDevice> {

    @Mapping(source = "cifArea.id", target = "cifAreaId")

    @Mapping(source = "cifDevice.id", target = "cifDeviceId")
    
    @Mapping(source = "cifArea.cifareaname", target = "cifAreaName")

    @Mapping(source = "cifDevice.deviceInfo.devicename", target = "cifDeviceName")
    CifAreaDeviceDTO toDto(CifAreaDevice cifAreaDevice); 

    @Mapping(source = "cifAreaId", target = "cifArea")

    @Mapping(source = "cifDeviceId", target = "cifDevice")
    CifAreaDevice toEntity(CifAreaDeviceDTO cifAreaDeviceDTO); 
    default CifAreaDevice fromId(Long id) {
        if (id == null) {
            return null;
        }
        CifAreaDevice cifAreaDevice = new CifAreaDevice();
        cifAreaDevice.setId(id);
        return cifAreaDevice;
    }
}
