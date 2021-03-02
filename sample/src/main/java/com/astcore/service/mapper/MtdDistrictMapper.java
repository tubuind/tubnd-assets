package com.astcore.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.astcore.domain.MtdDistrict;
import com.astcore.domain.MtdProvince;
import com.astcore.service.dto.MtdDistrictDTO;

/**
 * Mapper for the entity MtdDistrict and its DTO MtdDistrictDTO.
 */
@Mapper(componentModel = "spring", uses = {MtdProvinceMapper.class, })
public interface MtdDistrictMapper extends EntityMapper <MtdDistrictDTO, MtdDistrict> {

    @Mapping(source = "mtdProvince.id", target = "mtdProvinceId")
    @Mapping(source = "mtdProvince.provincename", target = "mtdProvinceProvincename")
    MtdDistrictDTO toDto(MtdDistrict mtdDistrict);

    @Mapping(source = "mtdProvinceId", target = "mtdProvince")
    @Mapping(target = "mtdWards", ignore = true)
    MtdDistrict toEntity(MtdDistrictDTO mtdDistrictDTO); 
    
    default MtdDistrict fromId(Long id) {
        if (id == null) {
            return null;
        }
        MtdDistrict mtdDistrict = new MtdDistrict();
        mtdDistrict.setId(id);
        return mtdDistrict;
    }
}
