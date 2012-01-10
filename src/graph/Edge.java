package graph;

import java.util.Set;

public interface Edge extends Comparable<Edge> {
	public Set<String> st();
	public double weight();
	public String getNode1();
	public String getNode2();
}
