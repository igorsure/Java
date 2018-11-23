package basicstar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


public class StarList {
	ArrayList<Star> stars=new ArrayList<Star>();
	public String currentConstellation="Ori";
	public String getCurrentConstellation(){
		return currentConstellation;
	}
	public void setCurrentConstellation(String constln){
		currentConstellation=constln;
	}
	public boolean loadStars(ConstellationList constList){
		//String csvFile = "data/hygdata_v3.csv";
		String csvFile = "hygdata_v3.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		stars=new ArrayList<Star>();
		HashSet<String> hs=new HashSet<String>(); 
		try {
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();
			while ((line = br.readLine()) != null) {

			        // use comma as separator
				Star star = new Star(line.split(cvsSplitBy));
				stars.add(star);
				if (!star.getConstellation().isEmpty()) {
					hs.add(star.getConstellation());
				}
				String constellation=star.getConstellation();
				if (constList.isSplitted(constellation) ) {
					double ra=star.getRA();
					if (ra<12) {
						star.setRA(ra+24);
					}
					}
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
		
		ArrayList<String> constellations=new ArrayList<String>(hs);
		Collections.sort(constellations);
		for (int i=0;i<constellations.size();i++){
			constList.addConstellation(new Constellation(constellations.get(i)));
		}
		constList.loadLines();
		return true;
	}
	public Star[] getConstellation(String cn, boolean withNames, double magnitude){
		ArrayList<Star> arr= new ArrayList<Star>();
		for (int i=0;i<stars.size(); i++){
			Star star=stars.get(i);
			if (withNames && !star.named()){
				continue;
			}
			
			if (star.getConstellation().equals(cn)){
				arr.add(star);
			}
		}
		
		return (Star[]) arr.toArray(new Star[arr.size()]);
	}
   public void dump(Star[] st){
	   for (int i=0;i<st.length;i++){
		   System.out.println(st[i]);
	   }
   }
   public void draw(MainApp pa, Star[] st){
	   int left=pa.getPanelWidth();
	   int top=0;
	   int border=10;
	   double inf=1000000;
	   double minRA=inf,maxRA=-inf,minDec=inf,maxDec=-inf;
	   
       for (int i=0;i<st.length;i++){
    	   minRA=Math.min(minRA,st[i].getRA());
    	   maxRA=Math.max(maxRA,st[i].getRA());
    	   minDec=Math.min(minDec,st[i].getDec());
    	   maxDec=Math.max(maxDec,st[i].getDec());
       }
       pa.redraw();
       for (int i=0;i<st.length;i++){
    	   int stX=(int) pa.map( (float) st[i].getRA(),(float) minRA,(float) maxRA,pa.width-border,left+border);
    	   int stY=(int) pa.map( (float) st[i].getDec(),(float) minDec,(float) maxDec,pa.height-border,top+border);
    	   st[i].draw(pa, stX, stY);
       }
       drawCurrentConstellationLines(pa, minRA, maxRA, minDec, maxDec);
   	
   }
   public void drawCurrentConstellationLines( MainApp pa,double minRA,double maxRA,double minDec,double maxDec){
	   int left=pa.getPanelWidth();
	   int top=0;
	   int border=10;
	   ArrayList<Star> lines=pa.getConstellationList().getConstellation(currentConstellation).getLines();
	   for(int i=0;i<lines.size()-1;i++){
		   Star star1=lines.get(i);
		   Star star2=lines.get(i+1);
		   if (star1.getName()=="*empty*" ||  star2.getName()=="*empty*") continue;
    	   int x1=(int) pa.map( (float) star1.getRA(),(float) minRA,(float) maxRA,pa.width-border,left+border);
    	   int y1=(int) pa.map( (float) star1.getDec(),(float) minDec,(float) maxDec,pa.height-border,top+border);
    	   int x2=(int) pa.map( (float) star2.getRA(),(float) minRA,(float) maxRA,pa.width-border,left+border);
    	   int y2=(int) pa.map( (float) star2.getDec(),(float) minDec,(float) maxDec,pa.height-border,top+border);
    	   pa.stroke(80);
    	   pa.line(x1, y1, x2, y2);
		   
	   }
	   
   }
   public static int map(int pos, int start,int finish, int begin, int end){
	   double exp=(0.0+end-begin)/(finish-start);
	   int pos1=pos-begin;
	   return (int) ( begin+pos1*exp);
   }
   public double[] getBorders(Star[] st){
	   double inf=1000000;
	   double minRA=inf,maxRA=-inf,minDec=inf,maxDec=-inf;
       for (int i=0;i<st.length;i++){
    	   minRA=Math.min(minRA,st[i].getRA());
    	   maxRA=Math.max(maxRA,st[i].getRA());
    	   minDec=Math.min(minDec,st[i].getDec());
    	   maxDec=Math.max(maxDec,st[i].getDec());
       }
       double[] arr={minRA,maxRA,minDec,maxDec};
	   return arr;
   }
   
}
