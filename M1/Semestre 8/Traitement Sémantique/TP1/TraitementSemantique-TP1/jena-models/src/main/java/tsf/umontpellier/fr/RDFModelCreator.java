package tsf.umontpellier.fr;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

public class RDFModelCreator {

    public static void main(String[] args) {

        // Create an empty model
        Model model = ModelFactory.createDefaultModel();

        // Define namespace prefixes
        String TSD_namespace = "http://www.umontpellier.fr/traitementsemantiquedesdonnees#";
        String FOAF_namespace = "http://xmlns.com/foaf/0.1/";
        model.setNsPrefix("tsd", TSD_namespace);
        model.setNsPrefix("foaf", FOAF_namespace);
        
        
        Property nameRelation = model.createProperty(FOAF_namespace + "name");
        Property familynameRelation = model.createProperty(FOAF_namespace + "familiyname");
        
        
        // Create resources and properties
        // Emily
        Resource emily = model.createResource(TSD_namespace + "P12345");
        model.add(emily, RDF.type, model.createResource(TSD_namespace + "Patient"));
        model.add(emily, nameRelation, model.createResource(TSD_namespace + "Emily"));
        model.add(emily, familynameRelation, model.createResource(TSD_namespace + "Emily"));
  
        
        System.out.println("Exporting the RDF Model\n");
        // export model with RDF/Turtle syntax
        model.write(System.out, "Turtle");
    }
}
