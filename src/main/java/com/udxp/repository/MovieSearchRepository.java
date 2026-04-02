package com.udxp.repository;

import com.udxp.entities.MovieDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MovieSearchRepository extends ElasticsearchRepository<MovieDocument, String> {
    @Query("""
{
  "bool": {
    "must": [
      {
        "multi_match": {
          "query": "?0",
          "fields": ["title", "description"]
        }
      }
    ],
    "filter": [
      { "term": { "releaseDate": "?1" }},
      { "term": { "categories": "?2" }}
    ]
  }
}
""")
    List<MovieDocument> searchAdvanced(String keyword, Integer releaseDate, String category);
}
