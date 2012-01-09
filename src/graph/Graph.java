package graph;
import java.util.*;

public interface Graph {
	public int noOfNodes();
	public List<Nachbar> neighbors(String ecke);
	public Set<String> allNodes();
	public Map<String,Integer> bfs(String ecke);
	public void setZugriffeNull();
	public int getZugriffe();
	public Pair<List<String>, Double> dijkstra(String start, String end);
	public String dijkstraFiFo(String start, String end);
	public Graph[] floydWarshall();
	public double weightBetween(String start, String end);
	public Pair<Graph, Double> residualGraph(String quelle, String senke);
	public List<Pair<String,Double>> edgesReverse(String eckenname) throws IllegalArgumentException;

	public abstract void insert(String s, String s2, double d);
	public abstract void changeCapacity(String s, String s2, double d);
	public abstract void deleteZeroEdges();
	public abstract void allEdgesZero();
	public Pair<Graph,Double> fordFulkerson(String quelle, String senke);
	public void setZugriffe(int i);
	public String fleury(String start);
	public int eingangsgrad(String ecke);
	public int ausgangsgrad(String ecke);
	public void deleteEdge(String start, String end);
	public boolean isEmpty();
	public boolean istSchnittkante(String s,String s2);
	

}
