package basicstar;

import processing.core.PApplet;
import processing.core.PGraphics;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;



/**
 * This example uses a local MBTiles file. Thus, it does not need an Internet connection to load tiles.
 */
public class MainApp extends PApplet {
	StarList st;
	Star[] cnst;
	ConstellationList constList=new ConstellationList();
	int panelWidth=250;
    PGraphics pg;
    int lastMouseX=-1;
    int lastMouseY=-1;
    public ConstellationList getConstellationList(){
    	return constList;
    }
	public void setup() {
		size(this.displayWidth-20, this.displayHeight-100);
		background(0);
		pg = createGraphics(700, 500);
		st=new StarList();
		st.loadStars(constList);
		cnst=st.getConstellation("Ori",false,1);
		//st.dump(cnst);
		

	}
	public int getPanelWidth(){
		return panelWidth;
	}
	public void draw() {
		fill(255);
		noStroke();
		st.draw(this, cnst);
   	    fill(255);
		rect(0,0, panelWidth, height);
		drawConstellationLabels();
	}
	public void mousePressed() {
		lastMouseX=mouseX;
		lastMouseY=mouseY;
		}
   public void drawConstellationLabels(){
	   int row=0;
	   int colSize=4;
	   int col=0;
	   int rowHeight=20;
	   int colWidth=50;
	   int i=0;
	   for (String constel:constList.getConstellations())	{   
		   int left=15+colWidth*col++;
		   int top=25+row*rowHeight;
		   int rectLeft=left-5;
		   int rectTop=top-rowHeight+5;
		   int rectWidth=colWidth-10;
		   int rectHeight=rowHeight;
		   if (between(lastMouseX,rectLeft,rectLeft+rectWidth) && between(lastMouseY,rectTop,rectTop+rectHeight)) {
			   st.setCurrentConstellation(constel);
			   cnst=st.getConstellation(constel,false,1);
			   Constellation myConst= constList.getConstellation(constel);
		   }
		   if (constel.equals(st.getCurrentConstellation())) {
			   fill(180);
			   rect(rectLeft, rectTop, rectWidth,rectHeight);
		   }
		   fill(0);
		   text(constel, left,top);
		   if (col>colSize) {
			   col=0;
			   row++;
		   }
		   i++;
	   }
	   
   }
   public void redraw(){
	   fill(0);
	   rect(0,0,this.displayWidth,displayHeight);
   }
   public boolean between(int key,int x,int y){
	   return (key>=x && key<=y) ;
   }
	   
	
 }

