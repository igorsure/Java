package basicstar;

import processing.core.PApplet;
import processing.core.PGraphics;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;



public class MainAppPolygon extends PApplet {
	 PGraphics pg;
	 int panelWidth=250;
	 
	 public void setup() {
			size(this.displayWidth-20, this.displayHeight-100);
			//background(0);
			pg = createGraphics(700, 500);
			//Solution sol=new Solution();
	}
	 public void draw() {
		  background(102);
		  
		  pushMatrix();
		  //ranslate((float) (width*0.2), (float) (height*0.5));
		  //rotate((float) (frameCount / 200.0));
		  //polygon(0, 0, 82, 3);  // Triangle
		  popMatrix();
		  
		  pushMatrix();
		  translate(width*0.5, height*0.5);
		  //rotate((float) (frameCount / 50.0));
		  //polygon(0, 0, 80, 20);  // Icosahedron
		  popMatrix();
		  
		  pushMatrix();
		  translate(width*0.8, height*0.5);
		  //rotate((float) (frameCount / -100.0));
		  //polygon(0, 0, 70, 7);  // Heptagon
		  popMatrix();
		  float[] x1 ={0,70 , 70,0};
		  float[] y1={0,0 ,70 ,70 };
		  pushMatrix();
		  translate(width*0.8, height*0.5);
		  polygon1(x1,y1);
		  popMatrix();
		  float[] x2 ={0,0 ,90};
		  float[] y2={0,90 ,90  };
		  pushMatrix();
		  translate(width*0.8, height*0.5);
		  polygon1(x2,y2);
		  popMatrix();
		}

		private void translate(double d, double e) {
			translate((float) d,(float) e);
		}
		// TODO Auto-generated method stub
		
	
		void polygon(float x, float y, float radius, int npoints) {
		  float angle = TWO_PI / npoints;
		  beginShape();
		  for (float a = 0; a < TWO_PI; a += angle) {
		    float sx = x + cos(a) * 2*radius;
		    float sy = y + sin(a) * radius;
		    vertex(sx, sy);
		  }
		  endShape(CLOSE);
		}
		void polygon1(float[] x, float[] y) {
			  //loat angle = TWO_PI / npoints;
			  beginShape();
			  for (int i=0;i<x.length;i++) {
			    vertex(x[i], y[i]);
			  }
			  endShape(CLOSE);
			}
	 
}
