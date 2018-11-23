public class Piece  {
	int type,color,x,y,value;
	private final String yCoord="ABCDEFGH";
	private final String white=" PNBRQK";
	private final String black=" pnbrqk";
	//                                      1       2       3        4      5      6
	String[] pieceName =new String[] {"","Pawn","Knight","Bishop","Rook","Queen","King"};
	String[] colorName=new String[] {"White","Black"};
	int[][] rookDir=new int[][] {{-1,0},{1,0},{0,-1},{0,1}};
	int[][] bishopDir=new int[][] {{-1,-1},{-1,1},{-1,1},{1,-1}} ;
	int[][] knightDir=new int[][] {{-1,-2},{-1,2},{-1,2},{1,-2},{-2,-1},{-2,1},{-2,1},{2,-1}} ;
	String name,shortName;
	
	int[][] dir;
	boolean isKing() {
		return type==6;
	}
	boolean isQueen() {
		return type==5;
	}
	boolean isRook() {
		return type==4;
	}
	boolean isBishop() {
		return type==3;
	}
	boolean isKnight() {
		return type==2;
	}
	boolean isPawn() {
		return type==1;
	}
	Piece(int Value, int X,int Y){
		value=Value;
		type=Math.abs(Value);
		color=Value>0?1:-1;
		System.out.println("type:"+type);
		name=pieceName[type];
		shortName=name.substring(0,1);
		//colorName=colorN
		if (color==1) {
			shortName=""+white.charAt(type);
		} else {
			shortName=""+black.charAt(type);
		}
		
		initDir();
	}
	private void initDir() {
		if (type>=5) {
			dir=concatArrays(bishopDir,rookDir);
		}
		if (type==4) { //Rook
			dir=concatArrays(rookDir,new int[][] {});
		}
		if (type==3) { //Bishop
			dir=concatArrays(bishopDir,new int[][] {});
		}
		if (type==2) { //Knight
			dir=concatArrays(knightDir,new int[][] {});
		}
	}
	int[][] concatArrays(int[][] arr1,int[][] arr2){
		int len1=arr1.length;
		int len2=arr2.length;
		int[][] res=new int[len1+len2][2];
		for(int i=0;i<len1;i++) {
			res[i][0]=arr1[i][0];
			res[i][1]=arr1[i][1];
		}
		for(int i=0;i<len2;i++) {
			res[i+len1][0]=arr2[i][0];
			res[i+len1][1]=arr2[i][1];
		}
		return res;
	}
    @Override
    public boolean equals(Object obj)
    {
         
    // checking if both the object references are 
    // referring to the same object.
    if(this == obj)
            return true;
         
        if(obj == null || obj.getClass()!= this.getClass())
            return false;
         
        // type casting of the argument. 
        Piece field = (Piece) obj;
         
        // comparing the state of argument with 
        // the state of 'this' Object.
        return (field.x== this.x && field.y==this.y);
    }
     
    @Override
    public int hashCode()
    {
         
        // We are returning the Geek_id 
        // as a hashcode value.
        // we can also return some 
        // other calculated value or may
        // be memory address of the 
        // Object on which it is invoked. 
        // it depends on how you implement 
        // hashCode() method.
        return 31*this.x+this.y;
    }
	
	
    public String toString() {
    	return shortName+yCoord.charAt(y)+(8-x);
    }
	
}
