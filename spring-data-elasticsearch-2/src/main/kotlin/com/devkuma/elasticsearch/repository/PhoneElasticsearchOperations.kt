//package com.devkuma.elasticsearch.repository
//
//import com.devkuma.elasticsearch.document.Phone
//import org.elasticsearch.index.query.QueryBuilders
//import org.elasticsearch.index.query.QueryBuilders.termQuery
//
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations
//import org.springframework.data.elasticsearch.core.SearchHit
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
//import org.springframework.stereotype.Repository
//
//@Repository
//class PhoneElasticsearchOperations(
//    private val elasticsearchOperations: ElasticsearchOperations,
//) {
//    fun searchOneById(id: Int): SearchHit<Phone>? {
//
//        val searchQuery = NativeSearchQueryBuilder()
//            .withQuery(QueryBuilders.boolQuery()
//                .must(termQuery("id", id))
//            )
//            .build()
//
//        return elasticsearchOperations.searchOne(searchQuery, Phone::class.java)
//    }
//}