package com.devkuma.neo4j.entity

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import java.util.*
import java.util.stream.Collectors

@Node
class Person {

    @Id
    @GeneratedValue
    var id: Long = 0
    var name: String = ""

    @Relationship(type = "TEAMMATE")
    var teammates: MutableSet<Person>? = null

    fun worksWith(person: Person) {
        if (teammates == null) {
            teammates = HashSet()
        }
        teammates!!.add(person)
    }

    override fun toString(): String {
        return ("$name's teammates => " +
                Optional
                    .ofNullable(teammates)
                    .orElse(mutableSetOf()).stream()
                    .map { obj: Person -> obj.name }
                    .collect(Collectors.toList()))
    }
}