package com.devkuma.batch.config

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.JdbcPagingItemReader
import org.springframework.batch.item.database.Order
import org.springframework.batch.item.database.PagingQueryProvider
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.BeanPropertyRowMapper
import javax.sql.DataSource

@Configuration
class ExampleJobConfig(
    private var jobBuilderFactory: JobBuilderFactory,
    private var stepBuilderFactory: StepBuilderFactory,
    private var dataSource: DataSource,
) {

    @Bean
    fun ExampleJobConfig(): Job? {
        return jobBuilderFactory["exampleJob"]
            .start(step())
            .build()
    }

    @Bean
    fun step(): Step {
        return stepBuilderFactory["Step"]
            .chunk<Member, Member>(10)
            .reader(reader())
            .processor(processor())
            .writer(writer())
            .build()
    }
    
    @Bean
    @StepScope
    fun reader(): JdbcPagingItemReader<Member> {
        val parameterValues: MutableMap<String, Any> = HashMap()
        parameterValues["amount"] = "10000"

        // pageSize와 fethSize는 동일하게 설정
        return JdbcPagingItemReaderBuilder<Member>()
            .pageSize(10)
            .fetchSize(10)
            .dataSource(dataSource)
            .rowMapper(BeanPropertyRowMapper(Member::class.java))
            .queryProvider(customQueryProvider())
            .parameterValues(parameterValues)
            .name("JdbcPagingItemReader")
            .build()
    }

    @Bean
    @StepScope
    fun processor(): ItemProcessor<Member, Member> {
        return ItemProcessor {
            //1000원 추가 적립
            it.amount = it.amount + 1000
            it
        }
    }

    @Bean
    @StepScope
    fun writer(): JdbcBatchItemWriter<Member> {
        return JdbcBatchItemWriterBuilder<Member>()
            .dataSource(dataSource)
            .sql("UPDATE MEMBER SET AMOUNT = :amount WHERE ID = :id")
            .beanMapped()
            .build()
    }

    @Throws(Exception::class)
    fun customQueryProvider(): PagingQueryProvider {
        val queryProviderFactoryBean = SqlPagingQueryProviderFactoryBean()
        queryProviderFactoryBean.setDataSource(dataSource)
        queryProviderFactoryBean.setSelectClause("SELECT ID, NAME, EMAIL, NICK_NAME, STATUS, AMOUNT ")
        queryProviderFactoryBean.setFromClause("FROM MEMBER ")
        queryProviderFactoryBean.setWhereClause("WHERE AMOUNT >= :amount")
        val sortKey: MutableMap<String, Order> = HashMap<String, Order>()
        sortKey["id"] = Order.ASCENDING
        queryProviderFactoryBean.setSortKeys(sortKey)
        return queryProviderFactoryBean.getObject()
    }
}