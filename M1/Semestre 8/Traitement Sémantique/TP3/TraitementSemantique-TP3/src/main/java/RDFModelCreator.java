import java.time.LocalDate;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.OWL;

public class RDFModelCreator {

    public static void main(String[] args) {

        // Create an empty model
        Model model = ModelFactory.createDefaultModel();

        // Define namespace prefixes
        String MOV_namespace = "http://www.lirmm.fr/ulliana/movies";
        String FOAF_namespace = "http://xmlns.com/foaf/0.1/";
        String DBP_namespace = "http://dbpedia.org/";
        model.setNsPrefix("movies", MOV_namespace);
        model.setNsPrefix("foaf", FOAF_namespace);
        model.setNsPrefix("dbpedia", DBP_namespace);
        
        // Propriétés et ressources
        Resource director = model.createResource(MOV_namespace + "Director");
        Resource actor = model.createResource(MOV_namespace + "Actor");
        Resource artist = model.createResource(MOV_namespace + "Artist");
        Resource movie = model.createResource(MOV_namespace + "Movie");
        
        Property directedBy = model.createProperty(MOV_namespace + "directedBy");
        Property playsIn = model.createProperty(MOV_namespace + "playsIn");
        Property title = model.createProperty(MOV_namespace + "title");
        
        //Hierarchie du vocabulaire des films
        model.add(director, RDFS.subClassOf, artist);
        model.add(actor, RDFS.subClassOf, artist);
        model.add(title, RDF.type, OWL.DatatypeProperty);
        model.add(directedBy, RDFS.range, director);
        model.add(directedBy, RDFS.domain, movie);
        model.add(playsIn, RDFS.range, movie);
        model.add(playsIn, RDFS.domain, actor);
        model.add(title, RDFS.domain, movie);
        
        // Film 1
        Resource m1 = model.createResource(MOV_namespace + "m1");
        model.add(m1, RDF.type, movie);
        
        // Film 2
        Resource m2 = model.createResource(MOV_namespace + "m2");
        model.add(m2, title, model.createLiteral("Vertigo"));
        
        // Film 3
        Resource m3 = model.createResource(MOV_namespace + "m3");
        Resource ah = model.createResource(DBP_namespace + "Alfred_Hitchcock");
        model.add(m3, directedBy, ah);
        
        // Film 4
        Resource a1 = model.createResource(MOV_namespace + "a1");
        Resource m4 = model.createResource(MOV_namespace + "m4");
        model.add(a1, playsIn, m4);
        
        System.out.println("Exporting the RDF Model\n");
        // export model with RDF/Turtle syntax
        model.write(System.out, "Turtle");
    }
}
