import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

/**
 * This is a maze data structure that generates a random maze based on user inputs. 
 * 
 * Zach Dunham, Colin Brevitz
 * MyMaze - 35
 * CS2321, Fall 2014
 * 
 */
public class MyMazeTest {

	@Test
	public void test() {
		MyMaze maze = new MyMaze();
		maze.generateMaze(5, 5);
		System.out.println("startVertext(): " + maze.startVertex());
		System.out.println("finishVertext(): " + maze.finishVertex());
		System.out.println("solveMaze(): " + maze.solveMaze());
		System.out.println("maze.toString():\n" + maze);
	}

	@Test
	public void testNumberOfEdges() {
		MyMaze maze = new MyMaze();
		maze.generateMaze(5, 5);
		Graph graph = maze.toGraph();
		if (graph.edges().size() != graph.vertices().size() - 1) {
			fail("For a 5 x 5 maze, there should be 24 edges. Instead, there are " + graph.edges().size() + " edges.");
		}
	}

	@Test
	public void testDistinctStartFinish() {
		MyMaze maze = new MyMaze();
		maze.generateMaze(5, 5);
		if (maze.startVertex() == maze.finishVertex()) {
			fail("startVertex and finishVertex are not unique.");
		}
	}

	@Test
	public void testArrayIsCorrectSize() {
		MyMaze maze = new MyMaze();
		maze.generateMaze(5, 5);
		Vertex[][] mazeArray = maze.toArray();
		if (mazeArray.length != 5 || mazeArray[0].length != 5) {
			fail("Maze array is not 5 x 5.");
		}
	}

	@Test
	public void testPathBetweenAllVertices() {
		MyMaze maze = new MyMaze();
		maze.generateMaze(4, 4);
		Graph graph = maze.toGraph();
		ArrayList<Vertex> vertices = graph.vertices();
		for (int i = 0; i < vertices.size(); i++) {
			for (int j = 0; j < vertices.size(); j++) {
				Vertex v1 = vertices.get(i);
				Vertex v2 = vertices.get(j);
				if (v1 != v2) {
					if (graph.shortestPath((MyVertex) v1, (MyVertex) v2).isEmpty()) {
						fail("Vertex " + v1 + " is not connected to vertex " + v2 + " in graph:" + graph);
					}
				}
			}
		}
	}

	@Test
	public void testSolutionPath() {
		MyMaze maze = new MyMaze();
		maze.generateMaze(4, 4);
		Graph graph = maze.toGraph();
		ArrayList<Vertex> path = graph.shortestPath(maze.startVertex(), maze.finishVertex());
		ArrayList<Vertex> solve = maze.solveMaze();
		if (path.size() != solve.size()) {
			fail("Path between start and finish differs in size from solution path.");
		}
		for (Vertex v: path) {
			if (!solve.contains(v)) {
				fail("Vertex " + v + " not found in solution: " + solve);
			}
		}
	}

	@Test
	public void testGenerateMaze() {
		MyMaze maze = new MyMaze();
		maze.generateMaze(5, 5);
		if (maze.solveMaze().isEmpty())
			fail("Maze wasn't generated");
	}

	@Test
	public void testSolveMaze() {
		MyMaze maze = new MyMaze();
		maze.generateMaze(5, 5);
		if (maze.solveMaze().isEmpty())
			fail("Maze wasn't generated");
	}

	@Test
	public void testToGraph() {
		MyMaze maze = new MyMaze();
		maze.generateMaze(5, 5);
		if (maze.toGraph() == null)
			fail("Graph returns null");
	}

	@Test
	public void testToArray() {
		MyMaze maze = new MyMaze();
		maze.generateMaze(5, 5);
		if (maze.toArray().length == 0)
			fail("maze array has size zero");
	}

	@Test
	public void testStartVertex() {
		MyMaze maze = new MyMaze();
		maze.generateMaze(5, 5);
		if (maze.startVertex() == null) {
			fail("startVertex is null.");
		}
	}

	@Test
	public void testFinishVertex() {
		MyMaze maze = new MyMaze();
		maze.generateMaze(5, 5);
		if (maze.finishVertex() == null) {
			fail("finishVertex is null.");
		}
	}
	
	@Test
	public void testVertices() {
		MyGraph graph = new MyGraph();
		
		int numVertices = graph.vertices().size();
		if (numVertices != 0)
			fail("Incorrect number of vertices in vertices ArrayList");
		
		MyVertex v0 = new MyVertex();
		MyVertex v1 = new MyVertex();
		MyVertex v2 = new MyVertex();
		
		graph.addVertex(v0);
		graph.addVertex(v1);
		graph.addVertex(v2);
		
		numVertices = 0;
		numVertices = graph.vertices().size();
		if (numVertices != 3)
			fail("Incorrect number of vertices in vertices ArrayList");
	}

