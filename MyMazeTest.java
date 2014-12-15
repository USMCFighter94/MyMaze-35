import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

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

	}

	@Test
	public void testSolveMaze() {

	}

	@Test
	public void testToGraph() {

	}

	@Test
	public void testToArray() {

	}

	@Test
	public void testStartVertex() {

	}

	@Test
	public void finishVertex() {

	}
}
