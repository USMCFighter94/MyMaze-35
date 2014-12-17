import java.util.ArrayList;
import java.util.Random;

public class MyMaze implements Maze {
	private MyGraph graph;
	private MyVertex startVert;
	private MyVertex finishVert;
	private MyVertex[][] vertexArray;
	private static Random rand;
	private int rows;
	private int columns;

	public MyMaze() {
		graph = new MyGraph();
		startVert = new MyVertex();
		finishVert = new MyVertex();
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
		
		this.rows = rows;
		this.columns = columns;
		startVert = (MyVertex) startVertex();
		finishVert = (MyVertex) finishVertex();
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

	public static int randInt(int min, int max) {
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}
