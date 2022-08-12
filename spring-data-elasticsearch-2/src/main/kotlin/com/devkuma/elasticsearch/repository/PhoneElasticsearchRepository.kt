package com.devkuma.elasticsearch.repository

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.elasticsearch._types.FieldValue
import co.elastic.clients.elasticsearch.core.*
import co.elastic.clients.elasticsearch.core.bulk.BulkOperationBuilders.index
import com.devkuma.elasticsearch.document.Phone
import org.springframework.stereotype.Repository
import java.io.IOException


@Repository
class PhoneElasticsearchRepository(
    private val elasticsearchClient: ElasticsearchClient,
) {

    val indexName = "phone"

    fun searchOneById(id: Int): Phone? {

        return elasticsearchClient.search(
            SearchRequest.Builder()
                .index(indexName)
                .query { query ->
                    query
                        .bool { bool ->
                            bool
                                .filter { filter ->
                                    filter
                                        .term { term ->
                                            term
                                                .field("id")
                                                .value(FieldValue.of(id.toLong()))
                                        }
                                }
                        }
                }.build(),
            Phone::class.java
        )?.hits()?.hits()
            ?.mapNotNull { hit -> hit.source() }
            ?.toList()?.get(0)
    }

    fun<T> save(t: T): T {
        try {
            val indexRequest: IndexRequest<T> = IndexRequest.Builder<T>().index(indexName).document(t).build()
            elasticsearchClient.index(indexRequest)
        } catch (e: IOException) {
            e.stackTrace
        }
        return t
    }


    fun delete(id: Int) : Boolean {
        try {
            val deleteRequest = DeleteRequest.Builder().index(indexName).id(id.toString()).build()
            val deleteResponse: DeleteResponse = elasticsearchClient.delete(deleteRequest)
            return "deleted" == deleteResponse.result().jsonValue()
        } catch (e: IOException) {
            e.stackTrace
        }
        return false
    }
}