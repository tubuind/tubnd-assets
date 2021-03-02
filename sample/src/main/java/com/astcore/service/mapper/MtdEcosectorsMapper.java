package com.astcore.service.mapper;

import com.astcore.domain.*;
import com.astcore.service.dto.MtdEcosectorsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MtdEcosectors and its DTO MtdEcosectorsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MtdEcosectorsMapper extends EntityMapper <MtdEcosectorsDTO, MtdEcosectors> {
    
    @Mapping(target = "cifMasters", ignore = true)
    MtdEcosectors toEntity(MtdEcosectorsDTO mtdEcosectorsDTO); 
    default MtdEcosectors fromId(Long id) {
        if (id == null) {
            return null;
        }
        MtdEcosectors mtdEcosectors = new MtdEcosectors();
        mtdEcosectors.setId(id);
        return mtdEcosectors;
    }
}
