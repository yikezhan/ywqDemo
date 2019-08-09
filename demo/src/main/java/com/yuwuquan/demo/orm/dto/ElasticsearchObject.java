package com.yuwuquan.demo.orm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "user_info", type = "doc")
public class ElasticsearchObject {
    @JsonProperty("auto_id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("age")
    private String age;

    @JsonProperty("address")
    private String address;

    @JsonProperty("school")
    private String school;

}
