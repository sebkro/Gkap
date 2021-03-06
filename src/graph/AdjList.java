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
		boolean changed = false;
		if(internMap.containsKey(from)){
				for(Nachbar elem : internMap.get(from)){
					zugriffe++;
					if(elem.name().equals(to)){
						elem.setWeight(capacity);
						changed = true;
						break; // zur optimierung
					}		
				}
			if(!changed){
				zugriffe++;
				internMap.get(from).add(Graphs.nachbar(to,capacity));
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
	public Pair<List<String>, Double> dijkstra(String start, String end) {
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
		for(Map.Entry<String, List<Nachbar>> entry : internMap.entrySet()){
			List<Nachbar> result = new ArrayList<Nachbar>();
			for(Nachbar n : entry.getValue()){
				zugriffe++;
				if(n.weight() != 0.0){
					result.add(n);
				}
			}
			internMap.put(entry.getKey(), result);
		}
	}
	
	public double weightBetween(String node1, String node2){
		if(!((this.allNodes().contains(node1)) && (this.allNodes().contains(node2)))){
			return Double.NaN;
		}
		
		for(Nachbar elem : internMap.get(node1)){
			zugriffe++;
			if(elem.name().equals(node2)){
				return elem.weight();
			}
		}
		return Double.POSITIVE_INFINITY;
	}

	public List<Pair<String,Double>> edgesReverse(String eckenname) throws IllegalArgumentException {
		if(!allNodes().contains(eckenname)) throw new IllegalArgumentException();
		
		List<Pair<String,Double>> result = new ArrayList<Pair<String,Double>>();
		
		for(Map.Entry<String, List<Nachbar>> entry : internMap.entrySet()){
			for(Nachbar elem : entry.getValue()){
				zugriffe++;
				if(elem.name().equals(eckenname)){
					Pair<String,Double> p = new Pair<String,Double>();
					p.setFirst(entry.getKey());
					p.setSecond(elem.weight());
					result.add(p);
				}
			}
		}
		return result;
	}

	public void allEdgesZero() {
		for(List<Nachbar> elem : internMap.values()){
			for(Nachbar e : elem){
				zugriffe++;
				e.setWeight(0.0);
			}
		}
	}

	
	public void setZugriffe(int i) {
		this.zugriffe = i;
	}
	
	public int eingangsgrad(String ecke){
		int result = 0;
		for(String node: this.allNodes()){
			for(Nachbar elem : this.neighbors(node)){
				if(elem.name().equals(ecke)){
					result ++;
				}
			}
		}
		return result;
	}
	
	public void deleteEdge(String start, String end){
		if(this.allNodes().contains(start)){
			if(this.isNeighbor(end, start)){
				List<Nachbar> l = this.internMap.get(start);
				if(l.size() > 0){
					int index = 0;
					for(int i = 0; i < l.size(); i++){
						if(l.get(i).name().equals(end)){
							index = i;
							break;
						}
					}
					l.remove(index);
					
				}
			}
		}
	}
	
	private boolean isNeighbor(String node1, String node2){
		List<Nachbar> l = this.neighbors(node2);
		for(Nachbar elem : l){
			if(elem.name().equals(node1)){
				return true;
			}
		}
		return false;
	}
	
	public int ausgangsgrad(String ecke){
		return this.neighbors(ecke).size();
	}
	
	public List<Edge> allEdges(){
		List<Edge> result = new ArrayList<Edge>();
		for(Map.Entry<String, List<Nachbar>> entry : internMap.entrySet()){
			for(Nachbar elem : entry.getValue()){
				Edge e = Graphs.createEdge(entry.getKey(), elem.name(), elem.weight());
				if(!(result.contains(e))){
					result.add(e);
				}
			}
		}
		return result;
	}
	
	public Graph doubleAllEdges(){
		Graph result = Graphs.adjList(einleseString);
		for(Map.Entry<String, List<Nachbar>> e : this.internMap.entrySet()){
			for(Nachbar n : e.getValue()){
				result.insert(e.getKey(), n.name(), n.weight());
			}
		}
		return result;
	}
	
	public String einleseString(){
		StringBuffer result = new StringBuffer();
		for(Map.Entry<String, List<Nachbar>> entry : this.internMap.entrySet()){
			for(Nachbar n : entry.getValue()){
				String s = entry.getKey() + ">" + n.name() + ":" + n.weight() + ";";
				result.append(s);
			}
		}
		result.deleteCharAt(result.length() -1);
		return result.toString();
	}

	
}
