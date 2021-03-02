package com.astcore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.astcore.domain.MtdDevicetype;


/**
 * Spring Data JPA repository for the MtdDevicetype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MtdDevicetypeRepository extends JpaRepository<MtdDevicetype,Long> {
	@Query(value = "select a from MtdDevicetype a where a.isdel=0", countQuery = "select count(a) from MtdDevicetype a where a.isdel=0")
	Page<MtdDevicetype> findAll(Pageable pageable);
	
	Page<MtdDevicetype> findByDevicetypenameLikeIgnoreCaseAndIsdel(Pageable pageable, String devicetypename, Integer isdel);
}
