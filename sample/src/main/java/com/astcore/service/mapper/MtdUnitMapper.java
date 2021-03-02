package com.astcore.service.mapper;

import com.astcore.domain.*;
import com.astcore.service.dto.MtdUnitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MtdUnit and its DTO MtdUnitDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MtdUnitMapper extends EntityMapper <MtdUnitDTO, MtdUnit> {
    
    @Mapping(target = "mtdDevicegroups", ignore = true)
    MtdUnit toEntity(MtdUnitDTO mtdUnitDTO); 
    default MtdUnit fromId(Long id) {
        if (id == null) {
            return null;
        }
        MtdUnit mtdUnit = new MtdUnit();
        mtdUnit.setId(id);
        return mtdUnit;
    }
}
