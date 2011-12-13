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
		result.setFirst(new ArrayList<String>());
		result.setSecond(Double.NaN);
		
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
		String aktuell = end;
		resultList.add(aktuell);
		while(!(aktuell.equals(start))){
			resultList.add(vorgaenger.get(aktuell));
			aktuell = vorgaenger.get(aktuell);
		}
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
	
	public Pair<Graph, Double> residualGraph(String quelle, String senke){
		Graph residual = Graphs.adjMatrix(this.initialString());
		residual.deleteZeroEdges();
		Pair<List<String>, Double> dikstra = residual.dijkstra(quelle, senke);
		
		while(!(dikstra.getFirst().isEmpty())){
			
			double smallestValue = Double.POSITIVE_INFINITY;
			
			ListIterator<String> it = dikstra.getFirst().listIterator();
			ListIterator<String> it2 = dikstra.getFirst().listIterator(1);
			
			while(it2.hasNext()){
				double currentVal = residual.weightBetween(it.next(), it2.next());
				if(currentVal <= smallestValue){
					smallestValue = currentVal;
				}
			}
	
		
			ListIterator<String> ite = dikstra.getFirst().listIterator();
			ListIterator<String> ite2 = dikstra.getFirst().listIterator(1);
			
			while(ite2.hasNext()){
				String from = ite.next();
				String to = ite2.next();
				double newCapacity = residual.weightBetween(from, to) - smallestValue;
				
				residual.changeCapacity(from, to, newCapacity);
				
				if(weightBetween(to, from) == Double.POSITIVE_INFINITY){
					residual.insert(to, from, smallestValue);
				}
				else{
					double newVal = residual.weightBetween(to, from) + smallestValue;
					residual.changeCapacity(to, from, newVal);
				}
			}
			residual.deleteZeroEdges();
			dikstra = residual.dijkstra(quelle, senke);
		}
		
		double result = 0;
		for(Nachbar elem : residual.neighbors(senke)){
			double val = residual.weightBetween(senke, elem.name());
			result += val;
		}
		Pair<Graph,Double> p = new Pair<Graph,Double>();
		p.setFirst(residual);
		p.setSecond(result);
		return p;
	}
	
	public Pair<Graph,Double> fordFulkerson(String quelle, String senke){
		if(!(allNodes().contains(quelle))) throw new IllegalArgumentException();
		if(!(allNodes().contains(senke))) throw new IllegalArgumentException();
		
		Map<String,Pair<String,Double>> completed = new HashMap<String,Pair<String,Double>>();
		Queue<String> queue = new LinkedList<String>();
		
		//FlussGraph als Kopie der OriginalGraphen
		Graph flussGraph = Graphs.adjList(initialString());
		flussGraph.allEdgesZero();
		
		//Gewichtung bei allen Kanten von FlussGraph mit 0 init
		
		
		boolean finished = false;
		
		while(!finished){
			
			
			
			//init wird bei jedem Teilschritt wiederholt
			completed.put(quelle, Graphs.createPair("undef", Double.POSITIVE_INFINITY) );
			queue.add(quelle);
			
			//eigentliche berechnung
			
			
			while(!(queue.isEmpty())){
				
				String aktuell = queue.poll();
				List<Nachbar> positiveNeighbors = new ArrayList<Nachbar>();
				List<Pair<String,Double>> negativeNeighbors = new ArrayList<Pair<String,Double>>();
				
				//entferne bei positiveNeighbors alle markierten oder vollstaendig untersuchten ecken
				for(Nachbar elem : this.neighbors(aktuell)){
					if(weightBetween(aktuell,elem.name()) - flussGraph.weightBetween(aktuell, elem.name()) > 0 ){
						if(!(completed.keySet().contains(elem.name()))){
							positiveNeighbors.add(elem);
						}
					}
				}
				
										
				//entferne bei negativeNeighbors alle markierten oder vollstaendig untersuchten ecken
				
				for(Pair<String,Double> elem : edgesReverse(aktuell) ){
					if(flussGraph.weightBetween(elem.getFirst(), aktuell) > 0){
						if(!(completed.containsKey(elem.getFirst()))){
							negativeNeighbors.add(elem);
						}
					}
				}
				
				
				if(negativeNeighbors.isEmpty() && positiveNeighbors.isEmpty() && queue.isEmpty()){
					finished = true;
					queue.clear();
				}
				
				double flussVorgaenger = Math.abs(completed.get(aktuell).getSecond());
				
				for(Nachbar elem : positiveNeighbors){
					//berechnung des Flusses
					
					double flussAktuell = this.weightBetween(aktuell, elem.name()) - flussGraph.weightBetween(aktuell, elem.name());
					
					if(flussAktuell > 0){
						flussAktuell = Math.min(flussVorgaenger, flussAktuell);
						
						//elem in completed eintragen (mit Vorgaenger und Fluss)
					
						completed.put(elem.name(), Graphs.createPair(aktuell, flussAktuell));
						queue.add(elem.name());
						
						//Abbruch der Schleife in naechsten Durchlauf, wenn senke gefunden
						if(elem.name().equals(senke)) queue.clear();
					}
						 
				}
				
				for(Pair<String,Double> elem : negativeNeighbors){
					double flussAktuell = flussGraph.weightBetween(elem.getFirst(), aktuell);
					flussAktuell = Math.min(flussAktuell, flussVorgaenger); 
					completed.put(elem.getFirst(), Graphs.createPair(aktuell, flussAktuell * (-1.0) ));
					queue.add(elem.getFirst());
				}
				
				
				
				
			}
			
			// aktualisieren des FlussGraphen
			
			
			if(!finished){
				double value = completed.get(senke).getSecond(); //zu erhoehender Wert
				
				String ecke = senke;
				
				while(!(ecke.equals(quelle))){
					String vorgaenger = completed.get(ecke).getFirst();
					if(completed.get(ecke).getSecond() > 0){
						flussGraph.changeCapacity(vorgaenger, ecke, flussGraph.weightBetween(vorgaenger, ecke) + value);
					}else{
						flussGraph.changeCapacity(ecke, vorgaenger, flussGraph.weightBetween(ecke, vorgaenger) - value);
					}
					
					ecke = vorgaenger;
				}
			}
				
			//markierungen zuruecksetzen
			
			completed.clear();
			
		}
		
		// fluesse fertig berechnet ==> bewertungen der von s ausgehenden kanten zusammenrechnen
		
		double fluss = 0;
		
		for(Nachbar elem : flussGraph.neighbors(quelle)){
			fluss += elem.weight();
		}
		
		this.setZugriffe(flussGraph.getZugriffe() + this.getZugriffe());
		return Graphs.createPair(flussGraph, fluss);
		
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
