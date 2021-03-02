package com.astcore.repository;

import com.astcore.domain.CifAreaDevice;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CifAreaDevice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CifAreaDeviceRepository extends JpaRepository<CifAreaDevice,Long> {
    
}
