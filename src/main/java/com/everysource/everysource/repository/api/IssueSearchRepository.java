package com.everysource.everysource.repository.api;

import com.everysource.everysource.domain.api.IssueSearch;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Multi Match 쿼리 구조
 *
 * title^2은 title 필드에 가중치를 3배 주어 검색 결과에서 더 중요하게 다루어짐
 *
 * {
 *   "multi_match": {
 *     "query": "?0",
 *     "fields": ["title^2", "body", "repo","owner"],
 *     "type": "best_fields"
 *   }
 * }
 *
 * */

@Repository
public interface IssueSearchRepository extends ElasticsearchRepository<IssueSearch, String> {
    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"title^2\", \"body\", \"repo\", \"owner\"], \"type\": \"best_fields\"}}")
    List<IssueSearch> findByKeyword(String keyword);
}

/**
 *
 private final IssueRepository issueRepository;

 public IssueSearchRepository(IssueRepository issueRepository) {
 this.issueRepository = issueRepository;
 }

 /** 검색 로직.
 *
 *  데이터베이스 쿼리의 유연성을 크게 향상
 *\\\criteriaBuilder.or()\\\\\
 * CriteriaBuilder 객체의 메소드 중 하나로,
 * 여러 Predicate 객체를 인자로 받아 이들 중 하나라도
 * 참인 경우 참을 반환하는 조건을 생성
 * */
//public List<Issue> searchIssues(String keyword) {
//    Specification<Issue> spec = (root, query, criteriaBuilder) -> {/**Specification<Issue> 라고해서 root에 이슈 넘어감 */
//        List<Predicate> predicates = new ArrayList<>();
//        String pattern = "%" + keyword.toLowerCase() + "%";
//        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), pattern));/**필드를 소문자로 변환합니다. LOWER(title) LIKE '%keyword%' 으로 쿼리 날라감*/
//        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), pattern));
//        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), pattern));
//        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
//    };
//    return issueRepository.findAll(spec);
//}





