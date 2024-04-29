package com.everysource.everysource.domain.board;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "error_boards")
@Mapping(mappingPath = "elastic/error_board_mapping.json")
@Setting(settingPath = "elastic/setting.json")
public class ErrorBoardSearch {


    @Id
    @Field(name="id", type = FieldType.Long)
    private Long id;

    @Field(name="name", type = FieldType.Text)
    private String name;

    @Field(name="content", type = FieldType.Text)
    private String content;

    @Field(name="createAt", type = FieldType.Text)
    private LocalDateTime createAt;

    public ErrorBoardSearch(Long id,String name, String content) {
        this.id=id;
        this.name = name;
        this.content = content;
    }

}
