package com.astcore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.astcore.domain.MtdDevicegroup;


/**
 * Spring Data JPA repository for the MtdDevicegroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MtdDevicegroupRepository extends JpaRepository<MtdDevicegroup,Long> {
	@Query(value = "select a from MtdDevicegroup a where a.isdel=0", countQuery = "select count(a) from MtdDevicegroup a where a.isdel=0")
	Page<MtdDevicegroup> findAll(Pageable pageable);
	
	Page<MtdDevicegroup> findByDevicegroupnameLikeIgnoreCaseAndIsdel(Pageable pageable, String devicegroupname, Integer isdel);
}
