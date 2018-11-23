package basicstar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NavigableSet;
import java.util.TreeMap;

public class ConstellationList {
	HashSet<String> splitted=new HashSet<String>();
	TreeMap<String,Constellation> constellations=new TreeMap<String,Constellation>();
	HashMap<String,String> capital=new HashMap<String,String>(); //just link from capital ids to ids
	public ConstellationList(){
		fillInSplitted();
	}
	public void fillInSplitted(){
		String[] arr={"And","Cas","Cep","Cet","Lyn","Peg","Phe","Psc","Scl","Ser","Tuc","UMi"};
		for (int i=0;i<arr.length;i++){
			splitted.add(arr[i]);
		}
	}
	public boolean isSplitted(String s){
		return splitted.contains(s);
	}
	public void addConstellation(Constellation con){
		constellations.put(con.getName(),con );
		capital.put( con.getCapitalName(),con.getName());
	}
	public NavigableSet<String> getConstellations(){
		return constellations.navigableKeySet();
	}
	public String decapitalize(String cnst){
		return capital.get(cnst);
	}
	public Constellation getConstellation(String key){
		return constellations.get(key);
	}
	public int size(){
		return constellations.size();
	}
	
	public void loadLines(){
		String csvFile = "ConstellationLines.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		HashSet<String> hs=new HashSet<String>(); 
		try {

			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] items= line.split(cvsSplitBy);
				String consName=decapitalize(items[0]);
				Constellation constel=this.getConstellation(consName);
				constel.addLine(new Star(line));
			}
				

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
	
	}
}
