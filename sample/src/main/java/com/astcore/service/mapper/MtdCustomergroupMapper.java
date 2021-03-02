package com.astcore.service.mapper;

import com.astcore.domain.*;
import com.astcore.service.dto.MtdCustomergroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MtdCustomergroup and its DTO MtdCustomergroupDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MtdCustomergroupMapper extends EntityMapper <MtdCustomergroupDTO, MtdCustomergroup> {
    
    @Mapping(target = "cifMasters", ignore = true)
    MtdCustomergroup toEntity(MtdCustomergroupDTO mtdCustomergroupDTO); 
    default MtdCustomergroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        MtdCustomergroup mtdCustomergroup = new MtdCustomergroup();
        mtdCustomergroup.setId(id);
        return mtdCustomergroup;
    }
}
