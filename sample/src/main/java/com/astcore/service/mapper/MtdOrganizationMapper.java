package com.astcore.service.mapper;

import com.astcore.domain.*;
import com.astcore.service.dto.CifMasterDTO;
import com.astcore.service.dto.MtdOrganizationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MtdOrganization and its DTO MtdOrganizationDTO.
 */
@Mapper(componentModel = "spring", uses = {MtdWardMapper.class})
public interface MtdOrganizationMapper extends EntityMapper <MtdOrganizationDTO, MtdOrganization> {
    
	@Mapping(source = "mtdWard.id", target = "mtdWardId")
	@Mapping(source = "mtdWard.mtdDistrict.mtdProvince.id", target = "mtdProvinceId")
	@Mapping(source = "mtdWard.mtdDistrict.id", target = "mtdDistrictId")
	MtdOrganizationDTO toDto(MtdOrganization mtdOrganization); 

	@Mapping(source = "mtdWardId", target = "mtdWard")
    @Mapping(target = "cifMasters", ignore = true)
    MtdOrganization toEntity(MtdOrganizationDTO mtdOrganizationDTO); 
    default MtdOrganization fromId(Long id) {
        if (id == null) {
            return null;
        }
        MtdOrganization mtdOrganization = new MtdOrganization();
        mtdOrganization.setId(id);
        return mtdOrganization;
    }
}
