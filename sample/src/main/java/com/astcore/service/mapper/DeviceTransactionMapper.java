package com.astcore.service.mapper;

import com.astcore.domain.*;
import com.astcore.service.dto.DeviceTransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DeviceTransaction and its DTO DeviceTransactionDTO.
 */
@Mapper(componentModel = "spring", uses = {DeviceInfoMapper.class, })
public interface DeviceTransactionMapper extends EntityMapper <DeviceTransactionDTO, DeviceTransaction> {

    @Mapping(source = "deviceInfo.id", target = "deviceInfoId")
    DeviceTransactionDTO toDto(DeviceTransaction deviceTransaction); 

    @Mapping(source = "deviceInfoId", target = "deviceInfo")
    DeviceTransaction toEntity(DeviceTransactionDTO deviceTransactionDTO); 
    default DeviceTransaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeviceTransaction deviceTransaction = new DeviceTransaction();
        deviceTransaction.setId(id);
        return deviceTransaction;
    }
}
