package com.astcore.repository.search;

import com.astcore.domain.DeviceTransaction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DeviceTransaction entity.
 */
public interface DeviceTransactionSearchRepository extends ElasticsearchRepository<DeviceTransaction, Long> {
}
