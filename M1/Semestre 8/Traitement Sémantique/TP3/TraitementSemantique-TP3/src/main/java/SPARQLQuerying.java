import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

public class SPARQLQuerying {

	private static final String SPARQL_QUERY = 
	        "PREFIX movies: <http://www.lirmm.fr/ulliana/movies#>\n" +
	        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
	        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
	        "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
	        "PREFIX dbp: <http://dbpedia.org/>\n" +
	        
	        "SELECT ?artistID WHERE {?artistID a ?Class . ?Class rdfs:subClassOf movies:Artist .}";
	
	
		
	
	private static void evaluateQuery(InfModel infmodel) {
		Query query = QueryFactory.create(SPARQL_QUERY);
		System.out.println(SPARQL_QUERY);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, infmodel)) {
            ResultSet results = qexec.execSelect();
            ResultSetFormatter.out(System.out, results, query);
        }
        System.out.println("\n=======================================================================\n");
		
	}
	
	public static void main(String[] args) {
		// Load the schema and data models
		Model schema = RDFDataMgr.loadModel("src/main/resources/owlMovieSchema.ttl", Lang.TURTLE);
		Model data = RDFDataMgr.loadModel("src/main/resources/owlMovieData.ttl", Lang.TURTLE);

		// Get an OWL Reasoner
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();

		// Bind the reasoner to the schema
		reasoner = reasoner.bindSchema(schema);

		// Create an inference model using the reasoner and the data
		InfModel infmodel = ModelFactory.createInfModel(reasoner, data);

		System.out.println("\n\n==========RDF Triples before inferences========================================\n");
		RDFOWLReasoning.printTriples(data);

		System.out.println("\n\n==========RDF Inferred Triples ================================================\n");

		RDFOWLReasoning.printInferredTriples(infmodel, data);

		System.out.println("\n\n===============================================================================\n");

        // Execute SPARQL query on the inference model
        System.out.println("\n==========SPARQL Query Results=========================================\n");
        
        evaluateQuery(infmodel);
	}

	

	


}
