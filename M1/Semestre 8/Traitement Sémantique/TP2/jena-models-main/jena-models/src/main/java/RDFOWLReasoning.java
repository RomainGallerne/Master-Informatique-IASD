import java.util.Set;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

public class RDFOWLReasoning {

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
		printTriples(data);

		System.out.println("\n\n==========RDF Triples after inferences=========================================\n");

		printInferredTriples(infmodel, data);

		System.out.println("\n\n===============================================================================\n");

	}

	private static void printInferredTriples(InfModel infModel, Model originalModel) {
		// Create a temporary model to store the inferred triples
		Model temp = ModelFactory.createDefaultModel();
		temp.add(infModel.difference(originalModel));

		// Print inferred triples
		StmtIterator iter = temp.listStatements();
		while (iter.hasNext()) {
			Statement stmt = iter.nextStatement();
			if (isRelevantStatement(stmt)) {
				System.out.println(stmt.asTriple());

			}
		}
	}

	private static void printTriples(Model infModel) {
		StmtIterator iter = infModel.listStatements();

		while (iter.hasNext()) {
			Statement stmt = iter.nextStatement();

			// Check if the statement is relevant
			if (isRelevantStatement(stmt)) {
				System.out.println(stmt.asTriple());
			}
		}
	}

	private static boolean isRelevantStatement(Statement stmt) {
		Resource subject = stmt.getSubject();
		Property predicate = stmt.getPredicate();
		RDFNode object = stmt.getObject();

		Set<String> Pbanned = Set.of(RDFS.getURI(), OWL.getURI());

		Set<String> SObanned = Set.of(RDF.getURI(), RDFS.getURI(), OWL.getURI());

		if (!SObanned.contains(subject.getNameSpace())) {

			if (Pbanned.contains(predicate.getNameSpace())) {

				// Filter out statements with RDFS and OWL as predicates

				return false;

			}

			if (object.isResource()) {

				Resource o = (Resource) object;

				if (SObanned.contains(o.getNameSpace()))

					// Filter out statements with RDF, RDFS and OWL as object

					return false;
			}

			return true;
		}

		return false;
		// Filter out statements with RDF, RDFS and OWL as subject

	}
}
