package ccs.neu.edu.kids.mproj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import ccs.neu.edu.kids.mproj.Property;

public class Runner {

	public static void main (String args[]){

		int i = 0;
		CSVParser csvParser = new CSVParser();
		HashMap<String,List<Property>> classPropertyMap = csvParser.parseCSV("/Users/prasanna/Desktop/rdf_files_parsed_A.csv");
		Map<String,List<Property>> tempMap;
		//A temp map to mark class in map as visited 
		
		for (Map.Entry<String, List<Property>> entry : classPropertyMap.entrySet()) {
			tempMap = new HashMap<String, List<Property>>(classPropertyMap);
			createTriples(entry.getKey(),tempMap,i,classPropertyMap);
			i++;
		}
	}

	private static void createTriples(String className,
			Map<String, List<Property>> tempMap, int i,
			HashMap<String, List<Property>> classPropertyMap) {
		if(tempMap.isEmpty())
			return;
		for(Property p : classPropertyMap.get(className))
		{
			if(p.isObjectProperty())
			{
				createTriples(p.getType(), tempMap, i, classPropertyMap);;
				System.out.println(className + "#" + i + " " + p.getName() + " " + p.getName() + "#" + i);
				tempMap.remove(p.getType());
			} else {
				System.out.println(className + "#" + i + " " + p.getName() + " " + getObjectValue(p));
			}
		}
	}

	private static String getObjectValue(Property p) {
		if(p.getType().equals("String")){
		 return RandomStringUtils.randomAlphabetic(p.getMaxValue());	
		}
		Random r = new Random();
		return Integer.toString(r.nextInt(p.getMaxValue()));
	}
}
	/*
	private static void generateRandomData(HashMap<String,List<Property>> classPropertyMap) {
		
		Model model = ModelFactory.createDefaultModel();
		//Model model1 = ModelFactory.createDefaultModel();

		for (Map.Entry<String, List<Property>> entry : classPropertyMap.entrySet()) {
			Resource src = createResource(entry.getKey(),entry.getValue(),model,classPropertyMap);
		}

		RDFDataMgr.write(System.out, model, Lang.NTRIPLES) ;
		//print_details(model);

	}

	private static Resource createResource(String key, List<Property> value, Model model, HashMap<String, List<Property>> classPropertyMap) {
		Resource tempResource = model.createResource(key);
		com.hp.hpl.jena.rdf.model.Property tmpProperty = model.getProperty(key);
		
		for(ccs.neu.edu.kids.mproj.Property property : value){
			if(classPropertyMap.containsKey(property.getName())){
				createResource(property.getName(), classPropertyMap.get(property.getName()), model, classPropertyMap);
			} else{
				tempResource.addProperty(tmpProperty,"1");
			}
		}
		return tempResource;		
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
			System.out.println(" .");
		}
	}
}
*/

