
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;


public class Runner{
	

	static Model model = ModelFactory.createDefaultModel();
	static String has_property = "HAS_PROPERTY";
	
	public static void main (String args[]){
		int i = 0;
		CSVParser csvParser = new CSVParser();
		HashMap<String,List<Property1>> classPropertyMap = csvParser.parseCSV("C:/Back to college/Summer 2015/Master s Project/rdf_files_parsed_A.csv");
		Map<String,List<Property1>> tempMap;
		//A temp map to mark class in map as visited 
		for (Map.Entry<String, List<Property1>> entry : classPropertyMap.entrySet()) {
			tempMap = new HashMap<String, List<Property1>>(classPropertyMap);
			createTriples(entry.getKey(),tempMap,i,classPropertyMap);
			i++;
		}
		//print_details(model);
		String queryString = 
				"SELECT ?name_property WHERE {?name_property <isCapacityOf> \"isCapacityOf0\"}" ;
		Query query = QueryFactory.create(queryString) ;
		
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		try {
            ResultSet rs = qexec.execSelect() ;
            while(rs.hasNext()) {
            	System.out.println("Name: ") ;
                QuerySolution rb = rs.nextSolution() ;
                //System.out.println(rb + "\n");
                
                RDFNode x = rb.get("name_property") ;
                System.out.println("   "+x) ;
            }
        } catch(Exception c)  {
        	System.out.println(c);
        }
		finally{
			qexec.close() ;
        }
	}
        

	private static void createTriples(String className,
			Map<String, List<Property1>> tempMap, int i,
			HashMap<String, List<Property1>> classPropertyMap) {
		if(tempMap.isEmpty())
		{
			/*System.out.println("return null");
			Model model1 = ModelFactory.createDefaultModel();
			Resource nullR = model1.createResource("null");
			return nullR;*/
			return ;
		}
		for(Property1 p : classPropertyMap.get(className))
		{
			if(p.isObjectProperty())
			{
				//Resource r1 = createTriples(p.getType(), tempMap, i, classPropertyMap);
				Property name_property = model.getProperty(p.getName());
				//System.out.println(className + "#" + i + " " + p.getName() + " " + p.getName() + "#" + i);
				tempMap.remove(p.getType());
				Property hp = model.getProperty(has_property);
				Resource r = model.createResource(className+"#"+i)
						.addProperty(name_property, p.getName() + i);
					//	.addProperty(hp, r1);
				
			} else {
				//System.out.println(className + "#" + i + " " + p.getName() + " " + getObjectValue(p));
				Property name_property = model.getProperty(p.getName());
				Resource r1 = model.createResource(className +"#"+i)
						.addProperty(name_property, p.getName() + i);
				//return r1;
			}
		}
		//Model model1 = ModelFactory.createDefaultModel();
		//Resource nullR = model1.createResource("null");
		//System.out.println("returns after for");
		return;
	}

	private static String getObjectValue(Property1 p) {
		if(p.getType().equals("String")){
		 return RandomStringUtils.randomAlphabetic(p.getMaxValue());	
		}
		Random r = new Random();
		return Integer.toString(r.nextInt(p.getMaxValue()));
	}
	public static void print_details(Model model){
		// list the statements in the graph
		StmtIterator iter = model.listStatements();

		// print out the predicate, subject and object of each statement
		while (iter.hasNext()) {
			Statement stmt      = iter.nextStatement();         // get next statement
			com.hp.hpl.jena.rdf.model.Resource  subject   = stmt.getSubject();   // get the subject
			com.hp.hpl.jena.rdf.model.Property  predicate = stmt.getPredicate(); // get the predicate
			RDFNode   object    = stmt.getObject();    // get the object
			
			System.out.print(subject.toString());
			System.out.print(" " + predicate.toString() + " ");
			if (object instanceof Resource) {
				System.out.print(object.toString());
			} else {
				// object is a literal
				System.out.print(" \"" + object.toString() + "\"");
			}
			System.out.println(".");
		}
	}
}

