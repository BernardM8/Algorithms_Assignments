import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class ConvexHullGUI {

    public static void main(String[] args) {
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, 10000);
        StdDraw.setYscale(0, 10000);
        StdDraw.enableDoubleBuffering();

        ArrayList<Point2D> pointList = new ArrayList<Point2D>();

        //for testing
       /* pointList.add(new Point2D(1, 1.));
        pointList.add(new Point2D(4 5));
        pointList.add(new Point2D(3, 3));
        pointList.add(new Point2D(5, 3));
        pointList.add(new Point2D(5201.0, 3140.0));
        pointList.add(new Point2D(3087.0, 2850.0));
        pointList.add(new Point2D(1444.0, 1032.0));
        */

        //listens mouse clicks, add points to the list
        //and draw convex hull
        while (true) {
            if (StdDraw.isMousePressed()) {
                //System.out.println("mouse trigger");
                //user clicks, add point to the list
                int x = (int) (Math.round(StdDraw.mouseX()));
                int y = (int) (Math.round(StdDraw.mouseY()));
                pointList.add(new Point2D(x, y));
                //System.out.println("x = "+x);
                //System.out.println("y = "+y);
                // compute convex hull
                ConvexHullBuilder convexBuilder = new ConvexHullBuilder(pointList);

                StdDraw.clear();

                drawConvexHull(pointList, convexBuilder.hull());

            }
            StdDraw.show();
            StdDraw.pause(20);
        }

    }

    //plot convex hull
	private static void drawConvexHull(ArrayList<Point2D> pointList, 
									   Iterable<Point2D> hull) {
		drawPoints(pointList);  //black points

		drawHullPoints(hull);	//red points
		
		drawHullLines(hull);	//blue lines
	}

	// plot black points
	private static void drawPoints(ArrayList<Point2D> pointList) {
        int n = pointList.size(); //number of points
		
        StdDraw.setPenRadius(.01);
        StdDraw.setPenColor(StdDraw.BLACK);

        for (int i = 0; i < n; i++)
            pointList.get(i).draw();
	}
	
	// plot red hull points 
	private static void drawHullPoints(Iterable<Point2D> hull) {
        StdDraw.setPenColor(StdDraw.RED);
        for (Point2D p : hull)
            p.draw();
	}
	
	// plot blue hull line segments 
	private static void drawHullLines(Iterable<Point2D> hull) {
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BLUE);
        Point2D prev = null;
        for (Point2D p : hull) {
            if (prev != null) 
            	prev.drawTo(p);
            prev = p;
        }
        // connect first and last points
        for (Point2D p : hull) {
            prev.drawTo(p);
            break;
        }
	}

}