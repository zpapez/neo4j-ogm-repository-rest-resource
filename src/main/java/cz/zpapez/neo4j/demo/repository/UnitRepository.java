package cz.zpapez.neo4j.demo.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cz.zpapez.neo4j.demo.entity.Unit;

@RepositoryRestResource(collectionResourceRel = "units", path = "units")
public interface UnitRepository extends Neo4jRepository<Unit, Long> {

}
