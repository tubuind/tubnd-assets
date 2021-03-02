package com.astcore.service.mapper;

import com.astcore.domain.*;
import com.astcore.service.dto.MtdProvinceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MtdProvince and its DTO MtdProvinceDTO.
 */
@Mapper(componentModel = "spring", uses = {MtdCountryMapper.class, })
public interface MtdProvinceMapper extends EntityMapper <MtdProvinceDTO, MtdProvince> {

    @Mapping(source = "mtdCountry.id", target = "mtdCountryId")
    @Mapping(source = "mtdCountry.countryname", target = "countryname")
    MtdProvinceDTO toDto(MtdProvince mtdProvince); 

    @Mapping(source = "mtdCountryId", target = "mtdCountry")
    @Mapping(target = "mtdDistricts", ignore = true)
    MtdProvince toEntity(MtdProvinceDTO mtdProvinceDTO); 
    default MtdProvince fromId(Long id) {
        if (id == null) {
            return null;
        }
        MtdProvince mtdProvince = new MtdProvince();
        mtdProvince.setId(id);
        return mtdProvince;
    }
}
