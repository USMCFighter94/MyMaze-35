import java.util.ArrayList;

public class MyVertex implements Vertex {
	private MyPair element;
	private ArrayList<Edge> incidentEdges;
	private ArrayList<Vertex> adjacentVertices;
	private boolean visited;
	
	public MyVertex() {
		element = new MyPair();
		incidentEdges = new ArrayList<Edge>();
		adjacentVertices = new ArrayList<Vertex>();
		visited = false;
	}

	@Override
	public Pair getElement() {
		return element;
	}
	
	public boolean getVisited() {
		return visited;
	}

	@Override
	public void setElement(Pair e) {
		element = (MyPair) e;
	}
	
	public void setVisited(boolean t) {
		visited = t;
	}

	@Override
	public ArrayList<Edge> incidentEdges() {
		return incidentEdges;
	}

	@Override
	public ArrayList<Vertex> adjacentVertices() {
		return adjacentVertices;
	}

}
