package com.devkuma.neo4j

import com.devkuma.neo4j.entity.Person
import com.devkuma.neo4j.repository.PersonRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories
import kotlin.system.exitProcess

private val log = KotlinLogging.logger {}

@SpringBootApplication
@EnableNeo4jRepositories
class Neo4jApplication {

    @Bean
    fun demo(@Autowired personRepository: PersonRepository): CommandLineRunner {
        return CommandLineRunner {

            personRepository.deleteAll()

            var greg = Person()
            greg.name = "Greg"
            var roy = Person()
            roy.name = "Roy"
            val craig = Person()
            craig.name = "Craig"
            val team: List<Person> = listOf(greg, roy, craig)

            log.info("Before linking up with Neo4j...")
            team.stream().forEach { person: Person ->
                log.info("\t${person}")
            }

            personRepository.save(greg)
            personRepository.save(roy)
            personRepository.save(craig)

            greg = personRepository.findByName(greg.name)!!
            greg.worksWith(roy)
            greg.worksWith(craig)
            personRepository.save(greg)

            roy = personRepository.findByName(roy.name)!!
            roy.worksWith(craig)
            personRepository.save(roy)

            log.info("Lookup each person by name...")
            team.stream().forEach { person: Person ->
                log.info("\t${personRepository.findByName(person.name)}")
            }
            val teammates = personRepository.findByTeammatesName(craig.name)
            log.info("The following have ${craig.name} as a teammate...")
            teammates.stream()
                .forEach { person: Person -> log.info("\t${person.name}") }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<Neo4jApplication>(*args)
    exitProcess(0)
}
