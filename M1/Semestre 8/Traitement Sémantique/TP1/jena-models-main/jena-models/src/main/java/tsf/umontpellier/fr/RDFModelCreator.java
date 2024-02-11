package tsf.umontpellier.fr;

import java.time.LocalDate;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

public class RDFModelCreator {

    public static void main(String[] args) {

        // Create an empty model
        Model model = ModelFactory.createDefaultModel();

        // Define namespace prefixes
        String TSD_namespace = "http://www.umontpellier.fr/traitementsemantiquedesdonnees#";
        String FOAF_namespace = "http://xmlns.com/foaf/0.1/";
        String MEDI_namespace = "http://www.medicalRDF.org";
        String DISP_namespace = "http://www.deviceRDF.org";
        model.setNsPrefix("tsd", TSD_namespace);
        model.setNsPrefix("foaf", FOAF_namespace);
        model.setNsPrefix("med", MEDI_namespace);
        model.setNsPrefix("disp", DISP_namespace);
        
        // Propriétés et ressources
        Resource patient = model.createResource(MEDI_namespace + "patient");
        Resource hypertension = model.createResource(MEDI_namespace + "Hypertension");
        Resource allergy = model.createResource(MEDI_namespace + "Allergy");
        Resource diabeteT2 = model.createResource(MEDI_namespace + "DiabeteType2");
        Resource diabete = model.createResource(MEDI_namespace + "Diabete");
        Resource condition = model.createResource(MEDI_namespace + "Condition");
        Resource metformine = model.createResource(MEDI_namespace + "Metformine");
        
        Property nom = model.createProperty(FOAF_namespace + "name");
        Property nomFamille = model.createProperty(FOAF_namespace + "familiyname");
        Property age = model.createProperty(FOAF_namespace + "age");
        Property possede = model.createProperty(DISP_namespace + "own");
        Property prend = model.createProperty(TSD_namespace + "take");
        Property date = model.createProperty(TSD_namespace + "date");
        Property pas = model.createProperty(TSD_namespace + "step");
        Property bpm = model.createProperty(MEDI_namespace + "bpm");
        Property prescription = model.createProperty(MEDI_namespace + "prescription");
        Property pressionSang = model.createProperty(MEDI_namespace + "bloodPressure");
        Property tauxSucre = model.createProperty(MEDI_namespace + "sugar");
        Property aCondition = model.createProperty(MEDI_namespace + "condition");
        Property valeur = model.createProperty(TSD_namespace + "valeur");
        
        //Hierarchie du vocabulaire de médecine
        model.add(hypertension, RDFS.subClassOf, condition);
        model.add(allergy, RDFS.subClassOf, condition);
        model.add(diabete, RDFS.subClassOf, condition);
        model.add(diabeteT2, RDFS.subClassOf, diabete);
        
        // Emily
        Resource emily = model.createResource(TSD_namespace + "P12345");
        Resource dsr = model.createResource("dsr");
        Resource dsw = model.createResource("dsw");
        Resource m1 = model.createResource("m1");
        Resource m2 = model.createResource("m2");
        Resource sr = model.createResource(DISP_namespace + "SmartRing");
        Resource sw = model.createResource(DISP_namespace + "SmartWatch");
        Resource dev = model.createResource(DISP_namespace + "Device");
        model.add(emily, RDF.type, patient);
        model.add(emily, nom, model.createLiteral("Emily"));
        model.add(emily, possede, dsr);
        model.add(emily, possede, dsw);
        model.add(dsr, RDF.type, sr);
        model.add(dsw, RDF.type, sw);
        model.add(dsr, prend, m1);
        model.add(dsw, prend, m2);
        model.add(m1, date, model.createTypedLiteral(LocalDate.of(2024, 1, 30)));
        model.add(m1, pas, model.createTypedLiteral(8500));
        model.add(m2, date, model.createTypedLiteral(LocalDate.of(2024, 1, 30)));
        model.add(m2, pas, model.createTypedLiteral(8500));
        model.add(sr, RDFS.subClassOf, dev);
        model.add(sw, RDFS.subClassOf, dev);
        
        
        // Alice
        Resource alice = model.createResource(TSD_namespace + "P1");
        Resource c1 = model.createResource(TSD_namespace + "c1");
        model.add(alice, RDF.type, patient);
        model.add(alice, nom, model.createLiteral("Alice"));
        model.add(alice, age, model.createTypedLiteral(30));
        model.add(alice, aCondition, c1);
        model.add(c1, RDF.type, hypertension);
        
        // Bob
        Resource bob = model.createResource(TSD_namespace + "P2");
        Resource c2 = model.createResource(TSD_namespace + "c2");
        model.add(bob, RDF.type, patient);
        model.add(bob, nom, model.createLiteral("Bob"));
        model.add(bob, age, model.createTypedLiteral(25));
        model.add(bob, aCondition, c2);
        model.add(c2, RDF.type, allergy);
        
        // Charlie
        Resource charlie = model.createResource(TSD_namespace + "P3");
        Resource c3 = model.createResource(TSD_namespace + "c3");
        model.add(charlie, RDF.type, patient);
        model.add(charlie, nom, model.createLiteral("Charlie"));
        model.add(charlie, age, model.createTypedLiteral(40));
        model.add(charlie, aCondition, c3);
        model.add(c3, RDF.type, diabete);
        
        // Jean
        Resource jean = model.createResource(TSD_namespace + "JDP123");
        model.add(jean, RDF.type, patient);
        Resource c4 = model.createResource(TSD_namespace + "c4");
        Resource c5 = model.createResource(TSD_namespace + "c5");
        model.add(jean, nom, model.createLiteral("Jean"));
        model.add(jean, nomFamille, model.createLiteral("Dupont"));
        model.add(jean, age, model.createTypedLiteral(58));
        model.add(jean, aCondition, c4);
        model.add(jean, aCondition, c5);
        model.add(c4, RDF.type, hypertension);
        model.add(c5, RDF.type, diabeteT2);
        
        // John
        Resource john = model.createResource(TSD_namespace + "JOH600");
        Resource pr1 = model.createResource(MEDI_namespace + "pr1");
        Resource mbp = model.createResource(TSD_namespace + "mbp");
        Resource mts = model.createResource(TSD_namespace + "mts");
        model.add(john, RDF.type, patient);
        model.add(john, nom, model.createLiteral("John"));        
        model.add(john, prescription, pr1);
        model.add(pr1, RDF.type, metformine);
        model.add(john, pressionSang, mbp);
        model.add(john, tauxSucre, mts);
        model.add(mbp, valeur, model.createLiteral("140/90mmHg"));
        model.add(mts, valeur, model.createLiteral("180mg/dL"));
        
        System.out.println("Exporting the RDF Model\n");
        // export model with RDF/Turtle syntax
        model.write(System.out, "Turtle");
    }
}
