package cz.zpapez.neo4j.demo.entity;

import java.util.List;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonBackReference;

@NodeEntity
public class Unit {

    @Id @GeneratedValue
    private Long id;

    private String unitName;

    @Relationship(type = "OWNS", direction = Relationship.INCOMING)
    @JsonBackReference
    private List<Team> ownedBy;

}
