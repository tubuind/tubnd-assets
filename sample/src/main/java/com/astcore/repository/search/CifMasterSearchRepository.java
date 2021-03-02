package com.astcore.repository.search;

import com.astcore.domain.CifMaster;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CifMaster entity.
 */
public interface CifMasterSearchRepository extends ElasticsearchRepository<CifMaster, Long> {
}
