package com.astcore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.astcore.domain.MtdDistrict;


/**
 * Spring Data JPA repository for the MtdDistrict entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MtdDistrictRepository extends JpaRepository<MtdDistrict,Long> {
	@Query(value = "select a from MtdDistrict a where a.isdel=0", countQuery = "select count(a) from MtdDistrict a where a.isdel=0")
	Page<MtdDistrict> findAll(Pageable pageable);
	
	@Query(value = "select a from MtdDistrict a where a.isdel=0 and a.mtdProvince.id=:idProvince", countQuery = "select count(a) from MtdDistrict a where a.isdel=0 and a.mtdProvince.id=:idProvince")
    Page<MtdDistrict> getDistrictByProvince(@Param("idProvince") Long idProvince, Pageable pageable);
	
	Page<MtdDistrict> findByDistrictnameLikeIgnoreCaseAndIsdel(Pageable pageable, String districtname, Integer isdel);
}