	@Test
	public void testAddVertex() {
		MyGraph graph = new MyGraph();
		MyVertex v0 = new MyVertex();
		MyVertex v1 = new MyVertex();
		MyVertex v2 = new MyVertex();

		Vertex testV0 = graph.addVertex(v0);
		Vertex testV1 = graph.addVertex(v1);
		Vertex testV2 = graph.addVertex(v2);


		if (testV0 != v0)
			fail("Vertex 0 not added correctly");
		if (testV1 != v1)
			fail("Vertex 1 not added correctly");
		if (testV2 != v2)
			fail("Vertex 2 not added correctly");

		MyVertex v3 = v0;
		Vertex testV3 = graph.addVertex(v3);

		if (testV3 != v3)
			fail("Vertex 3 not added correctly");
	}

	@Test
	public void testRemoveVertex() {
		MyGraph graph = new MyGraph();
		MyVertex v0 = new MyVertex();
		MyVertex v1 = new MyVertex();
		MyVertex v2 = new MyVertex();
		
		graph.addVertex(v0);
		graph.addVertex(v1);
		
		boolean testV0Remove = graph.removeVertex(v0);
		
		if (!testV0Remove)
			fail("V0 not removed or didn't exist to start");
		
		boolean testV2Remove = graph.removeVertex(v2);
		if (testV2Remove)
			fail("Removed vertex that didn't exist");
	}


	@Test
	public void testAddEdge() {
		MyGraph graph = new MyGraph();
		MyVertex v0 = new MyVertex();
		MyVertex v1 = new MyVertex();
		MyVertex v2 = new MyVertex();

		Edge testE0 = graph.addEdge(v0, v1);
		Edge testE1 = graph.addEdge(v1, v2);
		Edge testE2 = graph.addEdge(v0, v2);
		
		if (testE0 != null)
			fail("Edge 0 not added correctly");
		if (testE1 != null)
			fail("Edge 1 not added correctly");
		if (testE2 != null)
			fail("Edge 2 not added correctly");
		
		graph.addVertex(v0);
		graph.addVertex(v1);
		graph.addVertex(v2);
		
		testE0 = graph.addEdge(v0, v1);
		testE1 = graph.addEdge(v1, v2);
		testE2 = graph.addEdge(v0, v2);
		
		if (testE0 == null)
			fail("Edge 0 not added correctly");
		if (testE1 == null)
			fail("Edge 1 not added correctly");
		if (testE2 == null)
			fail("Edge 2 not added correctly");
	}


	@Test
	public void testAddEdge2() {
		MyGraph graph = new MyGraph();
		MyVertex v0 = new MyVertex();
		MyVertex v1 = new MyVertex();
		MyVertex v2 = new MyVertex();
		
		graph.addVertex(v0);
		graph.addVertex(v1);
		graph.addVertex(v2);
		
		MyEdge e0 = new MyEdge();
		e0.vertices().add(0, v0);
		e0.vertices().add(1, v1);
		
		Edge testE0 = graph.addEdge(e0);
		
		if (testE0 == null)
			fail("Edge couldn't be added");
	} 

	@Test
	public void testEdges() {
		MyGraph graph = new MyGraph();
		MyVertex v0 = new MyVertex();
		MyVertex v1 = new MyVertex();
		MyVertex v2 = new MyVertex();
		
		graph.addVertex(v0);
		graph.addVertex(v1);
		graph.addVertex(v2);

		graph.addEdge(v0, v1);
		graph.addEdge(v1, v2);
		graph.addEdge(v0, v2);
		
		int numEdges = graph.edges().size();
		if (numEdges != 3)
			fail("Incorrect number of edges in ArrayList");
	}

	@Test
	public void testRemoveEdge() {
		MyGraph graph = new MyGraph();
		MyVertex v0 = new MyVertex();
		MyVertex v1 = new MyVertex();
		MyVertex v2 = new MyVertex();
		
		graph.addVertex(v0);
		graph.addVertex(v1);

		graph.addEdge(v0, v1);
		graph.addEdge(v1, v2);
		graph.addEdge(v0, v2);
		
		boolean teste0Remove = graph.removeEdge(v0, v1);
		
		if (!teste0Remove)
			fail("V0 not removed or didn't exist to start");
		
		boolean testE2Remove = graph.removeEdge(v0, v2);
		if (testE2Remove)
			fail("Removed edge that didn't exist");
	}

