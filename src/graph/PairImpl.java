package graph;

public class PairImpl<X, Y> implements Pair {
	
	X first;
	Y second;
	
	public PairImpl(){
		
	}
	
	public X getFirst(){
		return first;
	}
	
	public Y getSecond(){
		return second;
	}
	
	public void setFirst(X first){
		this.first = first;
	}
	
	public void setSecond(Y second){
		this.second = second;
	}
}
