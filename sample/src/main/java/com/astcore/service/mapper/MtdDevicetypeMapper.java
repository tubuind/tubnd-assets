package com.astcore.service.mapper;

import com.astcore.domain.*;
import com.astcore.service.dto.MtdDevicetypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MtdDevicetype and its DTO MtdDevicetypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MtdDevicetypeMapper extends EntityMapper <MtdDevicetypeDTO, MtdDevicetype> {
    
    @Mapping(target = "deviceInfos", ignore = true)
    MtdDevicetype toEntity(MtdDevicetypeDTO mtdDevicetypeDTO); 
    default MtdDevicetype fromId(Long id) {
        if (id == null) {
            return null;
        }
        MtdDevicetype mtdDevicetype = new MtdDevicetype();
        mtdDevicetype.setId(id);
        return mtdDevicetype;
    }
}
