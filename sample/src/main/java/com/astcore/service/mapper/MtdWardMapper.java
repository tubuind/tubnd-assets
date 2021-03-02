package com.astcore.service.mapper;

import com.astcore.domain.*;
import com.astcore.service.dto.MtdWardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MtdWard and its DTO MtdWardDTO.
 */
@Mapper(componentModel = "spring", uses = {MtdDistrictMapper.class, })
public interface MtdWardMapper extends EntityMapper <MtdWardDTO, MtdWard> {

    @Mapping(source = "mtdDistrict.id", target = "mtdDistrictId")
    @Mapping(source = "mtdDistrict.districtname", target = "districtname")
    MtdWardDTO toDto(MtdWard mtdWard); 

    @Mapping(source = "mtdDistrictId", target = "mtdDistrict")
    @Mapping(target = "cifMasters", ignore = true)
    MtdWard toEntity(MtdWardDTO mtdWardDTO); 
    default MtdWard fromId(Long id) {
        if (id == null) {
            return null;
        }
        MtdWard mtdWard = new MtdWard();
        mtdWard.setId(id);
        return mtdWard;
    }
}
