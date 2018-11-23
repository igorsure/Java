package basicstar;

import processing.core.PApplet;

/*
 * ftp://cdsarc.u-strasbg.fr/cats/VI/49/ - constellations
 http://www.astronexus.com/hyg
 Version 3: The field content is very nearly the same as in Version 2, but the column headers are somewhat different, and a few extra fields (for variable star range and multiple star info) have been added to the end of each record. For a full list of the updated column names, see the official database documentation on Github.

Fields labeled with "*" exist only in version 2.0 or higher. Also, since I used a larger set of data for this version, the StarID differs from versions 1.*

    StarID: The database primary key from a larger "master database" of stars.
    HD: The star's ID in the Henry Draper catalog, if known.
    HR: The star's ID in the Harvard Revised catalog, which is the same as its number in the Yale Bright Star Catalog.
    Gliese: The star's ID in the third edition of the Gliese Catalog of Nearby Stars.
    BayerFlamsteed: The Bayer / Flamsteed
    designation, from the Fifth Edition of the Yale Bright Star Catalog. This is a combination of the two designations. The Flamsteed number, if present, is given first; then a three-letter abbreviation for the Bayer Greek letter; the Bayer superscript number, if present; and finally, the three-letter constellation abbreviation. Thus Alpha Andromedae has the field value "21Alp And", and Kappa1 Sculptoris (no Flamsteed number) has "Kap1Scl".
    RA, Dec: The star's right ascension and declination, for epoch 2000.0. Stars present only in the Gliese Catalog, which uses 1950.0 coordinates, have had these coordinates precessed to 2000.
    ProperName: A common name for the star, such as "Barnard's Star" or "Sirius". I have taken these names primarily from the Hipparcos project's web site, which lists representative names for the 150 brightest stars and many of the 150 closest stars. I have added a few names to this list. Most of the additions are designations from catalogs mostly now forgotten (e.g., Lalande, Groombridge, and Gould ["G."]) except for certain nearby stars which are still best known by these designations.
    Distance: The star's distance in parsecs, the most common unit in astrometry. To convert parsecs to light years, multiply by 3.262. A value of 10000000 indicates missing or dubious (e.g., negative) parallax data in Hipparcos.
    Mag: The star's apparent visual magnitude.
    AbsMag: The star's absolute visual magnitude (its apparent magnitude from a distance of 10 parsecs).
    Spectrum: The star's spectral type, if known.
    ColorIndex: The star's color index (blue magnitude - visual magnitude), where known.
    * X,Y,Z: The Cartesian coordinates of the star, in a system based on the equatorial coordinates as seen from Earth. +X is in the direction of the vernal equinox (at epoch 2000), +Z towards the north celestial pole, and +Y in the direction of R.A. 6 hours, declination 0 degrees.
    * VX,VY,VZ: The Cartesian velocity components of the star, in the same coordinate system described immediately above. They are determined from the proper motion and the radial velocity (when known). The velocity unit is parsecs per year; these are small values (around 10-5 to 10-6), but they enormously simplify calculations using parsecs as base units for celestial mapping.
 
  */
public class Star {
	private int id;
	private int hip;
	private int hd;
	private int hr;
	private String gl,bf,name;
	private double ra,dec,dist,pmra,pmdec,rv,mag,absmag;
	private String spect;
	private double ci,x,y,z,vx,vy,vz;
	private double r,g,b,color;
	/* 6fields */
	private String constellation;
	
