package com.astcore.service.mapper;

import com.astcore.domain.*;
import com.astcore.service.dto.MtdDevicegroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MtdDevicegroup and its DTO MtdDevicegroupDTO.
 */
@Mapper(componentModel = "spring", uses = {MtdUnitMapper.class, })
public interface MtdDevicegroupMapper extends EntityMapper <MtdDevicegroupDTO, MtdDevicegroup> {

    @Mapping(source = "mtdUnit.id", target = "mtdUnitId")
    @Mapping(source = "mtdUnit.unitname", target = "unitname")
    MtdDevicegroupDTO toDto(MtdDevicegroup mtdDevicegroup); 
    @Mapping(target = "deviceInfos", ignore = true)

    @Mapping(source = "mtdUnitId", target = "mtdUnit")
    MtdDevicegroup toEntity(MtdDevicegroupDTO mtdDevicegroupDTO); 
    default MtdDevicegroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        MtdDevicegroup mtdDevicegroup = new MtdDevicegroup();
        mtdDevicegroup.setId(id);
        return mtdDevicegroup;
    }
}
