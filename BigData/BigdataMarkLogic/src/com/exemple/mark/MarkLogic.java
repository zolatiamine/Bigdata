package com.exemple.mark;

import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.semantics.jena.MarkLogicDatasetGraph;
import com.marklogic.semantics.jena.MarkLogicDatasetGraphFactory;

public class MarkLogic {

	private MarkLogicDatasetGraph graph;
	private static Dataset dataset;
	String query;
	QueryExecution qExecution;
	QuerySolution qSolution;


	 public static MarkLogicDatasetGraph loadPropsAndInit() {
	        Properties props = new Properties();
	        // two attempts to load
	        try {
	            props.load(new FileInputStream("resources/gradle.properties"));
	        } catch (IOException e) {
	            // gradle prefers this path.
	            try {
	                props.load(new FileInputStream("gradle.properties"));
	            } catch (IOException e2) {
	                System.err.println("problem loading properties file.");
	                System.exit(1);
	            }
	        }
	        String host = props.getProperty("admin");
	        int port = Integer.parseInt(props.getProperty("8000"));
	        String user = props.getProperty("admin");
	        String pass = props.getProperty("admin");
	        
	        DatabaseClient client = DatabaseClientFactory.newClient(host, port,
	                new DatabaseClientFactory.DigestAuthContext(user, pass));
	        MarkLogicDatasetGraph dg = MarkLogicDatasetGraphFactory
	                .createDatasetGraph(client);
	        return dg;
	    }
	
	public void configure() {
		graph = loadPropsAndInit();
	    RDFDataMgr.read(graph, "resources/drugbank_dump.nt", Lang.TURTLE);
        dataset = DatasetFactory.wrap(graph);
	}
	
    public void Query1() throws Exception {
    	query = "PREFIX rdf:<http://www.w3.org/2000/01/rdf-schema#label>\n" + 
    			"SELECT ?x ?o ?z\n" + 
    			"WHERE { ?x  rdf: ?o }";
    	qExecution = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qExecution.execSelect();
        while(results.hasNext()) {
            qSolution = results.next();
        }
        ResultSetFormatter.out(System.out, results);
    }
    
    public void Query2() throws Exception {
    	query = "PREFIX dat:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/creationDate>\n" + 
    			"\n" + 
    			"SELECT ?X ?y ?z\n" + 
    			"WHERE {?X ?y ?z . ?X dat: \"2005-06-13 13:24:05 UTC\"}" + 
    			"";
    	qExecution = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qExecution.execSelect();
        while(results.hasNext()) {
            qSolution = results.next();
        }
        ResultSetFormatter.out(System.out, results);
    }
    
    public void Query3() throws Exception {
    	query = "PREFIX dat:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/creationDate>\n" + 
    			"PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>\n" + 
    			"\n" + 
    			"\n" + 
    			"SELECT ?X ?y ?z\n" + 
    			"WHERE {?X ?y ?z . ?X dat: \"2005-06-13 13:24:05 UTC\" . ?X rdf: ?z}";
    	qExecution = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qExecution.execSelect();
        while(results.hasNext()) {
            qSolution = results.next();
        }
        ResultSetFormatter.out(System.out, results);
    }
    
    public void Query4() throws Exception {
    	query = "PREFIX cID:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/pubchemCompoundId>\n" + 
    			"PREFIX rdf:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugs/DB00001>\n" + 
    			"\n" + 
    			"\n" + 
    			"SELECT DISTINCT ?pubchemCompoundId\n" + 
    			"WHERE{\n" + 
    			"    rdf: cID: ?pubchemCompoundId .\n" + 
    			"}";
    	qExecution = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qExecution.execSelect();
        while(results.hasNext()) {
            qSolution = results.next();
        }
        ResultSetFormatter.out(System.out, results);
    }
    
    public void Query5() throws Exception {
    	query = "PREFIX pdb:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/toxicity>\n" + 
    			"\n" + 
    			"SELECT ?x ?toxicity\n" + 
    			"WHERE   { ?x pdb: ?toxicity\n" + 
    			"          FILTER regex(?toxicity, \"Reproductive\",\"i\") \n" + 
    			"        }";
    	qExecution = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qExecution.execSelect();
        while(results.hasNext()) {
            qSolution = results.next();
        }
        ResultSetFormatter.out(System.out, results);
    }
    
    public void Query6() throws Exception {
    	query = "SELECT ?v WHERE { ?v ?p \"1ITF\" }";
    	qExecution = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qExecution.execSelect();
        while(results.hasNext()) {
            qSolution = results.next();
        }
        ResultSetFormatter.out(System.out, results);
    }
    
    public void Query7() throws Exception {
    	query = "PREFIX pdb:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/toxicity>\n" + 
    			"\n" + 
    			"SELECT  ?toxicity\n" + 
    			"WHERE   { ?x pdb: ?toxicity\n" + 
    			"          FILTER regex(?toxicity, \"^Reproductive\") \n" + 
    			"        }";
    	qExecution = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qExecution.execSelect();
        while(results.hasNext()) {
            qSolution = results.next();
        }
        ResultSetFormatter.out(System.out, results);
    }
    
    public void Query8() throws Exception {
    	query = "PREFIX pdb:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/toxicity>\n" + 
    			"PREFIX rdf:<http://www.w3.org/2000/01/rdf-schema#label>\n" + 
    			"\n" + 
    			"SELECT ?y ?z\n" + 
    			" WHERE { ?x rdf:  ?y ;\n" + 
    			"           pdb:  ?z .\n" + 
    			"         FILTER isLiteral(?z) }";
    	qExecution = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qExecution.execSelect();
        while(results.hasNext()) {
            qSolution = results.next();
        }
        ResultSetFormatter.out(System.out, results);
    }
    public void Query9() throws Exception {
    	query = "PREFIX pdb:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/toxicity>\n" + 
    			"PREFIX rdf:<http://www.w3.org/2000/01/rdf-schema#label>\n" + 
    			"\n" + 
    			"SELECT ?y ?z\n" + 
    			" WHERE { ?x rdf:  ?y ;\n" + 
    			"           pdb:  ?z .\n" + 
    			"         FILTER isLiteral(?z) } LIMIT 3";
    	qExecution = QueryExecutionFactory.create(query, dataset);
        ResultSet results = qExecution.execSelect();
        while(results.hasNext()) {
            qSolution = results.next();
        }
        ResultSetFormatter.out(System.out, results);
    }

    private void mai() {
		configure();
	}
}
