package com.astcore.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.astcore.domain.DeviceTransaction;


/**
 * Spring Data JPA repository for the DeviceTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceTransactionRepository extends JpaRepository<DeviceTransaction,Long> {
	@Query(value = "select a from DeviceTransaction a where a.isdel=0", countQuery = "select count(a) from DeviceTransaction a where a.isdel=0")
	Page<DeviceTransaction> findAll(Pageable pageable);
	
	@Query(value = "select new com.astcore.domain.DeviceTransaction(ROUND(AVG(dt.currentvalue), 2), TRUNC(dt.valuedate)) from DeviceTransaction dt"
			+ " where dt.deviceInfo.id in ("
			+ " select di.id from DeviceInfo di, CifDevice cd, CifAreaDevice cad, CifMaster cm"
			+ " where di.id=cd.deviceInfo.id and cd.id=cad.cifDevice.id and cd.cifMaster.id=cm.id and cm.login=:login"
			+ " and (:cifAreaId is null or cad.cifArea.id=:cifAreaId))"
			+ " and dt.valuetype=:valueType"
			+ " group by TRUNC(dt.valuedate)"
			+ " order by TRUNC(dt.valuedate)", 
	countQuery = "select count(dt) from DeviceTransaction dt"
			+ " where dt.deviceInfo.id in ("
			+ " select di.id from DeviceInfo di, CifDevice cd, CifAreaDevice cad, CifMaster cm"
			+ " where di.id=cd.deviceInfo.id and cd.id=cad.cifDevice.id and cd.cifMaster.id=cm.id and cm.login=:login"
			+ " and (:cifAreaId is null or cad.cifArea.id=:cifAreaId))"
			+ " and dt.valuetype=:valueType"
			+ " group by TRUNC(dt.valuedate)"
			+ " order by TRUNC(dt.valuedate)")
    Page<DeviceTransaction> getDeviceTransactionsByAreaIdTypeUser(@Param("cifAreaId") Long cifAreaId, @Param("valueType") String valueType, @Param("login") String login, Pageable pageable);
	
	@Query(value = "select new com.astcore.domain.DeviceTransaction(ROUND(AVG(dt.currentvalue), 2), TRUNC(dt.valuedate), dt.valuetype) from DeviceTransaction dt"
			+ " where dt.deviceInfo.id in ("
			+ " select di.id from DeviceInfo di, CifDevice cd, CifAreaDevice cad, CifMaster cm"
			+ " where di.id=cd.deviceInfo.id and cd.id=cad.cifDevice.id and cd.cifMaster.id=cm.id and cm.login=:login"
			+ " and (:cifAreaId is null or cad.cifArea.id=:cifAreaId))"
			+ " group by TRUNC(dt.valuedate), dt.valuetype"
			+ " order by TRUNC(dt.valuedate)", 
	countQuery = "select count(dt) from DeviceTransaction dt"
			+ " where dt.deviceInfo.id in ("
			+ " select di.id from DeviceInfo di, CifDevice cd, CifAreaDevice cad, CifMaster cm"
			+ " where di.id=cd.deviceInfo.id and cd.id=cad.cifDevice.id and cd.cifMaster.id=cm.id and cm.login=:login"
			+ " and (:cifAreaId is null or cad.cifArea.id=:cifAreaId))"
			+ " group by TRUNC(dt.valuedate), dt.valuetype"
			+ " order by TRUNC(dt.valuedate)")
    Page<DeviceTransaction> getDeviceTransactionsByAreaIdUser(@Param("cifAreaId") Long cifAreaId, @Param("login") String login, Pageable pageable);
	
	@Query(value = "select new com.astcore.domain.DeviceTransaction(ROUND(AVG(dt.currentvalue), 2), TRUNC(dt.valuedate), dt.valuetype) from DeviceTransaction dt"
			+ " where dt.deviceInfo.id in ("
			+ " select di.id from DeviceInfo di, CifDevice cd, CifAreaDevice cad, CifMaster cm"
			+ " where di.id=cd.deviceInfo.id and cd.id=cad.cifDevice.id and cd.cifMaster.id=cm.id and cm.login=:login"
			+ " and (:cifAreaId is null or cad.cifArea.id=:cifAreaId))"
			+ " and (:fromDate is null or dt.valuedate>=:fromDate) and (:toDate is null or dt.valuedate<=:toDate)"
			+ " group by TRUNC(dt.valuedate), dt.valuetype"
			+ " order by TRUNC(dt.valuedate)", 
	countQuery = "select count(dt) from DeviceTransaction dt"
			+ " where dt.deviceInfo.id in ("
			+ " select di.id from DeviceInfo di, CifDevice cd, CifAreaDevice cad, CifMaster cm"
			+ " where di.id=cd.deviceInfo.id and cd.id=cad.cifDevice.id and cd.cifMaster.id=cm.id and cm.login=:login"
			+ " and (:cifAreaId is null or cad.cifArea.id=:cifAreaId))"
			+ " and (:fromDate is null or dt.valuedate>=:fromDate) and (:toDate is null or dt.valuedate<=:toDate)"
			+ " group by TRUNC(dt.valuedate), dt.valuetype"
			+ " order by TRUNC(dt.valuedate)")
    Page<DeviceTransaction> getDeviceTransactionsByAreaIdDateUser(@Param("cifAreaId") Long cifAreaId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("login") String login, Pageable pageable);
	
	Page<DeviceTransaction> findByDevicecodeLikeIgnoreCaseAndIsdel(Pageable pageable, String devicecode, Integer isdel);
}
