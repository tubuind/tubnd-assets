package com.astcore.repository;

import com.astcore.domain.CifDevice;
import com.astcore.domain.MtdEcosectors;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CifDevice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CifDeviceRepository extends JpaRepository<CifDevice,Long> {
	@Query(value = "select a from CifDevice a where a.isdel=0", countQuery = "select count(a) from CifDevice a where a.isdel=0")
	Page<CifDevice> findAll(Pageable pageable);
}
