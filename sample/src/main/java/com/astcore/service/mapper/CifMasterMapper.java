package com.astcore.service.mapper;

import com.astcore.domain.*;
import com.astcore.service.dto.CifMasterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CifMaster and its DTO CifMasterDTO.
 */
@Mapper(componentModel = "spring", uses = {MtdWardMapper.class, MtdOrganizationMapper.class, MtdEcosectorsMapper.class, MtdCustomergroupMapper.class, })
public interface CifMasterMapper extends EntityMapper <CifMasterDTO, CifMaster> {

    @Mapping(source = "mtdWard.id", target = "mtdWardId")

    @Mapping(source = "mtdOrganization.id", target = "mtdOrganizationId")

    @Mapping(source = "mtdEcosectors.id", target = "mtdEcosectorsId")

    @Mapping(source = "mtdCustomergroup.id", target = "mtdCustomergroupId")
    
    @Mapping(source = "mtdOrganization.organizationname", target = "mtdOrganizationName")

    @Mapping(source = "mtdEcosectors.econame", target = "mtdEcosectorsName")

    @Mapping(source = "mtdCustomergroup.custgroupname", target = "mtdCustomergroupName")
    CifMasterDTO toDto(CifMaster cifMaster); 

    @Mapping(source = "mtdWardId", target = "mtdWard")

    @Mapping(source = "mtdOrganizationId", target = "mtdOrganization")

    @Mapping(source = "mtdEcosectorsId", target = "mtdEcosectors")

    @Mapping(source = "mtdCustomergroupId", target = "mtdCustomergroup")
    @Mapping(target = "cifDevices", ignore = true)
    CifMaster toEntity(CifMasterDTO cifMasterDTO); 
    default CifMaster fromId(Long id) {
        if (id == null) {
            return null;
        }
        CifMaster cifMaster = new CifMaster();
        cifMaster.setId(id);
        return cifMaster;
    }
}
