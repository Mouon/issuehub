package com.everysource.everysource.repository.board;

import com.everysource.everysource.domain.api.IssueSearch;
import com.everysource.everysource.domain.board.ErrorBoardSearch;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface EBoardSearchRepository extends ElasticsearchRepository<ErrorBoardSearch, String> {

    /**
     * 부분일치 + 오타 허용 phrase_prefix 사용
     * */
    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"name\", \"content\"], \"type\": \"phrase_prefix\"}}")
    List<ErrorBoardSearch> findByKeyword(String keyword);

    List<ErrorBoardSearch> findAll();

}
