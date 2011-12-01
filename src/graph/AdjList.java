package graph;
import java.util.*;
import static graph.Graphs.*;

public class AdjList extends AbstractGraph implements Graph  {
	private Map<String,List<Nachbar>> internMap = new HashMap<String,List<Nachbar>>();
	private int zugriffe;
	
	
	
	/*Konstruktor
	 * erzeugt aus einer Liste von Strings einen Graphen. Jeder Eintrag in der Liste entspricht einer 
	 * Kante.
	 * "V1>V2:8" entspricht einer gerichteten Kante von V1 nach V2 mit der Gewichtung 8
	 * "V1-V2:8" entspricht einer ungerichteten Kante von V1 nach V2 mit Gewichtung 8
	 */
	private AdjList(String[] pairs, String einleseString){	
		this.einleseString = einleseString;
		for(String elem : pairs){			
			insert(getFirst(elem),getSecond(elem),gewichtung(elem));
			insertNode(getSecond(elem));
			if(trenner(elem).equals("!")){
				insert(getSecond(elem),getFirst(elem),gewichtung(elem));
			}			
		}
		zugriffe = 0;
	}
	
	public String toString(){
		StringBuffer s = new StringBuffer();
		for(Map.Entry<String, List<Nachbar>> entry : internMap.entrySet()){
			s.append(entry.getKey());
			s.append(" --> ");
			for(Nachbar elem : entry.getValue()){
				s.append(elem.name());
				s.append("(");
				s.append(String.valueOf(elem.weight()));
				s.append("); ");
			}
			s.append("\n");			
		}
		return s.toString();
	}
	
	/*
	 * fuegt einen Eintrag in den internMap ein
	 */
	public void insert(String first, String second, double weight){
		if(internMap.containsKey(first)){
			internMap.get(first).add(nachbar(second,weight));
		}
		else{
			List<Nachbar> l = new ArrayList<Nachbar>();
			l.add(nachbar(second, weight));
			internMap.put(first, l);
		}
	}
	
	/*
	 * fuegt eine einzelne Ecke in den Graphen ein
	 */
	private void insertNode(String name){
		if(!(internMap.containsKey(name))){
			List<Nachbar> l = new ArrayList<Nachbar>();
			internMap.put(name, l);
		}
	}
	
	public void changeCapacity(String from, String to, double capacity){
		if(internMap.containsKey(from)){
			if(internMap.get(from).contains(to)){
				List<Nachbar> n = internMap.get(from);
				for(Nachbar elem : n){
					zugriffe++;
					if(elem.name().equals(to)){
						elem.setWeight(capacity);
						break; // zur optimierung
					}
					
				}
			}else{
				internMap.get(from).add(Graphs.nachbar(to, capacity));
			}
				
		}
		
	}
	
	
	public static Graph valueOf(String s){
		String[] pairs = s.split(";");
		for(String elem : pairs){
			if(!(checkElem(elem))) return Graphs.nag;
		}
		return new AdjList(pairs,s);
	}
	
	/*
	 * gibt alle Nachbarn einer Ecke in einer Liste aus
	 */
	private List<Nachbar> neighborsInt(String ecke){
		zugriffe++;
		if(!(internMap.containsKey(ecke))) return null;
		zugriffe++;
		return internMap.get(ecke);
	}
	
	/*
	 * gibt eine Defensivkopie der Liste aller Nachbarn einer Ecke aus
	 */
	public List<Nachbar> neighbors(String ecke){
		return new ArrayList<Nachbar>(this.neighborsInt(ecke));
	}
	
	/*
	 * (non-Javadoc)
	 * @see graph.Graph#allNodes()
	 * gibt ein Set mit allen Ecken aus
	 */
	public Set<String> allNodes(){
		zugriffe++;
		return internMap.keySet();
	}
	
	public int noOfNodes(){
		zugriffe++;
		return internMap.size();
	}
	
	public static void main(String args[]){
		Graph g1 = AdjList.valueOf("v1-v2:8");
		System.out.println(g1.bfs("v1"));
	}
	
	public int getZugriffe(){
		return this.zugriffe;
	}
	
	public void setZugriffeNull(){
		zugriffe = 0;
	}

	@Override
	public String dijkstra(String start, String end) {
		return super.dijkstra(start, end);
	}

	@Override
	public String dijkstraFiFo(String start, String end) {
		return super.dijkstraFiFo(start, end);
	}
	
	private Graph toAdjMatrix(){
		return Graphs.adjMatrix(einleseString);
	}

	public Graph[] floydWarshall() {
		return this.toAdjMatrix().floydWarshall();
	}
	
	public void deleteZeroEdges(){
		for(List<Nachbar> elem : internMap.values()){
			zugriffe++;
			for(Nachbar e : elem){
				zugriffe++;
				if(e.weight() == 0.0){
					elem.remove(e);
				}
			}
		}
	}
	
	public Double weightBetween(String node1, String node2){
		if(!((this.allNodes().contains(node1)) && (this.allNodes().contains(node2)))){
			return Double.NaN;
		}
		
		for(Nachbar elem : internMap.get(node1)){
			if(elem.name().equals(node2)){
				return elem.weight();
			}
		}
		return Double.POSITIVE_INFINITY;
	}

	
	
	
}
