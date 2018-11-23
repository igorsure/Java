package basicstar;

import java.util.ArrayList;
import java.util.HashSet;

public class Constellation {
	String id;
	String name;
	String capitalName;
	String info;
	HashSet<Star> stars;
    ArrayList<Star> lines=new ArrayList<Star>();
    public Constellation(String myname){
    	name=myname;
    	capitalName=name.toUpperCase();
    }
    public void addStar(Star star){
    	stars.add(star);
    }
    public ArrayList<Star> getLines(){
    	return lines;
    }
    public void addLine(Star star){
    	lines.add(star);
    }
    public String getName(){
    	return name;
    }
    public String getCapitalName(){
    	return capitalName;
    }
    public void dumpLines(){
    	for (Star s:lines){
    		System.out.println(s);
    	}
    	System.out.println("");
    }
    
}
