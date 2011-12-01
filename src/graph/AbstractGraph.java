package graph;
import static graph.Graphs.getFirst;
import static graph.Graphs.getSecond;
import static graph.Graphs.gewichtung;

import java.util.*;

public abstract class AbstractGraph implements Graph {
	
	protected String einleseString;
	
	protected static boolean checkElem(String s) {
		
		if(getFirst(s).equals("Error")) return false;
		if(getSecond(s).equals("Error")) return false;
		if(Double.isNaN(gewichtung(s))) return false;
		if(Graphs.trenner(s).equals("Error")) return false;
		return true;
	}


	public Map<String,Integer> bfs(String start){
		if(!(this.allNodes().contains(start))) return null;
		
		Map<String,Integer> result = new HashMap<String,Integer>();
		Queue<String> q = new LinkedList<String>();
		
		
		result.put(start,0);
		q.add(start);
		while(!(q.isEmpty())){
			List<Nachbar> l = this.neighbors(q.peek());
			for(Nachbar element : l){
				if(!(result.containsKey(element.name()))){
					result.put(element.name(),result.get(q.peek()) + 1);
					q.add(element.name());
				}
			}
			q.remove();
		}
		
		return result;		
		
	}
	
	public Pair<List<String>, Double> dijkstra(String start, String end){
		
		List<String> resultList = new ArrayList<String>();
		Pair<List<String>, Double> result = new Pair<List<String>, Double>();
		
		if(!(this.allNodes().contains(start))){
			System.out.println(start + " nicht im Graphen!");
			return result;
		}
		if(!(this.allNodes().contains(end))){
			System.out.println(end + " nicht im Graphen!");
			return result;
		}
		
		
		Map<String,Double> minEntf = new HashMap<String,Double>();
		Map<String, String> vorgaenger = new HashMap<String, String>();
		Map<String,Double> marked = new HashMap<String,Double>();
		
		
		//fuellen der maxEntf --> init
		
		for(String elem : this.allNodes()){
			minEntf.put(elem, Double.POSITIVE_INFINITY);
		}
		minEntf.put(start, 0.0);
		
		//fuellen der vorgaengerMap --> init
		
		vorgaenger.put(start, start);
		
		//eigentlicher algorithmus
		
		while(!(marked.containsKey(end)) || !(marked.keySet().equals(allNodes()))){
			String aktuell = dNextNode(minEntf);
			if(aktuell.equals("Error")){
				System.out.println("kein Weg");
				return result;
			}
			
			for(Nachbar elem : this.neighbors(aktuell)){
				if(!(marked.containsKey(elem.name()))){
					if(elem.weight() + minEntf.get(aktuell) < minEntf.get(elem.name())){
						minEntf.put(elem.name(), elem.weight() + minEntf.get(aktuell));
						vorgaenger.put(elem.name(), aktuell);
					}
				}
			}
			marked.put(aktuell, minEntf.get(aktuell));
			minEntf.remove(aktuell);
		}
		
		/*
		 * Berechnung fertig, es folgt die Generierung des AusgabeStrings
		 */
		
		StringBuffer weg = new StringBuffer();
		String aktuell = end;
		weg.append(" " + aktuell);
		resultList.add(aktuell);
		while(!(aktuell.equals(start))){
			weg.append(", ");
			weg.append(vorgaenger.get(aktuell));
			resultList.add(aktuell);
			aktuell = vorgaenger.get(aktuell);
		}
		weg.reverse();
		weg.append("Entfernung: ");
		weg.append(marked.get(end));
		System.out.println(weg.toString());
		resultList = reverse(resultList);
		result.setFirst(resultList);
		result.setSecond(marked.get(end));
		return result;
	}
	
	
	public String dijkstraFiFo(String start, String end){
		if(!(this.allNodes().contains(start))) return start + " nicht im Graphen!";
		if(!(this.allNodes().contains(end))) return end + " nicht im Graphen!";
		
		Map<String,Double> minEntf = new HashMap<String,Double>();
		Map<String, String> vorgaenger = new HashMap<String, String>();
		Queue<String> marked = new LinkedList<String>();
		
		
		//fuellen der maxEntf --> init
			
		for(String elem : this.allNodes()){
			minEntf.put(elem, Double.POSITIVE_INFINITY);
		}
		minEntf.put(start, 0.0);
			
		//fuellen der vorgaengerMap --> init
			
		vorgaenger.put(start, start);
			
		//eigentlicher algorithmus
		marked.add(start);
		while(!(marked.isEmpty())){
			String aktuell = marked.poll();
			for(Nachbar elem : this.neighbors(aktuell)){
				if(elem.weight() + minEntf.get(aktuell) < minEntf.get(elem.name())){
					minEntf.put(elem.name(), elem.weight() + minEntf.get(aktuell));
					vorgaenger.put(elem.name(), aktuell);
					marked.add(elem.name());
				}
			}
		}
			
		// AusgabeString zusammenbauen
		StringBuffer weg = new StringBuffer();
		String aktuell = end;
		weg.append(" " + aktuell);
		while(!(aktuell.equals(start))){
			
			weg.append(", ");
			weg.append(vorgaenger.get(aktuell));
			aktuell = vorgaenger.get(aktuell);
		}
		weg.reverse();
		weg.append("Entfernung: ");
		weg.append(minEntf.get(end));
		return weg.toString();
	}
	
	//fuer Dijkstra
	private static String dNextNode(Map<String, Double> m){
		Double min = Double.POSITIVE_INFINITY;	
		for(Double elem : m.values()){
			if(elem < min) min = elem;
		}
		
		
		for(Map.Entry<String, Double> entry: m.entrySet()){
			if(entry.getValue() == min){
				return entry.getKey();
			}
		}
		return "Error";
	}
	
	public double residualGraph(String quelle, String senke){
		Graph residual = Graphs.adjList(this.initialString());
		residual.deleteZeroEdges();
		Pair<List<String>, Double> dikstra = residual.dijkstra(quelle, senke);
		double smallestValue = Double.POSITIVE_INFINITY;
		while(dikstra.getFirst().isEmpty()){
			System.out.println("Keine Wege mehr vorhanden!");
			ListIterator<String> it = dikstra.getFirst().listIterator();
			ListIterator<String> it2 = dikstra.getFirst().listIterator(1);
			
			while(it2.hasNext()){
				double currentVal = weightBetween(it.next(), it2.next());
				if(currentVal <= smallestValue){
					smallestValue = currentVal;
				}
			}
		}
		ListIterator<String> it = dikstra.getFirst().listIterator();
		ListIterator<String> it2 = dikstra.getFirst().listIterator(1);
		
		while(it2.hasNext()){
			String from = it.next();
			String to = it2.next();
			double newCapacity = weightBetween(from, to) - smallestValue;
			
			residual.changeCapacity(from, to, newCapacity);
			
			if(weightBetween(to, from) == Double.POSITIVE_INFINITY){
				residual.insert(to, from, smallestValue);
			}
			else{
				double newVal = weightBetween(to, from) + smallestValue;
				residual.changeCapacity(to, from, newVal);
			}
		}
		
		double result = 0;
		for(Nachbar elem : residual.neighbors(senke)){
			result += weightBetween(senke, elem.name());
		}
		return result;
	}
	

	public String initialString() {
		return this.einleseString;
	}
	
	public static List<String> reverse(List<String> l){
		List<String> result = new ArrayList<String>();
		ListIterator<String> it = l.listIterator(l.size());
		while(it.hasPrevious()){
			result.add(it.previous());
		}
		return result;
	}
	
	
	
}
