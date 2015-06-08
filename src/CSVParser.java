
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Parses the CSV file to in-memory data structure 
 * which holds the class and property mapping
 */
public class CSVParser {

	private static final int MAX_NUMBER_OF_COLUMNS = 4;
	private HashMap<String, List<Property1>> classPropertyMap ; 

	public HashMap parseCSV(String path){
		//TODO: Use POJO for csv instead of reading line by line
		classPropertyMap = new HashMap<>();
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
			br = new BufferedReader(new FileReader(path));
			br.readLine();
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] tokens = line.split(cvsSplitBy);
				if(tokens.length > MAX_NUMBER_OF_COLUMNS){
					if(!classPropertyMap.containsKey(tokens[0])){
						List<Property1> prptyLst = new ArrayList<>();	
						classPropertyMap.put(tokens[0], prptyLst);	
					} 
					Property1 tempProperty = new Property1();
					tempProperty.setName(tokens[1]);
					tempProperty.setType(tokens[2]);
					tempProperty.setObjectProperty(tokens[3].equals("Object"));
					if(!tempProperty.isObjectProperty())
					   tempProperty.setMaxValue(Integer.parseInt(tokens[4]));
					classPropertyMap.get(tokens[0]).add(tempProperty);
					//tempProperty.setMaxValue(tokens[4]);
					//System.out.println(tokens[0] + tokens[1]);
				}
			}
			/*for (Map.Entry<String, List<Property>> entry : classPropertyMap.entrySet()) {
				System.out.println(entry.getKey()+" : "+ entry.getValue().toString());
			}
			*/
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//System.out.println("Done");
		return classPropertyMap;
	}
}
