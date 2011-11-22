package graph;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class NAG implements Graph{
	private NAG(){}
	public static Graph valueOf(){
		return new NAG();
	}
	@Override
	public int noOfNodes() {
		return -1;
	}
	@Override
	public List<Nachbar> neighbors(String ecke) {
		return null;
	}
	
	public String toString(){
		return "NAG";
	}
	@Override
	public Set<String> allNodes() {
		return null;
	}
	@Override
	public Map<String, Integer> bfs(String ecke) {
		return null;
	}
	@Override
	public void setZugriffeNull() {
		
		
	}
	@Override
	public int getZugriffe() {
		
		return 0;
	}
	@Override
	public String dijkstra(String start, String end) {
		return "NAG: Kein Weg!";
	}
	
	public String dijkstraFiFo(String strat, String end){
		return "NAG: Kein Weg!";
	}
	@Override
	public Graph[] floydWarshall() {
		Graph[] x = {Graphs.nag, Graphs.nag};
		return x;
	}
	
}
