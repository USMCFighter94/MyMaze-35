import java.util.ArrayList;

public class MyVertex implements Vertex {
	private MyPair element;
	private ArrayList<Edge> incidentEdges;
	private ArrayList<Vertex> adjacentVertices;
	
	public MyVertex() {
		element = new MyPair();
		incidentEdges = new ArrayList<Edge>();
		adjacentVertices = new ArrayList<Vertex>();
	}

	@Override
	public Pair getElement() {
		return element;
	}

	@Override
	public void setElement(Pair e) {
		element = (MyPair) e;
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
