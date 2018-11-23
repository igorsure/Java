import java.util.HashMap;
import java.util.HashSet;

public class Stringer {
	public static int countStringer=0;
	static String alphabet="abcdefghijklmnopqrtuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ012345";
	StringBuilder s;
	long amount;
	long value;
	Stringer(StringBuilder sb){
		s=sb;
		amount=1;
		value=0;
		
	}
	Stringer(Stringer sb){
		s=sb.s;
		amount=sb.amount;
		value=sb.value;
		
	}
	Stringer addChar(char ch) {
		Stringer str=new Stringer(this);
		str.s.append(ch);
		return str;
	}
	void addStringer(Stringer str) {
		amount+=str.amount;
		
	}
    int calcFunc() {
    	HashSet<Integer> set=new HashSet<Integer>();
    	int len=s.length();
    	for(int i=1;i<len;i++) {
    		if (s.charAt(i)==s.charAt(0)) {
    			set.add(i);
    		}
    	}
    	if (set.isEmpty()) return 0;
    	int curInd=1;
    	while(curInd<len) {
    		HashSet<Integer> set1=new HashSet<Integer>();
    		for(int ind:set) {
    			if (ind==len-1) continue;
    			if (s.charAt(ind+1)==s.charAt(curInd)) {
    				set1.add(ind+1);
    			}
    		}
    		if (set1.isEmpty()) return curInd;
    		set=set1;
    		curInd++;
    		
    	}
    	return len-1;
    }
	
	void normalize() {
		
		HashMap<Character,Character> map=new HashMap<Character,Character>();
		int ind=0;
		char ch='a';
		StringBuilder sNext=new StringBuilder(s.length());
		for(int i=0;i<s.length();i++) {
			char nextChar=s.charAt(i);
			if (!map.containsKey(nextChar)) {
				ch=alphabet.charAt(ind++);
				map.put(nextChar,ch);
			}
			sNext.append(map.get(nextChar));
		}
		s=sNext;
		
	}
    @Override
    public boolean equals(Object obj)
    {
         
	    // checking if both the object references are 
	    // referring to the same object.
	    if(this == obj)
	            return true;
         
        // it checks if the argument is of the 
        // type Geek by comparing the classes 
        // of the passed argument and this object.
        // if(!(obj instanceof Geek)) return false; ---> avoid.
        if(obj == null || obj.getClass()!= this.getClass())
            return false;
         
        // type casting of the argument. 
        Stringer str=(Stringer) obj;
        return this.s==str.s;
    }
    public String toString() {
    	return s+":"+this.calcFunc();
    }
    @Override
    public int hashCode()
    {
         
        return countStringer++;
    }	
    public static  void test(String s) {
		Stringer str=new Stringer(new StringBuilder(s));
		System.out.println(str);
		str.normalize();
		System.out.println(str);
		System.out.println("");
    	
    }
	public static void main(String[] args) {
		test("abcdlkjabc");
		test("dhyabcdlkdhjabc");
	}

}
