package graph;

public class NachbarImpl implements Nachbar {
	private String name;
	private double weight;
	
	private NachbarImpl(String name, double weight){
		this.name = name;
		this.weight = weight;
	}
	
	public static Nachbar valueOf(String name, double weight){
		return new NachbarImpl(name,weight);
	}
	
	public double weight(){
		return this.weight;
	}
	
	public String name(){
		return this.name;
	}
	
	public String toString(){
		return "Nachbar: "+ this.name() + " " + String.valueOf(this.weight());
	}
	
	public void setWeight(double weight){
		this.weight = weight;
	}
	
	public boolean equals(Object o){
		if(!(o instanceof Nachbar)) return false;
		Nachbar n = (Nachbar) o;
		return name.equals(n.name()) && weight == (n.weight());
	}
}
