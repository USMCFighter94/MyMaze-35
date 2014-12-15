import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {

   private static final long serialVersionUID = 4648172894076113183L;
   
   MyMaze maze = null;
   ArrayList<Vertex> path = null;
   int rows = 50;
   int columns = 50;
   
   public Main() {
      maze = new MyMaze();
      maze.generateMaze(rows, columns);
      path = maze.solveMaze();
      JFrame frame = new JFrame("CS2321 Final Program Maze");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(1024, 768);
      frame.setContentPane(this);
      frame.setVisible(true);
   }
   
   public void paintComponent( Graphics g ) {
      Vertex[ ][ ] map = maze.toArray( );
      Graph graph = maze.toGraph( );
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor( Color.WHITE );
      g2.fillRect( 0, 0, this.getWidth( ), this.getHeight( ) );
      double w = Math.round( (this.getWidth( )-5)/( 1.0 * (columns+2) ) );
      double h = Math.round( (this.getHeight( )-5)/( 1.0 * (rows+2) ) );
      for( int r = 0; r < rows; r++ ) {
         for( int c = 0; c < columns; c++ ) {
            if ( map[r][c] == maze.startVertex( ) ) {
               g2.setColor( Color.GREEN );
               g2.setStroke(new BasicStroke(5));
               g2.fillRect( (int) ( c * w + w ), (int) ( r * h + h ), (int) w, (int) h );
            } else if ( map[r][c] == maze.finishVertex( ) ) {
               g2.setColor( Color.RED );
               g2.setStroke(new BasicStroke(5));
               g2.fillRect( (int) ( c * w + w ), (int) ( r * h + h ), (int) w, (int) h );
            } else if ( path != null && path.contains( map[r][c] ) ) {
               g2.setColor( Color.BLUE );
               g2.setStroke(new BasicStroke(5));
               g2.fillOval( (int) ( c * w + 5*w/4 ), (int) ( r * h + 5*h/4 ), (int) w/2, (int) h/2 );
            } 
         }
      }
      for( int r = 0; r < rows; r++ ) {
         for( int c = 0; c < columns; c++ ) {
            if ( (c == 0) || ( !graph.areConnected( map[r][c], map[r][c-1] ) ) ) {
               g2.setColor( Color.BLACK );
               g2.setStroke(new BasicStroke(5));
               g2.drawLine( (int) ( c * w + w ), (int) ( r * h + h ), (int) ( c * w + w ), (int) ( (r+1) * h + h ) );
            }
            if ( (c == columns - 1) || ( !graph.areConnected( map[r][c], map[r][c + 1] ) ) ) {
               g2.setColor( Color.BLACK );
               g2.setStroke(new BasicStroke(5));
               g2.drawLine( (int) ( (c+1) * w + w ), (int) ( r * h + h ), (int) ( (c+1) * w + w ), (int) ( (r+1) * h + h ) );
            }
            if ( (r == 0) || ( !graph.areConnected( map[r][c], map[r-1][c] ) ) ) {
               g2.setColor( Color.BLACK );
               g2.setStroke(new BasicStroke(5));
               g2.drawLine( (int) ( c * w + w ), (int) ( r * h + h ), (int) ( (c+1) * w + w ), (int) ( r * h + h ) );
            }
            if ( (r == rows - 1) || ( !graph.areConnected( map[r][c], map[r + 1][c] ) ) ) {
               g2.setColor( Color.BLACK );
               g2.setStroke(new BasicStroke(5));
               g2.drawLine( (int) ( c * w + w ), (int) ( (r+1) * h + h ), (int) ( (c+1) * w + w ), (int) ( (r+1) * h + h ) );
            }
         }
      }
   }

   public static void main( String[ ] args ) {
      new Main( );
   }
}