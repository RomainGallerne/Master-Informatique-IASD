package org.example;

import org.neo4j.graphdb.RelationshipType;


public enum Relation implements RelationshipType {

    NEARBY, WITHIN, CHEF_LIEU_DEPT, CHEF_LIEU_REG
}