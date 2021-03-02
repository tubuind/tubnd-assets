package com.astcore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.astcore.domain.DeviceInfo;


/**
 * Spring Data JPA repository for the DeviceInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceInfoRepository extends JpaRepository<DeviceInfo,Long> {
	@Query(value = "select a from DeviceInfo a where a.isdel=0", countQuery = "select count(a) from DeviceInfo a where a.isdel=0")
	Page<DeviceInfo> findAll(Pageable pageable);
	
	@Query("SELECT a FROM DeviceInfo a  WHERE a.devicecode=:devicecode")
	DeviceInfo findOneByDevicecode(@Param("devicecode") String devicecode);
	
	//@Query(value = "select device from DeviceInfo device, CifDevice cifDevice where device.id=cifDevice.deviceInfo.id and cifMaster.id=:customerId", countQuery = "select count(device) from DeviceInfo device, CifDevice cifDevice where device.id=cifDevice.deviceInfo.id and cifMaster.id=:customerId")
    //Page<DeviceInfo> getDevicesByCustomerId(@Param("customerId") Long customerId, Pageable pageable);
	
	@Query(value = "select device from DeviceInfo device, CifDevice cifDevice, CifMaster cifMaster where device.id=cifDevice.deviceInfo.id and cifDevice.cifMaster.id=cifMaster.id and cifMaster.login=:login", countQuery = "select count(device) from DeviceInfo device, CifDevice cifDevice, CifMaster cifMaster where device.id=cifDevice.deviceInfo.id and cifDevice.cifMaster.id=cifMaster.id and cifMaster.login=:login")
    Page<DeviceInfo> getDevicesByUname(@Param("login") String login, Pageable pageable);
	
	Page<DeviceInfo> findByDevicenameLikeIgnoreCaseAndIsdel(Pageable pageable, String devicename, Integer isdel);
}
