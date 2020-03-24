package cz.zpapez.neo4j.demo.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cz.zpapez.neo4j.demo.entity.Team;


@RepositoryRestResource(collectionResourceRel = "teams", path = "teams")
public interface TeamRepository extends Neo4jRepository<Team, Long> {

}
