import java.util.ArrayList;

/**
 * MyEdge class that holds what edges are in the graph.
 * 
 * Zach Dunham, Colin Brevitz
 * MyMaze - 35
 * CS2321, Fall 2014
 * 
 */
public class MyEdge implements Edge {
	private int element;
	private ArrayList<Vertex> vertices;
	
	public MyEdge() {
		element = 0;
		vertices = new ArrayList<Vertex>();
	}

	@Override
	public int getElement() {
		return element;
	}

	@Override
	public void setElement(int e) {
		element = e;
	}

	@Override
	public ArrayList<Vertex> vertices() {
		return vertices;
	}
	
	public String toString () {
		return "<" + vertices.get(0) + " - " + vertices.get(1) + ">";
	}
}