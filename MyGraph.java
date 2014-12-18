import java.util.ArrayList;
import java.util.Random;

/**
 * This is a graph data structure that implements a graph by having an edge class that stores
 * the information for an edge and a vertex class that stores the information needed for a vertex. 
 * 
 * Zach Dunham, Colin Brevitz
 * MyMaze - 35
 * CS2321, Fall 2014
 * 
 */
public class MyGraph implements Graph {
	private ArrayList<Vertex> vertices;
	private ArrayList<Edge> edges;
	private static Random rand;

	//constructor
	public MyGraph() {
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		rand  = new Random();
	}

	/**
	 * Gets a list of all the vertices in the graph.
	 * 
	 * @return vertices, a list of all the vertices in the graph.			
	 */
	public ArrayList<Vertex> vertices() {
		return vertices;
	}

	/**
	 * Add a vertex based on its pair.
	 * 
	 * @param p, The pair to be added.
	 * @return newVertex, The vertex created for the pair.
	 */
	public Vertex addVertex(Pair p) {
		MyVertex newVertex = new MyVertex();
		newVertex.setElement(p);
		vertices.add(newVertex);
		return newVertex;
	}

	/**
	 * Adds a vertex to the graph.
	 * 
	 * @param v, The vertex to be added to the graph.
	 * @return v, The vertex added to the graph.
	 */
	public Vertex addVertex(Vertex v) {
		if (vertices.contains(v))
			return v; // Don't re-add vertices
		vertices.add(v);
		return v;
	}

	/**
	 * Remove a vertex based on it pair.
	 * 
	 * @param p, The pair to be removed
	 * @return True if the pair was removed, false otherwise 
	 */
	public boolean removeVertex(Pair p) {
		MyVertex tempVertex = new MyVertex();
		for (int i = 0; i < vertices.size(); i++) {

			tempVertex = (MyVertex) vertices.get(i);
			if (tempVertex.getElement() == p) {
				MyVertex tempVert = new MyVertex();

				for (int j = 0; j < tempVertex.adjacentVertices().size(); j++) {
					tempVert = (MyVertex) tempVertex.adjacentVertices().get(j);
					removeEdge(tempVertex, tempVert);
				}
				return vertices.remove(tempVertex);
			}	
		}
		return false;
	}

	/**
	 * Remove a vertex from the graph.
	 * 
	 * @param v, The vertex to be removed.
	 * @return True if the vertex v was removed, False if the vertex v was not removed.
	 */
	public boolean removeVertex(Vertex v) {
		getEdgesToRemove(v);
		return vertices.remove(v);
	}

	//helper for removeVertex
	private void getEdgesToRemove(Vertex v) {
		int size = v.incidentEdges().size();

		for (int i = 0; i < size - 1; i++ )
			removeEdge(v.incidentEdges().get(i));	
	}

	/**
	 * Find a vertex based on it's pair
	 * 
	 * @param p, The pair that is being searched for.
	 * @return tempVertex, The found vertex if it is found, null otherwise.
	 */
	public Vertex findVertex(Pair p) {
		MyVertex tempVertex = new MyVertex();
		for (int i = 0; i < vertices.size(); i++) {
			tempVertex = (MyVertex) vertices.get(i);
			if (tempVertex.getElement() == p)
				return tempVertex;
		}
		return null;
	}

	/**
	 * Gets a list of all the edges in the graph.
	 * 
	 * @return edges, A list of all the edges in the graph.
	 */
	public ArrayList<Edge> edges() {
		return edges;
	}

	/**
	 * Adds an edge to the graph given two vertices.
	 * 
	 * 
	 * @param v1, Vertex 1 to be connected.
	 * @param v2, Vertex 2 to be connected.
	 * 
	 * @return edge, The edge that connects vertex 1 to vertex 2.
	 */
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

	/**
	 * Adds an edge to the graph given an edge.
	 * 
	 * 
	 * @param e, The edge that needs to be added the graph.
	 * @return temp, The edge that was added to the graph.
	 */
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

	/**
	 * Removes an edge from the graph given two vertices.
	 * 
	 * 
	 * @param v1, The vertex with an edge.
	 * @param v2, The vertex that completes the edge.
	 * @return True if the edge was removed False if the edge was not removed.
	 */
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

	/**
	 * Removes an edge in the graph given an edge.
	 * 
	 * @param e, The edge to be removed.
	 * @return True if the edge was removed. False if the edge was not removed.
	 */
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

	/**
	 * Finds an edge in the graph given by its vertices.
	 * 
	 * @param v1, Vertex that has an edge.
	 * @param v2, Vertex that completes the edge.
	 * @return edge, The edge that was found in the graph. null if the edge wasnt found.
	 */
	public Edge findEdge(Vertex v1, Vertex v2) {
		MyEdge tempEdge = new MyEdge();

		for (int i = 0; i < edges.size(); i++) {
			tempEdge = (MyEdge) edges.get(i);
			if ((tempEdge.vertices().get(0) == v1 && tempEdge.vertices().get(1) == v2) || (tempEdge.vertices().get(0) == v2 && tempEdge.vertices().get(1) == v1))
				return tempEdge;	
		}
		return null;
	}

