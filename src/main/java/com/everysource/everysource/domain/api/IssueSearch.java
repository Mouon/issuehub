package com.everysource.everysource.domain.api;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "issues")
@Mapping(mappingPath = "elastic/mapping.json")
@Setting(settingPath = "elastic/setting.json")
public class IssueSearch {
    @Id
    @Field(name="id", type = FieldType.Long)
    private Long id;

    @Field(name="title", type = FieldType.Text)
    private String title;

    @Field(name="owner", type = FieldType.Text)
    private String owner;

    @Field(name="repo", type = FieldType.Text)
    private String repo;
    @Field(name="state", type = FieldType.Text)
    private String state;
    @Field(name="since", type = FieldType.Text)
    private String since;

    @Field(name="body", type = FieldType.Text)
    private String body;

    @Field(name = "searchCount", type = FieldType.Integer, store = true)
    private int searchCount;



}
