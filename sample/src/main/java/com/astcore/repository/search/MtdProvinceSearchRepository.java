package com.astcore.repository.search;

import com.astcore.domain.MtdProvince;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MtdProvince entity.
 */
public interface MtdProvinceSearchRepository extends ElasticsearchRepository<MtdProvince, Long> {
}
