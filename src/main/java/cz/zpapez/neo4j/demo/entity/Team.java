package cz.zpapez.neo4j.demo.entity;

import java.util.List;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Team {

    @Id @GeneratedValue
    private Long id;

    private String teamName;

    @Relationship(type = "OWNS", direction = Relationship.OUTGOING)
    private List<Unit> owns;

}
