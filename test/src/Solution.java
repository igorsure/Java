import java.util.*;
// CLASS BEGINS, THIS CLASS IS REQUIRED
class Point implements Comparable<Point>{
    int x,y;
    double length;>
    Point(List<Integer> lst) {
            coords=lst;
            x=lst.get(0);
            y=lst.get(1);
            length=this.getLength();
    }
    double getLength(){
        return Math.sqrt(this.x*this.x+this.y*this.y);
    }
    List<Integer> getCoords(){
        return coords;
    }
    @Override
    public int compareTo(Point point) {
        return (int)(this.length - point.length);
    }        
    
}
public class Solution
{
    ArrayList<Point> pnts,res;
    // METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
    List<List<Integer>> ClosestXdestinations(int numDestinations, 
                                             List<List<Integer>> allLocations,
                                             int numDeliveries)
	{
        pnts=new ArrayList<Point>();
        for(List<Integer> coord: allLocations){
            pnts.add(new Point(coord));
            
        }
        Collections.sort(pnts);
        res=new ArrayList<Integer>();
        for(int i=0;i<numDeliveries;i++){
            res.add(pnts.get(i))
        }
        return res;
    }
    // METHOD SIGNATURE ENDS
}