	@Test
	public void testRemoveEdge2() {
		MyGraph graph = new MyGraph();
		MyVertex v0 = new MyVertex();
		MyVertex v1 = new MyVertex();
		MyVertex v2 = new MyVertex();
		
		graph.addVertex(v0);
		graph.addVertex(v1);

		Edge e0 = graph.addEdge(v0, v1);
		graph.addEdge(v1, v2);
		graph.addEdge(v0, v2);
		
		Edge e2 = null;
		
		boolean teste0Remove = graph.removeEdge(e0);
		
		if (!teste0Remove)
			fail("V0 not removed or didn't exist to start");
		
		boolean testE2Remove = graph.removeEdge(e2);
		if (testE2Remove)
			fail("Removed vertex that didn't exist");
	}

	@Test
	public void testFindEdge() {
		MyGraph graph = new MyGraph();
		MyVertex v0 = new MyVertex();
		MyVertex v1 = new MyVertex();
		MyVertex v2 = new MyVertex();
		
		Edge testEdge = graph.findEdge(v0, v2);
		
		if (testEdge != null)
			fail("Edge that doesn't exist found");
		
		graph.addVertex(v0);
		graph.addVertex(v1);
		graph.addVertex(v2);

		Edge e0 = graph.addEdge(v0, v1);
		Edge e1 = graph.addEdge(v1, v2);
		Edge e2 = graph.addEdge(v0, v2);
		
		Edge testE0 = graph.findEdge(v0, v1);
		Edge testE1 = graph.findEdge(v1, v2);
		Edge testE2 = graph.findEdge(v0, v2);
		
		if (e0 != testE0)
			fail("Couldn't find Edge 0");
		if (e1 != testE1)
			fail("Couldn't find Edge 1");
		if (e2 != testE2)
			fail("Couldn't find Edge 2");
	}

	@Test
	public void testAreConnected() {
		MyGraph graph = new MyGraph();
		MyVertex v0 = new MyVertex();
		MyVertex v1 = new MyVertex();
		MyVertex v2 = new MyVertex();
		
		graph.addVertex(v0);
		graph.addVertex(v1);

		graph.addEdge(v0, v1);
		graph.addEdge(v1, v2);
		
		boolean connect0 = graph.areConnected(v0, v1);
		boolean connect1 = graph.areConnected(v1, v2);
		boolean connect2 = graph.areConnected(v0, v2);
		
		if (!connect0)
			fail("Returned not connected incorrectly");
		if (connect1)
			fail("Returned connected incorrectly");
		if (connect2)
			fail("Returned connected incorrectly");
	}

	@Test
	public void testAdjacentVertices() {
		MyGraph graph = new MyGraph();
		MyVertex v0 = new MyVertex();
		MyVertex v1 = new MyVertex();
		MyVertex v2 = new MyVertex();
		
		graph.addVertex(v0);
		graph.addVertex(v1);

		graph.addEdge(v0, v1);
		graph.addEdge(v1, v2);
		graph.addEdge(v0, v2);
		
		ArrayList<Vertex> adjVert0 = new ArrayList<Vertex>();
		adjVert0 = graph.adjacentVertices(v0);
		
		ArrayList<Vertex> adjVert1 = new ArrayList<Vertex>();
		adjVert1 = graph.adjacentVertices(v1);
		
		ArrayList<Vertex> adjVert2 = new ArrayList<Vertex>();
		adjVert2 = graph.adjacentVertices(v2);
		
		if (adjVert0.get(0) != v1 && adjVert0.get(1) != v2)
			fail("Adjacent vertices not set correctly");
		if (adjVert1.get(0) != v0 && adjVert1.get(1) != v2)
			fail("Adjacent vertices not set correctly");
		if (adjVert2.size() != 0)
			fail("Adjacent vertices exist for Edgethat doesn't exist");

	}

	@Test
	public void testIncidentEdges() {
		MyGraph graph = new MyGraph();
		MyVertex v0 = new MyVertex();
		MyVertex v1 = new MyVertex();
		MyVertex v2 = new MyVertex();
		
		graph.addVertex(v0);
		graph.addVertex(v1);

		Edge e0 = graph.addEdge(v0, v1);
		graph.addEdge(v1, v2);
		graph.addEdge(v0, v2);
		
		ArrayList<Edge> inEdge0 = new ArrayList<Edge>();
		inEdge0 = graph.incidentEdges(v0);
		
		ArrayList<Edge> inEdge1 = new ArrayList<Edge>();
		inEdge1 = graph.incidentEdges(v1);
		
		ArrayList<Edge> inEdge2 = new ArrayList<Edge>();
		inEdge2 = graph.incidentEdges(v2);
		
		if (inEdge0.get(0) != e0)
			fail("Incident edges not set correctly");
		if (inEdge1.size() != 1)
			fail("Incident edges not set correctly");
		if (inEdge2.size() != 0)
			fail("Incident edges not set correctly");

	}
}