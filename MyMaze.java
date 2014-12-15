import java.util.ArrayList;


public class MyMaze implements Maze {
	private MyGraph graph;
	private MyVertex[][] vertexArray;
	
	public MyMaze() {
		graph = new MyGraph();
	}

	@Override
	public void generateMaze(int rows, int columns) {
		vertexArray = new MyVertex[rows][columns];
		// TODO Auto-generated method stub
	}

	@Override
	public ArrayList<Vertex> solveMaze() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex finishVertex() {
		// TODO Auto-generated method stub
		return null;
	}

}
