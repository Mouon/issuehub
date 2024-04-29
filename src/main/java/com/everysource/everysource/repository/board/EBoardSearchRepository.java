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
    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"name\", \"content\"]}}")
    List<ErrorBoardSearch> findByKeyword(String keyword);

    List<ErrorBoardSearch> findAll();

}
