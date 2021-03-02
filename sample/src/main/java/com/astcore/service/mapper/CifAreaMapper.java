package com.astcore.service.mapper;

import com.astcore.domain.*;
import com.astcore.service.dto.CifAreaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CifArea and its DTO CifAreaDTO.
 */
@Mapper(componentModel = "spring", uses = {CifMasterMapper.class, })
public interface CifAreaMapper extends EntityMapper <CifAreaDTO, CifArea> {
	
	@Mapping(source = "parent.id", target = "cifareaparent")
    @Mapping(source = "parent.cifareaname", target = "parentname")
    @Mapping(source = "cifMaster.id", target = "cifMasterId")
	@Mapping(source = "cifMaster.customername", target = "cifMasterName")
    CifAreaDTO toDto(CifArea cifArea); 
	
	@Mapping(source = "cifareaparent", target = "parent")
    @Mapping(source = "cifMasterId", target = "cifMaster")
    @Mapping(target = "cifAreaDevices", ignore = true)
    CifArea toEntity(CifAreaDTO cifAreaDTO); 
    default CifArea fromId(Long id) {
        if (id == null) {
            return null;
        }
        CifArea cifArea = new CifArea();
        cifArea.setId(id);
        return cifArea;
    }
}