	//constructor for constellation lines
	public Star(String lightStar){
		String cvsSplitBy = ",";
		String[] ss=lightStar.split(cvsSplitBy);
		constellation=ss[0];
		if (ss.length<3){
			name="*empty*";
			ra=0;
			dec=0;
			mag=0;
			return;
		}
		name=ss[1];
		ra=getDouble(ss[2]);
		dec=getDouble(ss[3]);
		mag=getDouble(ss[4]);
	}
	public Star(String[] star){

		id=getInteger(star[0]);
		hip=getInteger(star[1]);
		hd=getInteger(star[2]);
		hr=getInteger(star[3]);
		gl=star[4];
		bf=star[5];
		name=star[6];
		ra=getDouble(star[7]);
		dec=getDouble(star[8]);
		dist=getDouble(star[9]);
		pmra=getDouble(star[10]);
		pmdec=getDouble(star[11]);
		rv=getDouble(star[12]);
		mag=getDouble(star[13]);
		absmag=getDouble(star[14]);
		spect=star[15];
		ci=getDouble(star[16]);
		bv2rgb(ci);
		x=getDouble(star[17]);
		y=getDouble(star[18]);
		z=getDouble(star[19]);
		vx=getDouble(star[20]);
		vy=getDouble(star[21]);
		vz=getDouble(star[22]);
		constellation=star[29];
		
	}
	@Override public String toString() {
		String s="";
		if (!name.isEmpty())
			s=""+name+"";
		else
			s="hip"+Integer.toString(hip);
		s=s+" ("+constellation+") mag:"+Double.toString(mag);	
	    return s;
	  }	

	private Integer getInteger(String str) {
	    if (str == null || str.length()==0 ) {
	        return new Integer(0);
	    } else  {
	        return Integer.parseInt(str);
	    }
	}	
	private Double getDouble(String str) {
	    if (str == null || str.length()==0 ) {
	        return new Double(0);
	    } else  {
	        return Double.parseDouble(str);
	    }
	}	
	public String  getConstellation(){
		return constellation;
	}
	public String  getName(){
		return name;
	}
	public boolean named(){
		return !name.isEmpty();
	}
	public double getMagnitude(){
		return mag;
	}
	public int getDrawMagnitude(){
		double secondMag=10.0;
		return (int) (-4*Math.log(mag/secondMag));
	}
	public double getRA(){
		return ra;
	}
	public void setRA(double myra){
		ra=myra;
	}
	public double getDec(){
		return dec;
	}
	public void draw(PApplet pa,int x,int y){
 	   int drawMag=this.getDrawMagnitude();
       pa.textSize(15);
 	   //int[] clr=bv2rgb(this.ci);
 	   pa.fill((int) r,(int)g,(int)b);
       pa.ellipse(x,y, drawMag, drawMag);
       pa.text(name, x+10,y);

	}
	public void bv2rgb(double bv)    // RGB <0,1> <- BV <-0.4,+2.0> [-]
    {
	//double r,g,b;	
    double t;  r=0.0; g=0.0; b=0.0; if (bv<-0.4) bv=-0.4; if (bv> 2.0) bv= 2.0;
         if ((bv>=-0.40)&&(bv<0.00)) { t=(bv+0.40)/(0.00+0.40); r=0.61+(0.11*t)+(0.1*t*t); }
    else if ((bv>= 0.00)&&(bv<0.40)) { t=(bv-0.00)/(0.40-0.00); r=0.83+(0.17*t)          ; }
    else if ((bv>= 0.40)&&(bv<2.10)) { t=(bv-0.40)/(2.10-0.40); r=1.00                   ; }
         if ((bv>=-0.40)&&(bv<0.00)) { t=(bv+0.40)/(0.00+0.40); g=0.70+(0.07*t)+(0.1*t*t); }
    else if ((bv>= 0.00)&&(bv<0.40)) { t=(bv-0.00)/(0.40-0.00); g=0.87+(0.11*t)          ; }
    else if ((bv>= 0.40)&&(bv<1.60)) { t=(bv-0.40)/(1.60-0.40); g=0.98-(0.16*t)          ; }
    else if ((bv>= 1.60)&&(bv<2.00)) { t=(bv-1.60)/(2.00-1.60); g=0.82         -(0.5*t*t); }
         if ((bv>=-0.40)&&(bv<0.40)) { t=(bv+0.40)/(0.40+0.40); b=1.00                   ; }
    else if ((bv>= 0.40)&&(bv<1.50)) { t=(bv-0.40)/(1.50-0.40); b=1.00-(0.47*t)+(0.1*t*t); }
    else if ((bv>= 1.50)&&(bv<1.94)) { t=(bv-1.50)/(1.94-1.50); b=0.63         -(0.6*t*t); }
         r*=256;
         g*=256;
         b*=256;
         color=r*256*256+g*256+b;
    }


}
