
public class sdfgh {
	public Graph fordFulkerson(String quelle, String senke){
		if(!(allNodes().contains(quelle))) throw new IllegalArgumentException();
		if(!(allNodes().contains(senke))) throw new IllegalArgumentException();

		Map<String,Pair<String,Double>> completed = new HashMap<String,Pair<String,Double>>();
		Queue<String> queue = new LinkedList<String>();

		//FlussGraph als Kopie der OriginalGraphen
		Graph flussGraph = Graphs.adjList(initialString());

		//Gewichtung bei allen Kanten von FlussGraph mit 0 init


		boolean finished = false;

		while(!finished){
			finished = true;

			//init wird bei jedem Teilschritt wiederholt
			completed.put(quelle, Graphs.createPair("undef", Double.POSITIVE_INFINITY) );
			queue.add(quelle);

			//eigentliche berechnung
			while(!queue.isEmpty()){
				String aktuell = queue.poll();
				List<Nachbar> positiveNeighbors = this.neighbors(aktuell);
				List<Pair<String,Double>> negativeNeighbors = this.edgesReverse(aktuell);

				//entferne bei positiveNeighbors alle markierten oder vollstaendig untersuchten ecken
				List<Nachbar> result = new ArrayList<Nachbar>();
				for(Nachbar elem : positiveNeighbors){
					if(!((completed.entrySet().contains(elem)) && (queue.contains(elem.name())))) result.add(elem);
				}
				positiveNeighbors = result;


				if(!(positiveNeighbors.isEmpty())) finished = false;

				//entferne bei negativeNeighbors alle markierten oder vollstaendig untersuchten ecken
				List<Pair<String,Double>> r = new ArrayList<Pair<String,Double>>(); 
				for(Pair<String,Double> elem : negativeNeighbors){
					if(!((completed.entrySet().contains(elem.getFirst())) && (queue.contains(elem.getFirst())))) r.add(elem);
				}
				negativeNeighbors = r;

				if(!(negativeNeighbors.isEmpty())) finished = false;

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
}
