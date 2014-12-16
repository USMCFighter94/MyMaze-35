import java.util.ArrayList;

public class MyGraph implements Graph {
	private ArrayList<Vertex> vertices;
	private ArrayList<Edge> edges;

	public MyGraph() {
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
	}

	@Override
	public ArrayList<Vertex> vertices() {
		return vertices;
	}

	@Override
	public Vertex addVertex(Pair p) { /** Not sure if this is correct */
		MyVertex newVertex = (MyVertex) p;
		vertices.add(newVertex);
		return newVertex;
	}

	@Override
	public Vertex addVertex(Vertex v) {
		if (vertices.contains(v))
			return v; // Don't re-add vertices
		vertices.add(v);
		return v;
	}

	@Override
	public boolean removeVertex(Pair p) { /** Not sure if this is correct */
		MyVertex tempVertex = (MyVertex) p;
		return vertices.remove(tempVertex);
	}

	@Override
	public boolean removeVertex(Vertex v) {
		getEdgesToRemove(v);
		return vertices.remove(v);
	}

	private void getEdgesToRemove(Vertex v) {
		int size = v.incidentEdges().size();

		for (int i = 0; i < size - 1; i++ )
			removeEdge(v.incidentEdges().get(i));	
	}

	@Override
	public Vertex findVertex(Pair p) { /** Not sure if this is correct */
		MyVertex tempVertex = (MyVertex) p;
		if (vertices.contains(tempVertex))
			return tempVertex;
		return null;
	}

	@Override
	public ArrayList<Edge> edges() {
		return edges;
	}

	@Override
	public Edge addEdge(Vertex v1, Vertex v2) {
		if (!vertices.contains(v1) || !vertices.contains(v2))
			return null; // Don't add an edge if one or both vertices don't already exist

		if (v1 == v2)
			return null; // Don't add self-looping edges

		MyEdge newEdge = new MyEdge();
		newEdge = (MyEdge) findEdge(v1, v2); // Search to see if edge already exists

		if (newEdge == null) {
			MyEdge newEdge2 = new MyEdge();
			newEdge2.vertices().add(0, (MyVertex) v1);
			newEdge2.vertices().add(1, (MyVertex) v2);
			edges.add(newEdge2);
			// Add adjacent vertices and incident edges links
			v1.adjacentVertices().add(v2);
			v2.adjacentVertices().add(v1);
			v1.incidentEdges().add(newEdge2);
			v2.incidentEdges().add(newEdge2);
			return newEdge2;
		}
		return newEdge;
	}

	@Override
	public Edge addEdge(Edge e) {
		if (!vertices.contains(e.vertices().get(0)) || !vertices.contains(e.vertices().get(1)))
			return null; // Don't add an edge if one or both vertices don't already exist

		edges.add((MyEdge) e);
		// Add adjacent vertices and incident edges links
		e.vertices().get(0).adjacentVertices().add(e.vertices().get(1));
		e.vertices().get(1).adjacentVertices().add(e.vertices().get(0));
		e.vertices().get(0).incidentEdges().add(e);
		e.vertices().get(1).incidentEdges().add(e);

		return e;
	}

	@Override
	public boolean removeEdge(Vertex v1, Vertex v2) {
		MyEdge tempEdge = (MyEdge) findEdge(v1, v2);

		if (tempEdge == null)
			return false;

		// Remove adjacent vertices and incident edges links
		tempEdge.vertices().get(0).adjacentVertices().remove(tempEdge.vertices().get(1));
		tempEdge.vertices().get(1).adjacentVertices().remove(tempEdge.vertices().get(0));

		tempEdge.vertices().get(0).incidentEdges().remove(tempEdge);
		tempEdge.vertices().get(1).incidentEdges().remove(tempEdge);

		return edges.remove(tempEdge);
	}

	@Override
	public boolean removeEdge(Edge e) {
		if (!edges.contains(e)) // If edge doesn't exist
			return false;
		// Remove adjacent vertices and incident edges links
		e.vertices().get(0).adjacentVertices().remove(e.vertices().get(1));
		e.vertices().get(1).adjacentVertices().remove(e.vertices().get(0));
		e.vertices().get(0).incidentEdges().remove(e);
		e.vertices().get(1).incidentEdges().remove(e);

		return edges.remove(e);
	}

	@Override
	public Edge findEdge(Vertex v1, Vertex v2) {
		MyEdge tempEdge = new MyEdge();

		for (int i = 0; i < edges.size(); i++) {
			tempEdge = (MyEdge) edges.get(i);
			if ((tempEdge.vertices().get(0) == v1 && tempEdge.vertices().get(1) == v2) || (tempEdge.vertices().get(0) == v2 && tempEdge.vertices().get(1) == v1))
				return tempEdge;	
		}
		return null;
	}

	@Override
	public boolean areConnected(Vertex v1, Vertex v2) {
		MyEdge tempEdge = new MyEdge();
		tempEdge = (MyEdge) findEdge(v1, v2);

		if (tempEdge != null)
			return true;
		return false;
	}

	@Override
	public ArrayList<Vertex> adjacentVertices(Vertex v1) {
		MyVertex myV1 = (MyVertex) v1;
		return myV1.adjacentVertices();
	}

	@Override
	public ArrayList<Edge> incidentEdges(Vertex v1) {
		MyVertex myV1 = (MyVertex) v1;
		return myV1.incidentEdges();
	}

	@Override
	public ArrayList<Vertex> shortestPath(Vertex v1, Vertex v2) {
		Vertex [] prev = new Vertex [vertices.size()];
		ArrayList <Vertex> queue = new ArrayList<Vertex>();
		ArrayList<Vertex> reversePath = new ArrayList<Vertex>();
		ArrayList<Vertex> shortPath = new ArrayList<Vertex>();
		int currentV = 0;
		
		for (int i = 0; i < vertices.size(); i += 1){
			((MyVertex) vertices.get(i)).setVisited(false);
			prev[i] = null;
			if(vertices.get(i) == v2){
				currentV = i;
			}
		}
		((MyVertex) v1).setVisited(true);
		queue.add(v1);
		while (!queue.isEmpty()){
			Vertex s = queue.remove(0);
			for(int i = 0; i < s.adjacentVertices().size(); i += 1){
				if(((MyVertex) s.adjacentVertices().get(i)).getVisited() == false) {
					((MyVertex) s.adjacentVertices().get(i)).setVisited(true);
					queue.add(s.adjacentVertices().get(i));
					prev[i] = s;
				}	
			}
		}
		while(prev[currentV] != null){
			Vertex temp = prev[currentV];
			reversePath.add(vertices.get(currentV));
			for( int i = 0; i < vertices.size(); i += 1){
				if(vertices.get(i) == temp){
					currentV = i;
				}
			}
		}
		reversePath.add(v1);
		for(int i = reversePath.size(); i > -1; i -= 1){
			shortPath.add(reversePath.get(i));
		}
		return shortPath;
	}
	}

	@Override
	public Graph minimumSpanningTree() {
		// TODO Auto-generated method stub
		return null;
	}

}
