package graph;

public class EdgeImpl implements Edge {
	private final String node1;
	private final String node2;
	private final double weight;
	
	private EdgeImpl(String node1,String node2, double weight){
		this.node1 = node1;
		this.node2 = node2;
		this.weight = weight;
	}
	
	public static Edge create(String node1, String node2, double weight){
		return new EdgeImpl(node1,node2,weight);
	}

	@Override
	public int compareTo(Edge e) {
		return Double.compare(this.weight(),e.weight());
		
	}

	@Override
	public String[] st() {
		String[] result = {node1,node2};
		return result;
	}

	@Override
	public double weight() {
		return this.weight;
	}
	
	public String toString(){
		return this.node1 + " " + this.node2 + " " + this.weight;
	}

}
