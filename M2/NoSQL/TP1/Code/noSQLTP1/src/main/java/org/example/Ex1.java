package org.example;

import java.io.File;
import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;



public class Ex1 {
	   private static final File DB_Folder = new File("/home/e20200007056/Bureau/Cours/M2/NoSQL/TP/TP1/Systeme/data/databases/graph.db/");
	   
	   
	   public static void main(String[] args) {
	      GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
	      GraphDatabaseService graphDB = dbFactory.newEmbeddedDatabase(DB_Folder);

	      try (Transaction tx =graphDB.beginTx()) {

              Node saint_gaudens = graphDB.createNode(Labl.COMMUNE);
              saint_gaudens.setProperty("name", "SAINT-GAUDENS");
              saint_gaudens.setProperty("codeinsee", "31483");
              saint_gaudens.setProperty("latitude", 43.106895);
              saint_gaudens.setProperty("longitude", 0.723763);

              Node balma = graphDB.createNode(Labl.COMMUNE);
              balma.setProperty("name", "BALMA");
              balma.setProperty("codeinsee", "31044");
              balma.setProperty("latitude", 43.606098);
              balma.setProperty("longitude", 1.500032);

              Result result = graphDB.execute(
                      "MATCH (h:Departement)" +
                              "WHERE h.name = 'HAUTE-GARONNE'" +
                              "RETURN h");
              Node hautegaronne = null;
              while (result.hasNext()) {
                  Map<String, Object> row = result.next();
                  for (String key : result.columns()) {
                      hautegaronne = (Node) row.get(key);
                      System.out.println(hautegaronne);
                  }
              }

              saint_gaudens.createRelationshipTo(hautegaronne, Relation.WITHIN);
              balma.createRelationshipTo(hautegaronne, Relation.WITHIN);

              tx.success();
          }
	   }
}
