package com.astcore.repository.search;

import com.astcore.domain.DeviceInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DeviceInfo entity.
 */
public interface DeviceInfoSearchRepository extends ElasticsearchRepository<DeviceInfo, Long> {
}
