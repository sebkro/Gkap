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

	public Pair<List<String>, Double> dijkstra(String start, String end) {
		System.out.println("NAG: Kein Weg!");
		return new Pair<List<String>, Double>();
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
	
	public double weightBetween(String start, String end) {
		return Double.NaN;
	}
	
	public Pair<Graph, Double> residualGraph(String quelle, String senke) {
		Pair<Graph, Double> result = new Pair<Graph, Double>();
		result.setFirst(Graphs.nag);
		result.setSecond(Double.NaN);
		return result;
	}
	public List<Pair<String, Double>> edgesReverse(String eckenname)
			throws IllegalArgumentException {
		return null;
	}
	public void allEdgesZero() {
		
	}
	@Override
	public Pair<Graph,Double> fordFulkerson(String quelle, String senke) {
		return Graphs.createPair(Graphs.nag,-1.0);
	}
	@Override
	public void setZugriffe(int i) {
		// TODO Auto-generated method stub
		
	}
	
}
