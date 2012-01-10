package graph;


public class Graphs {
	
	public static Graph nag = NAG.valueOf();
	
	public static String getFirst(String s){
		int trenner = indexTrenner(s);
		String result;
		if(trenner == -1) return "Error";
		
		try{
			result = s.substring(0,trenner);
			if(result.contains(":")) return "Error";
		}catch(Exception e){
			result = "Error";
		}
		return result;
		
	}
	
	public static Nachbar nachbar(String s, double weight){
		return NachbarImpl.valueOf(s, weight);
	}
	
	
	public static <X,Y> Pair<X,Y> createPair(X x, Y y){
		Pair<X,Y> p = new Pair<X,Y>();
		p.setFirst(x);
		p.setSecond(y);
		return p;
	}
	public static Graph adjMatrix(String s){
		return AdjMatrix.valueOf(s);
	}
	
	public static Graph adjList(String s){
		return AdjList.valueOf(s);
	}
	
	public static String getSecond(String s){
		int trenner = indexTrenner(s);
		String result;
		if(trenner == -1) return "Error";
		try{
			result = s.substring(trenner + 1,s.indexOf(":"));
		}
		catch(Exception e){
			result = "Error";
		}
		return result;
	}
	
	public static String trenner(String s){
		int index = indexTrenner(s);
		if(index == -1) return "Error";
		return String.valueOf(s.charAt(index));
	}
	
	public static double gewichtung(String s){
		String result;	
		
		try{
			result = s.substring(s.indexOf(":") +1, s.length());
		}catch(Exception e){
			return Double.NaN;
		}
		
		try{
			return Double.valueOf(result);
		}catch(Exception e){
			return Double.NaN;
		}
		
		
	}
	
	public static Edge createEdge(String node1, String node2, double weight){
		return EdgeImpl.create(node1, node2, weight);
	}
	
	public static int indexTrenner(String s){
		int indTrenner = s.indexOf("!");
		if(indTrenner == -1){
			indTrenner = s.indexOf(">");
		}
		return indTrenner;
	}
	
	public static void main(String[] args){
		String s = "v1>v2:3;v1>v3:8";
		Graph g = AdjMatrix.valueOf(s);
		System.out.println(g.neighbors("v1"));
	}

}
