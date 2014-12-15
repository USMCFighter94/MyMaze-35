import java.util.ArrayList;

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

}
