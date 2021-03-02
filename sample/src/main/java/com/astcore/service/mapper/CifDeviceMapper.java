package com.astcore.service.mapper;

import com.astcore.domain.*;
import com.astcore.service.dto.CifDeviceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CifDevice and its DTO CifDeviceDTO.
 */
@Mapper(componentModel = "spring", uses = {CifMasterMapper.class, DeviceInfoMapper.class, })
public interface CifDeviceMapper extends EntityMapper <CifDeviceDTO, CifDevice> {

    @Mapping(source = "cifMaster.id", target = "cifMasterId")

    @Mapping(source = "deviceInfo.id", target = "deviceInfoId")
    
    @Mapping(source = "cifMaster.customername", target = "cifMasterName")

    @Mapping(source = "deviceInfo.devicename", target = "deviceInfoName")
    CifDeviceDTO toDto(CifDevice cifDevice); 

    @Mapping(source = "cifMasterId", target = "cifMaster")

    @Mapping(source = "deviceInfoId", target = "deviceInfo")
    CifDevice toEntity(CifDeviceDTO cifDeviceDTO); 
    default CifDevice fromId(Long id) {
        if (id == null) {
            return null;
        }
        CifDevice cifDevice = new CifDevice();
        cifDevice.setId(id);
        return cifDevice;
    }
}
