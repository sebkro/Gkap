package graph;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class NAG implements Graph{
	private NAG(){}
	public static Graph valueOf(){
		return new NAG();
	}

	public int noOfNodes() {
		return -1;
	}

	public List<Nachbar> neighbors(String ecke) {
		return null;
	}
	
	public String toString(){
		return "NAG";
	}

	public Set<String> allNodes() {
		return null;
	}

	public Map<String, Integer> bfs(String ecke) {
		return null;
	}

	public void setZugriffeNull() {
		
		
	}

	public int getZugriffe() {
		
		return 0;
	}

	public  dijkstra(String start, String end) {
		return "NAG: Kein Weg!";
	}
	
	public String dijkstraFiFo(String strat, String end){
		return "NAG: Kein Weg!";
	}

	public Graph[] floydWarshall() {
		Graph[] x = {Graphs.nag, Graphs.nag};
		return x;
	}
	
	public void deleteZeroEdges(){
		
	}
	public void insert(String s, String s2, double d) {
		// TODO Auto-generated method stub
		
	}
	public void changeCapacity(String s, String s2, double d) {
		// TODO Auto-generated method stub
		
	}
	
}
