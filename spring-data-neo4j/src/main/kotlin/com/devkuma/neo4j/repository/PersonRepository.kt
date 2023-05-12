package com.devkuma.neo4j.repository

import com.devkuma.neo4j.entity.Person

import org.springframework.data.neo4j.repository.Neo4jRepository

interface PersonRepository : Neo4jRepository<Person, Long> {
    fun findByName(name: String): Person?
    fun findByTeammatesName(name: String): List<Person>
}