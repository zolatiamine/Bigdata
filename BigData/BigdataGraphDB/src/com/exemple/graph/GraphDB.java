package com.exemple.graph;
import org.openrdf.OpenRDFException;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.http.HTTPRepository;


public class GraphDB { 
		  private static final String SERVER = "http://localhost:7200/repositories";
		  private static final String REPOSITORY = "MyData";

		  private static String query1 = "PREFIX rdf:<http://www.w3.org/2000/01/rdf-schema#label>\\n\" + \r\n" + 
		  		"    			\"SELECT ?x ?o ?z\\n\" + \r\n" + 
		  		"    			\"WHERE { ?x  rdf: ?o }s";
		  private static String query2 = "PREFIX dat:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/creationDate>\\n\" + \r\n" + 
		  		"    			\"\\n\" + \r\n" + 
		  		"    			\"SELECT ?X ?y ?z\\n\" + \r\n" + 
		  		"    			\"WHERE {?X ?y ?z . ?X dat: \\\"2005-06-13 13:24:05 UTC\\\"}\\n";
		  private static String query3 = "PREFIX dat:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/creationDate>\\n\" + \r\n" + 
		  		"    			\"PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>\\n\" + \r\n" + 
		  		"    			\"\\n\" + \r\n" + 
		  		"    			\"\\n\" + \r\n" + 
		  		"    			\"SELECT ?X ?y ?z\\n\" + \r\n" + 
		  		"    			\"WHERE {?X ?y ?z . ?X dat: \\\"2005-06-13 13:24:05 UTC\\\" . ?X rdf: ?z}";
		  private static String query4 = "PREFIX cID:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/pubchemCompoundId>\\n\" + \r\n" + 
		  		"    			\"PREFIX rdf:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugs/DB00001>\\n\" + \r\n" + 
		  		"    			\"\\n\" + \r\n" + 
		  		"    			\"\\n\" + \r\n" + 
		  		"    			\"SELECT DISTINCT ?pubchemCompoundId\\n\" + \r\n" + 
		  		"    			\"WHERE{\\n\" + \r\n" + 
		  		"    			\"    rdf: cID: ?pubchemCompoundId .\\n\" + \r\n" + 
		  		"    			\"}\\n";
		  private static String query5 = "\"PREFIX pdb:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/toxicity>\\n\" + \r\n" + 
		  		"    			\"\\n\" + \r\n" + 
		  		"    			\"SELECT ?x ?toxicity\\n\" + \r\n" + 
		  		"    			\"WHERE   { ?x pdb: ?toxicity\\n\" + \r\n" + 
		  		"    			\"          FILTER regex(?toxicity, \\\"Reproductive\\\",\\\"i\\\") \\n\" + \r\n" + 
		  		"    			\"        }";
		  private static String query6 = "SELECT ?v WHERE { ?v ?p \\\"1ITF\\\" }";
		  private static String query7 = "PREFIX pdb:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/toxicity>\\n\" + \r\n" + 
		  		"    			\"\\n\" + \r\n" + 
		  		"    			\"SELECT  ?toxicity\\n\" + \r\n" + 
		  		"    			\"WHERE   { ?x pdb: ?toxicity\\n\" + \r\n" + 
		  		"    			\"          FILTER regex(?toxicity, \\\"^Reproductive\\\") \\n\" + \r\n" + 
		  		"    			\"        }";
		  private static String query8 = "PREFIX pdb:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/toxicity>\\n\" + \r\n" + 
		  		"    			\"PREFIX rdf:<http://www.w3.org/2000/01/rdf-schema#label>\\n\" + \r\n" + 
		  		"    			\"\\n\" + \r\n" + 
		  		"    			\"SELECT ?y ?z\\n\" + \r\n" + 
		  		"    			\" WHERE { ?x rdf:  ?y ;\\n\" + \r\n" + 
		  		"    			\"           pdb:  ?z .\\n\" + \r\n" + 
		  		"    			\"         FILTER isLiteral(?z) }";
		  private static String query9 = "PREFIX pdb:<http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/toxicity>\\n\" + \r\n" + 
		  		"    			\"PREFIX rdf:<http://www.w3.org/2000/01/rdf-schema#label>\\n\" + \r\n" + 
		  		"    			\"\\n\" + \r\n" + 
		  		"    			\"SELECT ?y ?z\\n\" + \r\n" + 
		  		"    			\" WHERE { ?x rdf:  ?y ;\\n\" + \r\n" + 
		  		"    			\"           pdb:  ?z .\\n\" + \r\n" + 
		  		"    			\"         FILTER isLiteral(?z) } LIMIT 3";
		  
		  
		   
		  
		  private static RepositoryConnection getRepositoryConnection() throws OpenRDFException {
		    Repository repository = new HTTPRepository(SERVER, REPOSITORY);
		    repository.initialize();
		    RepositoryConnection repositoryConnection = 
		      repository.getConnection();
		    return repositoryConnection;
		  }
		  

		  private static void query(RepositoryConnection repositoryConnection, String query) throws OpenRDFException {
		    
		    TupleQuery tupleQuery = repositoryConnection.prepareTupleQuery(QueryLanguage.SPARQL, query);
		    TupleQueryResult result = null;
		    try {
		      result = tupleQuery.evaluate();
		      while (result.hasNext()) {
		        BindingSet bindingSet = result.next();
		      }
		    }
		    catch (QueryEvaluationException qee) {
		    } finally {
		      result.close();
		    }    
		  }  
		  
		  public static void main(String[] args) throws OpenRDFException {
			  
		    RepositoryConnection repositoryConnection = null ;
		    try {   
		      repositoryConnection = getRepositoryConnection();
		      
		      query(repositoryConnection , query1);      
		      query(repositoryConnection , query2);
		      query(repositoryConnection , query3);
		      query(repositoryConnection , query4);
		      query(repositoryConnection , query5);
		      query(repositoryConnection , query6);
		      query(repositoryConnection , query7);
		      query(repositoryConnection , query8);
		      query(repositoryConnection , query9);
		      
		    } catch (Throwable t) {
		    } finally {
		      repositoryConnection.close();
		    }
		  }  
		}