import java.util.ArrayList;
import java.util.Random;

/**
 * This is a maze data structure that generates a random maze based on user inputs. 
 * 
 * Zach Dunham, Colin Brevitz
 * MyMaze - 35
 * CS2321, Fall 2014
 * 
 */
public class MyMaze implements Maze {
	private MyGraph graph;
	private MyVertex startVert;
	private MyVertex finishVert;
	private MyVertex[][] vertexArray;
	private ArrayList<MyVertex> frontierVerts;
	private ArrayList<MyVertex> masterFrontierVerts;
	private ArrayList<Frontier> frontier;
	private static Random rand;
	private int rows;
	private int columns;

	//constructor
	public MyMaze() {
		graph = new MyGraph();
		startVert = new MyVertex();
		finishVert = new MyVertex();
		frontierVerts = new ArrayList<MyVertex>();
		masterFrontierVerts = new ArrayList<MyVertex>();
		frontier = new ArrayList<Frontier>();
		rand  = new Random();
		rows = 0;
		columns = 0;
	}

	/**
	 * Generates maze with a specified amount of rows and columns.
	 * 
	 * @param rows,
	 * 			The number of rows.
	 * @param columns,
	 * 			The number of columns.
	 * 
	 */
	public void generateMaze(int rows, int columns) {
		vertexArray = new MyVertex[rows][columns];
		this.rows = rows;
		this.columns = columns;
		MyVertex random = new MyVertex();
		MyVertex parent = new MyVertex();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				MyVertex tempVertex = new MyVertex();
				MyPair newPair = new MyPair();
				newPair.setX(i);
				newPair.setY(j);
				tempVertex.setElement(newPair);
				graph.addVertex(tempVertex);
				vertexArray[i][j] = tempVertex;
			}
		}

		int ranX = randInt(0, rows);
		int randY = randInt(0, columns);
		MyPair tempPair = new MyPair();
		tempPair.setX(ranX);
		tempPair.setY(randY);

		MyVertex startVertex = new MyVertex();

		for (int k = 0; k < graph.vertices().size(); k++) {
			startVertex = (MyVertex) graph.vertices().get(k);
			if (startVertex.getElement().getX() == tempPair.getX() &&
					startVertex.getElement().getY() == tempPair.getY())
				break;
		}

		// Get frontier vertices for starting vertex
		findFrontiers(startVertex, parent);

		while (!frontierVerts.isEmpty()) {

			// Get random frontier
			random  = findRandomFrontierVertex();

			// Find Random's parent
			for (int j = 0; j < frontier.size(); j++) {
				if (frontier.get(j).getTop() == random)
					parent = frontier.get(j).getParent();
				if (frontier.get(j).getLeft() == random)
					parent = frontier.get(j).getParent();
				if (frontier.get(j).getRight() == random)
					parent = frontier.get(j).getParent();
				if (frontier.get(j).getBottom() == random)
					parent = frontier.get(j).getParent();
			}
			if (parent == null) {

			} else {
				// Connect parent and random
				graph.addEdge(parent, random);
			}
			// Add random's frontiers
			findFrontiers(random, parent);
		}
	}

	//helper for generate maze
	private MyVertex findRandomFrontierVertex() {
		int random = randInt(0, frontierVerts.size() - 1);
		return frontierVerts.remove(random);
	}

	//helper for generate maze
	private void findFrontiers(MyVertex vertex, MyVertex parent) {
		MyVertex tempVertex = new MyVertex();
		Frontier newFront = new Frontier(vertex);

		//go through all the vertices in the graph
		for (int j = 0; j < graph.vertices().size(); j++) {
			tempVertex = (MyVertex) graph.vertices().get(j);
			MyPair tempPair = (MyPair) tempVertex.getElement();
			if(tempVertex == vertex) {
			}
			else if (tempVertex == parent){
			}
			else {
				if (vertex.getElement().getX() - 1 == tempPair.getX() &&
						vertex.getElement().getY() == tempPair.getY()) {
					if (!masterFrontierVerts.contains(tempVertex)) {
						frontierVerts.add(tempVertex);
						masterFrontierVerts.add(tempVertex);
						newFront.setLeft(tempVertex);
					}
				}
				if (vertex.getElement().getX() + 1 == tempPair.getX() &&
						vertex.getElement().getY() == tempPair.getY()) {
					if (!masterFrontierVerts.contains(tempVertex)) {
						frontierVerts.add(tempVertex);
						masterFrontierVerts.add(tempVertex);
						newFront.setRight(tempVertex);
					}

				}
				if (vertex.getElement().getY() - 1 == tempPair.getY() &&
						vertex.getElement().getX() == tempPair.getX()) {
					if (!masterFrontierVerts.contains(tempVertex)) {
						frontierVerts.add(tempVertex);
						masterFrontierVerts.add(tempVertex);
						newFront.setBottom(tempVertex);
					}

				}
				if (vertex.getElement().getY() + 1 == tempPair.getY() &&
						vertex.getElement().getX() == tempPair.getX()) {
					if (!masterFrontierVerts.contains(tempVertex)) {
						frontierVerts.add(tempVertex);
						masterFrontierVerts.add(tempVertex);
						newFront.setTop(tempVertex);
					}
				}
			}
		}
		frontier.add(newFront);
	}

	/**
	 * Solves the maze.
	 * 
	 * @return an Arraylist containing the path of vertices to take to 
	 * solve the maze
	 * 
	 */
	public ArrayList<Vertex> solveMaze() {
		return graph.shortestPath(startVert, finishVert);
	}

	/**
	 * Returns a graph representation of the maze. 
	 */
	public Graph toGraph() {
		return graph;
	}

	/**
	 * returns an 2D array of the vertices in the maze.
	 */
	public Vertex[][] toArray() {
		return vertexArray;
	}

	/**
	 * chooses a start vertex in the maze
	 */
	public Vertex startVertex() {
		int startX = randInt(0, rows - 1);
		int startY = randInt(0, columns - 1);

		
		MyPair newPair = new MyPair();
		newPair.setX(startX);
		newPair.setY(startY);
	
		MyVertex vertex = new MyVertex();
		for (int i = 0; i < graph.vertices().size(); i++) {
			vertex = (MyVertex) graph.vertices().get(i);
			if (vertex.getElement().getX() == newPair.getX() &&
					vertex.getElement().getY() == newPair.getY()){
				startVert = vertex;
				return startVert;
			}
		}
		return null;
	}
	
	/**
	 * chooses a finish vertex in the maze
	 */
	public Vertex finishVertex() {
		int startX = randInt(0,rows - 1);
		int startY = randInt(0, columns - 1);

		MyPair newPair = new MyPair();
		newPair.setX(startX);
		newPair.setY(startY);

		MyVertex vertex = new MyVertex();
		for (int i = 0; i < graph.vertices().size(); i++) {
			vertex = (MyVertex) graph.vertices().get(i);
			if (vertex.getElement().getX() == newPair.getX() &&
					vertex.getElement().getY() == newPair.getY()){
				finishVert = vertex;
				return finishVert;
			}
		}
		return null;
	}

	//helper for finding start and end of maze
	public static int randInt(int min, int max) {
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	//helper class to store each vertex and its adjacent vertices
	private class Frontier {
		private MyVertex parent;
		private MyVertex top;
		private MyVertex left;
		private MyVertex right;
		private MyVertex bottom;

		public Frontier(MyVertex vertex) {
			parent = vertex;
			top = null;
			left = null;
			right = null;
			bottom = null;
		}

		public MyVertex getParent() {
			return parent;
		}

		public void setTop(MyVertex v) {
			top = v;
		}

		public MyVertex getTop() {
			return top;
		}

		public void setLeft(MyVertex v) {
			left = v;
		}

		public MyVertex getLeft() {
			return left;
		}

		public void setRight(MyVertex v) {
			right = v;
		}

		public MyVertex getRight() {
			return right;
		}

		public void setBottom(MyVertex v) {
			bottom = v;
		}

		public MyVertex getBottom() {
			return bottom;
		}
	}

	public String toString () {
		String result = "";
		
		for (int i = 0; i < vertexArray.length; i++) {
			for (int j = 0; j < vertexArray[i].length; j ++) {
				result += vertexArray[i][j] + " ";
			}
			result += "\n";
		}
		return result;
	}
}

