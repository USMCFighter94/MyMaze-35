import java.util.ArrayList;


public interface Maze {

   // Returns a random maze with the specified number of rows and columns
   public void generateMaze( int rows, int columns );
   
   // Return an ordered list representing a path of vertices from the start to the finish
   public ArrayList<Vertex> solveMaze( );
   
   // Return a graph representing the maze
   public Graph toGraph( );
   // Return an array of vertices representing the maze
   public Vertex [ ][ ] toArray( );
   
   // Return the start vertex
   public Vertex startVertex( );
   // Return the end vertex
   public Vertex finishVertex( );

}