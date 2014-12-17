import java.util.ArrayList;
import java.util.Random;

public class MyMaze implements Maze {
	private MyGraph graph;
	private MyVertex startVert;
	private MyVertex finishVert;
	private MyVertex[][] vertexArray;
	private ArrayList<MyVertex> frontierVerts;
	private ArrayList<Frontier> frontier;
	private static Random rand;
	private int rows;
	private int columns;

	public MyMaze() {
		graph = new MyGraph();
		startVert = new MyVertex();
		finishVert = new MyVertex();
		frontierVerts = new ArrayList<MyVertex>();
		frontier = new ArrayList<Frontier>();
		rand  = new Random();
		rows = 0;
		columns = 0;
	}

	@Override
	public void generateMaze(int rows, int columns) {
		vertexArray = new MyVertex[rows][columns];

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
			if (startVertex.getElement() == tempPair)
				break;
		}

		// Get frontier vertices for starting vertex
		findFrontiers(startVertex);

		MyVertex random = new MyVertex();
		MyVertex parent = new MyVertex();

		while (!frontierVerts.isEmpty()) {
			// Get random frontier
			random  = findRandomFrontierVertex();

			// Find Random's parent
			for (int j = 0; j < frontier.size(); j++) {
				if (frontier.get(j).getTop() == random);
				parent = frontier.get(j).getParent();
				if (frontier.get(j).getLeft() == random)
					parent = frontier.get(j).getParent();
				if (frontier.get(j).getRight() == random)
					parent = frontier.get(j).getParent();
				if (frontier.get(j).getBottom() == random)
					parent = frontier.get(j).getParent();
			}
			// Connect parent and random
			graph.addEdge(parent, random);

			// Add random's frontiers
			findFrontiers(random);
		}

		this.rows = rows;
		this.columns = columns;
		startVert = (MyVertex) startVertex();
		finishVert = (MyVertex) finishVertex();
	}

	private MyVertex findRandomFrontierVertex() {
		int random = randInt(0, frontierVerts.size());
		return frontierVerts.get(random);
	}

	private void findFrontiers(MyVertex vertex) {
		MyVertex tempVertex = new MyVertex();
		Frontier newFront = new Frontier(vertex);

		for (int j = 0; j < graph.vertices().size(); j++) {
			tempVertex = (MyVertex) graph.vertices().get(j);
			MyPair tempPair = (MyPair) tempVertex.getElement();

			if (tempVertex.getVisited())
				continue;
			
			if (vertex.getElement().getX() - 1 == tempPair.getX()) {
				switch (isEdge(tempVertex)) {
				case 0:
					frontierVerts.add(tempVertex);
					newFront.setLeft(tempVertex);
					break;
				case 1:
				}
			}
			
			if (vertex.getElement().getX() + 1 == tempPair.getX()) {
				switch (isEdge(tempVertex)) {
				case 0:
					frontierVerts.add(tempVertex);
					newFront.setRight(tempVertex);
					break;
				case 1:
				}
			}
			
			if (vertex.getElement().getY() - 1 == tempPair.getY()) {
				switch (isEdge(tempVertex)) {
				case 0:
					frontierVerts.add(tempVertex);
					newFront.setBottom(tempVertex);
					break;
				
				}
			}
			
			if (vertex.getElement().getY() + 1 == tempPair.getY()) {
				switch (isEdge(tempVertex)) {
				case 0:
					frontierVerts.add(tempVertex);
					newFront.setTop(tempVertex);
					break;
				
				}
			}
		}
		frontier.add(newFront);
	}

	private int isEdge(MyVertex v) {
		MyPair tempPair2 = (MyPair) v.getElement();

		// Right Side
		if (tempPair2.getX() + 1 > rows)
			return 3;

		// Left Side
		if (tempPair2.getX() - 1 < rows)
			return 2;

		// Bottom
		if (tempPair2.getY() + 1 > columns)
			return 4;

		// Top
		if (tempPair2.getY() - 1 < columns)
			return 1;
		
		// Top Left
		if (tempPair2.getY() - 1 < columns && tempPair2.getX() - 1 < rows)
			return 12;
		
		// Top Right
		if (tempPair2.getY() - 1 < columns && tempPair2.getX() + 1 > rows)
			return 13;
		
		// Bottom Left
		if (tempPair2.getY() + 1 > columns && tempPair2.getX() - 1 < rows)
			return 42;
		
		// Bottom Right
		if (tempPair2.getY() + 1 < columns && tempPair2.getX() + 1 > rows)
			return 43;
		
		return 0;
	}

	@Override
	public ArrayList<Vertex> solveMaze() {
		return graph.shortestPath(startVert, finishVert);
	}

	@Override
	public Graph toGraph() {
		return graph;
	}

	@Override
	public Vertex[][] toArray() {
		return vertexArray;
	}

	@Override
	public Vertex startVertex() {
		int startX = randInt(0,rows);
		int startY = randInt(0, columns);

		MyPair newPair = new MyPair();
		newPair.setX(startX);
		newPair.setY(startY);

		MyVertex vertex = new MyVertex();
		for (int i = 0; i < graph.vertices().size(); i++) {
			vertex = (MyVertex) graph.vertices().get(i);
			if (vertex.getElement() == newPair)
				return vertex;
		}
		return null;
	}

	@Override
	public Vertex finishVertex() {
		int startX = randInt(0,rows);
		int startY = randInt(0, columns);

		MyPair newPair = new MyPair();
		newPair.setX(startX);
		newPair.setY(startY);

		MyVertex vertex = new MyVertex();
		for (int i = 0; i < graph.vertices().size(); i++) {
			vertex = (MyVertex) graph.vertices().get(i);
			if (vertex.getElement() == newPair)
				return vertex;
		}
		return null;
	}

	private static int randInt(int min, int max) {
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

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
}
