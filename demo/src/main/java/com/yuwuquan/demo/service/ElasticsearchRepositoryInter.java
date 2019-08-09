package com.yuwuquan.demo.service;

import com.yuwuquan.demo.orm.dto.ElasticsearchObject;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticsearchRepositoryInter extends ElasticsearchRepository<ElasticsearchObject,String> {
    ElasticsearchObject findByNameLike(String name);
    ElasticsearchObject deleteByNameEquals(String name);

}
