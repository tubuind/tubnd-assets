package com.astcore.repository.search;

import com.astcore.domain.CifAreaDevice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CifAreaDevice entity.
 */
public interface CifAreaDeviceSearchRepository extends ElasticsearchRepository<CifAreaDevice, Long> {
}
