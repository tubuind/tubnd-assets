package com.astcore.service.mapper;

import com.astcore.domain.*;
import com.astcore.service.dto.DeviceInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DeviceInfo and its DTO DeviceInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {MtdDevicetypeMapper.class, MtdDevicegroupMapper.class, })
public interface DeviceInfoMapper extends EntityMapper <DeviceInfoDTO, DeviceInfo> {

    @Mapping(source = "mtdDevicetype.id", target = "mtdDevicetypeId")

    @Mapping(source = "mtdDevicegroup.id", target = "mtdDevicegroupId")
    
    @Mapping(source = "mtdDevicetype.devicetypename", target = "mtdDevicetypename")

    @Mapping(source = "mtdDevicegroup.devicegroupname", target = "mtdDevicegroupname")
    
    DeviceInfoDTO toDto(DeviceInfo deviceInfo);

    @Mapping(source = "mtdDevicetypeId", target = "mtdDevicetype")

    @Mapping(source = "mtdDevicegroupId", target = "mtdDevicegroup")
    @Mapping(target = "deviceTransactions", ignore = true)
    @Mapping(target = "cifDevices", ignore = true)
    DeviceInfo toEntity(DeviceInfoDTO deviceInfoDTO); 
    default DeviceInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setId(id);
        return deviceInfo;
    }
}
