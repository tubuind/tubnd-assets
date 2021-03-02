package com.astcore.service.mapper;

import com.astcore.domain.*;
import com.astcore.service.dto.MtdCountryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MtdCountry and its DTO MtdCountryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MtdCountryMapper extends EntityMapper <MtdCountryDTO, MtdCountry> {
    
    @Mapping(target = "mtdProvinces", ignore = true)
    MtdCountry toEntity(MtdCountryDTO mtdCountryDTO); 
    default MtdCountry fromId(Long id) {
        if (id == null) {
            return null;
        }
        MtdCountry mtdCountry = new MtdCountry();
        mtdCountry.setId(id);
        return mtdCountry;
    }
}
