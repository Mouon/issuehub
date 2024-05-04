package com.everysource.everysource.repository.api;

import com.everysource.everysource.domain.api.IssueSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueSearchRepository extends ElasticsearchRepository<IssueSearch, String> {
    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"title^2\", \"body\", \"repo\", \"owner\"], \"type\": \"phrase_prefix\"}}")
    List<IssueSearch> findByKeyword(String keyword);
    List<IssueSearch> findByOwner(String owner);
    List<IssueSearch> findByRepo(String repo);

    @Query("{\"more_like_this\": {\"fields\": [\"title\", \"body\"], \"like\": [{\"_id\": \"?0\"}], \"min_term_freq\" : 1, \"max_query_terms\": 12}}")
    List<IssueSearch> findSimilarIssues(Long issueId, Pageable pageable);


    /**
     * MLT 쿼리
     * "min_term_freq": 이 옵션은 MLT 쿼리가 문서에서 최소 몇 번 등장하는 용어를 기준으로 삼을지 지정합니다.
     * "max_query_terms": 이 옵션은 생성되는 쿼리에서 사용될 최대 용어 수를 지정합니다.
     * "min_doc_freq": 이 옵션은 용어가 색인된 문서들 중 최소 몇 개의 문서에 등장해야 하는지 지정합니다.
     * */
    @Query("{\"bool\": {" +
            "\"should\": [" +
            "{\"more_like_this\": {" +
            "\"fields\": [\"title\", \"repo\",\"body\"]," +
            "\"like\": [{\"_id\": \"?0\"}]," +
            "\"min_term_freq\": 1," +
            "\"max_query_terms\": 10," +
            "\"min_doc_freq\": 2" +
            "}}," +
            "{\"bool\": {" +
            "\"must_not\": {" +
            "\"ids\": {" +
            "\"values\": ?1" +
            "}" +
            "}" +
            "}}" +
            "]" +
            "}}")
    List<IssueSearch> findSimilarIssues(List<Long> issueIds, List<Long> excludedIssueIds, Pageable pageable);

}
