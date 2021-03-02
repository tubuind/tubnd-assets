package com.astcore.repository.search;

import com.astcore.domain.MtdDistrict;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MtdDistrict entity.
 */
public interface MtdDistrictSearchRepository extends ElasticsearchRepository<MtdDistrict, Long> {
}
