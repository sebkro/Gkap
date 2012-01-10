package graph;

import java.util.HashSet;
import java.util.Set;

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

	public int compareTo(Edge e) {
		return Double.compare(this.weight(),e.weight());
		
	}

	public Set<String> st() {
		Set<String> result = new HashSet<String>();
		result.add(node1);
		result.add(node2);
		return result;
	}
	
	public String getNode1(){
		return this.node1;
	}
	
	public String getNode2(){
		return this.node2;
	}

	public double weight() {
		return this.weight;
	}
	
	public String toString(){
		return this.node1 + " " + this.node2 + " " + this.weight;
	}
	
	public boolean equals(Object o){
		if(! (o instanceof Edge)) return false;
		Edge other = (Edge)o;
		return this.st().containsAll(other.st()) && this.weight == other.weight();
		
	}
	
	public int hashCode(){
		int result = 1;
		result = (int) (result * 7 * this.weight());
		result = result * 13 * this.st().hashCode();
		return result;
	}

}
