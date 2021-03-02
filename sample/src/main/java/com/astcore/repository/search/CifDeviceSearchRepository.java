package com.astcore.repository.search;

import com.astcore.domain.CifDevice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CifDevice entity.
 */
public interface CifDeviceSearchRepository extends ElasticsearchRepository<CifDevice, Long> {
}
