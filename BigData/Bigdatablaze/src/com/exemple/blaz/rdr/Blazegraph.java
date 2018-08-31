package com.exemple.blaz.rdr;


import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

public class Blazegraph {
    public static final String URL = "http://localhost:9999/bigdata/namespace/test";
    
    public static Query query1 = QueryFactory.create("\"PREFIX rdf:<http://www.w3.org/2000/01/rdf-schema#label>\\n\" + \n" + 
    		"    			\"SELECT ?x ?o ?z\\n\" + \n" + 
    		"    			\"WHERE { ?x  rdf: ?o }\"") ;
    
    
    public static Query query2 = QueryFactory.create("PREFIX dat:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/creationDate>\\n\" + \n" + 
    		"    			\"\\n\" + \n" + 
    		"    			\"SELECT ?X ?y ?z\\n\" + \n" + 
    		"    			\"WHERE {?X ?y ?z . ?X dat: \\\"2005-06-13 13:24:05 UTC\\\"}") ;
    
    public static Query query3 = QueryFactory.create("PREFIX dat:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/creationDate>\\n\" + \n" + 
    		"    			\"PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>\\n\" + \n" + 
    		"    			\"\\n\" + \n" + 
    		"    			\"\\n\" + \n" + 
    		"    			\"SELECT ?X ?y ?z\\n\" + \n" + 
    		"    			\"WHERE {?X ?y ?z . ?X dat: \\\"2005-06-13 13:24:05 UTC\\\" . ?X rdf: ?z}") ;
    
    public static Query query4 = QueryFactory.create("PREFIX cID:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/pubchemCompoundId>\\n\" + \n" + 
    		"    			\"PREFIX rdf:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugs/DB00001>\\n\" + \n" + 
    		"    			\"\\n\" + \n" + 
    		"    			\"\\n\" + \n" + 
    		"    			\"SELECT DISTINCT ?pubchemCompoundId\\n\" + \n" + 
    		"    			\"WHERE{\\n\" + \n" + 
    		"    			\"    rdf: cID: ?pubchemCompoundId .\\n\" + \n" + 
    		"    			\"}") ;
    
    public static Query query5 = QueryFactory.create("PREFIX pdb:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/toxicity>\\n\" + \n" + 
    		"    			\"\\n\" + \n" + 
    		"    			\"SELECT ?x ?toxicity\\n\" + \n" + 
    		"    			\"WHERE   { ?x pdb: ?toxicity\\n\" + \n" + 
    		"    			\"          FILTER regex(?toxicity, \\\"Reproductive\\\",\\\"i\\\") \\n\" + \n" + 
    		"    			\"        }") ;
    
    public static Query query6 = QueryFactory.create("SELECT ?v WHERE { ?v ?p \\\"1ITF\\\" }") ;
    
    public static Query query7 = QueryFactory.create("PREFIX pdb:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/toxicity>\\n\" + \n" + 
    		"    			\"\\n\" + \n" + 
    		"    			\"SELECT  ?toxicity\\n\" + \n" + 
    		"    			\"WHERE   { ?x pdb: ?toxicity\\n\" + \n" + 
    		"    			\"          FILTER regex(?toxicity, \\\"^Reproductive\\\") \\n\" + \n" + 
    		"    			\"        }") ;
    
    public static Query query8 = QueryFactory.create("PREFIX pdb:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/toxicity>\\n\" + \n" + 
    		"    			\"PREFIX rdf:<http://www.w3.org/2000/01/rdf-schema#label>\\n\" + \n" + 
    		"    			\"\\n\" + \n" + 
    		"    			\"SELECT ?y ?z\\n\" + \n" + 
    		"    			\" WHERE { ?x rdf:  ?y ;\\n\" + \n" + 
    		"    			\"           pdb:  ?z .\\n\" + \n" + 
    		"    			\"         FILTER isLiteral(?z) }") ;
    
    
    public static Query query9 = QueryFactory.create("PREFIX pdb:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/toxicity>\\n\" + \n" + 
    		"    			\"PREFIX rdf:<http://www.w3.org/2000/01/rdf-schema#label>\\n\" + \n" + 
    		"    			\"\\n\" + \n" + 
    		"    			\"SELECT ?y ?z\\n\" + \n" + 
    		"    			\" WHERE { ?x rdf:  ?y ;\\n\" + \n" + 
    		"    			\"           pdb:  ?z .\\n\" + \n" + 
    		"    			\"         FILTER isLiteral(?z) } LIMIT 3") ;
    
    
    public static void load_Select(RDFConnection connection, Query query) {
    	try {	
    		connection.loadDataset("resources/rdr_test.ttl");
		    
		    QueryExecution qExec = connection.query(query);
		    ResultSet rs = qExec.execSelect() ;
		    while(rs.hasNext()) {
		        QuerySolution qs = rs.next() ;
		        Resource s = qs.getResource("x") ;
		        System.out.println("Resource : " + s) ;
		    }
		    qExec.close() ;
		    connection.end();
		    }catch (Exception e) {
		       System.out.println(e.getMessage());
		    }finally {
		    	connection.close();
		    }
    }
    
	public static void main(String[] args) throws Exception {
		
		
		RDFConnection connection = RDFConnectionFactory.connect(URL);
		load_Select(connection, query1);
		load_Select(connection, query2);
		load_Select(connection, query3);
		load_Select(connection, query4);
		load_Select(connection, query5);
		load_Select(connection, query6);
		load_Select(connection, query7);
		load_Select(connection, query8);
		load_Select(connection, query9);
	}
}
