package com.devkuma.elasticsearch.config

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.json.jackson.JacksonJsonpMapper
import co.elastic.clients.transport.rest_client.RestClientTransport
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.client.CredentialsProvider
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.ssl.SSLContextBuilder
import org.apache.http.ssl.SSLContexts
import org.elasticsearch.client.RestClient
//import org.elasticsearch.client.RestHighLevelClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
//import org.springframework.data.elasticsearch.client.ClientConfiguration
//import org.springframework.data.elasticsearch.client.RestClients
//import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration
//import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
//import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext


@Configuration
//@EnableElasticsearchRepositories
class ElasticsearchConfig(
    private val elasticsearchProperties: ElasticsearchProperties,
    private val objectMapper: ObjectMapper
) { // : AbstractElasticsearchConfiguration() {



    @Bean
    fun elasticsearchClient(): ElasticsearchClient {

        val caCertificatePath: Path = Paths.get("/Users/we/http_ca.crt")
        val factory = CertificateFactory.getInstance("X.509")
        var trustedCa: Certificate?
        Files.newInputStream(caCertificatePath).use { `is` -> trustedCa = factory.generateCertificate(`is`) }
        val trustStore = KeyStore.getInstance("pkcs12")
        trustStore.load(null, null)
        trustStore.setCertificateEntry("ca", trustedCa)
        val sslContextBuilder: SSLContextBuilder = SSLContexts.custom()
            .loadTrustMaterial(trustStore, null)
        val sslContext: SSLContext = sslContextBuilder.build()

        val credentialsProvider: CredentialsProvider = BasicCredentialsProvider()
        credentialsProvider.setCredentials(AuthScope.ANY,
            UsernamePasswordCredentials("elastic", "xKGjLPon5CK4_m3=Nc-G"))


        val builder = RestClient.builder(
            HttpHost("localhost", 9200, "https"))
            .setHttpClientConfigCallback { httpClientBuilder ->
                httpClientBuilder
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .setSSLContext(sslContext)
            }


        val restClient : RestClient = builder.build()

        val elasticsearchTransport = RestClientTransport(restClient, JacksonJsonpMapper(objectMapper))

        return ElasticsearchClient(elasticsearchTransport)
    }



//    override fun elasticsearchClient(): RestHighLevelClient {
//
//        val caCertificatePath: Path = Paths.get("/Users/we/http_ca.crt")
//        val factory = CertificateFactory.getInstance("X.509")
//        var trustedCa: Certificate?
//        Files.newInputStream(caCertificatePath).use { `is` -> trustedCa = factory.generateCertificate(`is`) }
//        val trustStore = KeyStore.getInstance("pkcs12")
//        trustStore.load(null, null)
//        trustStore.setCertificateEntry("ca", trustedCa)
//        val sslContextBuilder: SSLContextBuilder = SSLContexts.custom()
//            .loadTrustMaterial(trustStore, null)
//        val sslContext: SSLContext = sslContextBuilder.build()
//
//        val credentialsProvider: CredentialsProvider = BasicCredentialsProvider()
//        credentialsProvider.setCredentials(AuthScope.ANY,
//            UsernamePasswordCredentials("elastic", "xKGjLPon5CK4_m3=Nc-G"))
//
//
//        val builder = RestClient.builder(
//            HttpHost("localhost", 9200, "https"))
//            .setHttpClientConfigCallback { httpClientBuilder ->
//                httpClientBuilder
//                    .setDefaultCredentialsProvider(credentialsProvider)
//                    .setSSLContext(sslContext)
//            }
//
//
////        val restClient : RestClient = builder.build()
////        return builder.build()
////
////        return RestHighLevelClient(builder).lowLevelClient
//
//        val clientConfiguration: ClientConfiguration = ClientConfiguration.builder()
//            .connectedTo(elasticsearchProperties.getHostAndPort())
//            .usingSsl(sslContext)
//            .withBasicAuth("elastic", "xKGjLPon5CK4_m3=Nc-G")
//            .build()
//
//        return RestClients.create(clientConfiguration).rest()
//    }

//    @Bean(name = ["elasticsearchOperations", "elasticsearchTemplate"])
//    fun elasticsearchOperations(
//        elasticsearchConverter: ElasticsearchConverter?
//    ): ElasticsearchOperations? {
//
//        RestHighLevelClient(elasticsearchClient,  RestClient::close, emptyList())
//
//        val template = ElasticsearchRestTemplate(elasticsearchClient!!, elasticsearchConverter!!)
//        template.refreshPolicy = refreshPolicy()
//        return template
//    }

}