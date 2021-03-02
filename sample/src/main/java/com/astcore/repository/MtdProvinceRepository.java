package com.astcore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.astcore.domain.MtdProvince;


/**
 * Spring Data JPA repository for the MtdProvince entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MtdProvinceRepository extends JpaRepository<MtdProvince,Long> {
	@Query(value = "select a from MtdProvince a where a.isdel=0", countQuery = "select count(a) from MtdProvince a where a.isdel=0")
	Page<MtdProvince> findAll(Pageable pageable);
	
	@Query(value = "select a from MtdProvince a where a.isdel=0 and a.mtdCountry.id=:idCountry", countQuery = "select count(a) from MtdProvince a where a.isdel=0 and a.mtdCountry.id=:idCountry")
	Page<MtdProvince> getProvinceByCountry(@Param("idCountry") Long idCountry, Pageable pageable);
	
	Page<MtdProvince> findByProvincenameLikeIgnoreCaseAndIsdel(Pageable pageable, String provincename, Integer isdel);
}
