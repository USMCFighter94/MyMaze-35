import java.util.ArrayList;

public interface Edge {
   // Accessors for the element of the edge
   public int getElement();
   public void setElement(int e);
   
   // List the vertices connected by this edge
   public ArrayList<Vertex> vertices();

}