	/**
	 * Return true if two vertices are connected by an edge.
	 * 
	 * @param v1, Vertex 1 that could have an edge.
	 * @param v2, Vertex 2 that could complete that edge.
	 * @return True if the edge was found. False if the edge was not found.
	 */
	public boolean areConnected(Vertex v1, Vertex v2) {
		MyEdge tempEdge = new MyEdge();
		tempEdge = (MyEdge) findEdge(v1, v2);

		if (tempEdge != null)
			return true;
		return false;
	}

	/**
	 * Gets a list of all the adjacent vertices adjacent to v1.
	 * 
	 * @param v1, The vertex being tested for adjacent vertices.
	 * @return The list of all vertices that are adjacent to v1.
	 */
	public ArrayList<Vertex> adjacentVertices(Vertex v1) {
		MyVertex myV1 = (MyVertex) v1;
		return myV1.adjacentVertices();
	}

	/**
	 * Gets a list of the edges connected to v1.
	 * 
	 * @param v1, The vertex with edges connected to it.
	 * @return The list of the edges that are connected to v1.
	 */
	public ArrayList<Edge> incidentEdges(Vertex v1) {
		MyVertex myV1 = (MyVertex) v1;
		return myV1.incidentEdges();
	}

	/**
	 * Finds the shortest path between two vertices
	 * 
	 * @param v1, The vertex that we start searching from.
	 * @param v2 The vertex we end the search at.
	 * @return shortPath, A list of vertices that make up the shortest path between v1 and v2.
	 */
	public ArrayList<Vertex> shortestPath(Vertex v1, Vertex v2) {
		Vertex [] prev = new Vertex [vertices.size()];
		ArrayList <Vertex> queue = new ArrayList<Vertex>();
		ArrayList<Vertex> reversePath = new ArrayList<Vertex>();
		ArrayList<Vertex> shortPath = new ArrayList<Vertex>();
		int currentV = 0;
		int number;

		//sets all nodes isVisited to false
		for (int i = 0; i < vertices.size(); i += 1) {
			((MyVertex) vertices.get(i)).setVisited(false);
			prev[i] = null;
			//stores the location of the ending node
			if (vertices.get(i) == v2){
				currentV = i;
			}
		}
		((MyVertex) v1).setVisited(true);
		queue.add(v1);
		while (!queue.isEmpty()) {
			Vertex v = queue.remove(0);
			for (int i = 0; i < v.adjacentVertices().size(); i += 1) {
				if (((MyVertex) v.adjacentVertices().get(i)).getVisited() == false) {
					((MyVertex) v.adjacentVertices().get(i)).setVisited(true);
					queue.add(v.adjacentVertices().get(i));
					for (number = 0; number < vertices.size(); number += 1) {
						if (v.adjacentVertices().get(i) == vertices.get(number)) {
							break;
						}
					}
					prev[number] = v;
				}	
			}
		}				
		while (prev[currentV] != null) {
			Vertex temp = prev[currentV];
			reversePath.add(vertices.get(currentV));
			for (int i = 0; i < vertices.size(); i += 1) {
				if (vertices.get(i) == temp) {
					currentV = i;
				}
			}
		}
		reversePath.add(v1);
		for (int i = reversePath.size() - 1; i > -1; i -= 1) {
			shortPath.add(reversePath.get(i));
		}
		return shortPath;
	}

	/**
	 * Gets a minimum spanning tree out of the maze
	 * 
	 * @return graph, A graph of a minimum spanning tree in the maze.
	 */
	public Graph minimumSpanningTree() {
		ArrayList <Vertex> spanningTree = new ArrayList <Vertex>();
		MyGraph temp = new MyGraph();
		int start = randInt(0, vertices.size() - 1);
		int finsh = randInt(0, vertices.size() - 1);
		Vertex startVertex = vertices.get(start);
		Vertex finishVertex = vertices.get(finsh);
		spanningTree = shortestPath(startVertex, finishVertex);
		
		//add vertexs to spanning tree graph
		for (int i = 0; i < spanningTree.size(); i += 1) {
			MyVertex addThis = new MyVertex();
			addThis = (MyVertex) spanningTree.get(i);
			temp.addVertex(addThis);
		}
		//add edges to everything that needs them
		for (int i = 0; i < temp.vertices.size(); i += 1) {
			if (i != temp.vertices.size() - 1) {
				temp.addEdge(temp.vertices.get(i), temp.vertices.get(i + 1));
			}
		}
		return temp;
	}

	//helper for finding start and end of maze
	public static int randInt(int min, int max) {
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	
	public String toString () {
		String result = "<Graph:[";
		for (int i = 0; i < vertices.size(); i += 1) {
			if (i == vertices.size() - 1){
				result = result + vertices.get(i).toString();
			}
			else {
				result = result + vertices.get(i).toString() + ", ";
			}
		}
		result = result + "], [";
		for (int i = 0; i < edges.size(); i += 1) {
			if (i == edges.size() - 1) {
				result = result + edges.get(i).toString();
			}
			else {
				result = result + edges.get(i).toString() + ", ";
			}
		}
		result = result + "]>";
		return result;
	}
}