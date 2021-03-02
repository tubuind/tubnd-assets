package com.astcore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.astcore.domain.MtdCountry;


/**
 * Spring Data JPA repository for the MtdCountry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MtdCountryRepository extends JpaRepository<MtdCountry,Long> {
	@Query(value = "select a from MtdCountry a where a.isdel=0", countQuery = "select count(a) from MtdCountry a where a.isdel=0")
	Page<MtdCountry> findAll(Pageable pageable);
	
	Page<MtdCountry> findByCountrynameLikeIgnoreCaseAndIsdel(Pageable pageable, String countryname, Integer isdel);
}
