package com.astcore.repository.search;

import com.astcore.domain.MtdDevicegroup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MtdDevicegroup entity.
 */
public interface MtdDevicegroupSearchRepository extends ElasticsearchRepository<MtdDevicegroup, Long> {
}
