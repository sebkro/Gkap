package graph;
import java.util.*;

public interface Graph {
	public int noOfNodes();
	public List<Nachbar> neighbors(String ecke);
	public Set<String> allNodes();
	public Map<String,Integer> bfs(String ecke);
	public void setZugriffeNull();
	public int getZugriffe();
	public Map dijkstra(String start, String end);
	public String dijkstraFiFo(String start, String end);
	public Graph[] floydWarshall();
	

	public abstract void insert(String s, String s2, double d);
	public abstract void changeCapacity(String s, String s2, double d);
	public abstract void deleteZeroEdges();
	

}
