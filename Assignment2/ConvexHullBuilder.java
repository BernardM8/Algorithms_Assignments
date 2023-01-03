import edu.princeton.cs.algs4.Point2D;
import java.util.ArrayList;



public class ConvexHullBuilder {

    private ArrayList<Point2D> pointList;

    public ConvexHullBuilder( ArrayList<Point2D> list){
        pointList = list;
    }

    //Prints full array list (O(n) time complexity)
    public void printList(ArrayList<Point2D> list){
        for (int index = 0; index < list.size(); index++) {
            System.out.println("List.get(index) = " + list.get(index) + "  index = " + index);
        }
    }

    //Binary search of array list (O log(n) time complexity)
    boolean binarySearch(ArrayList<Point2D> arr, Point2D point)
    {
        int left = 0, right = arr.size() - 1;
        while (left <= right)
        {
            int mid = left + (right - left) / 2;
            // Check if point is present at mid
            if (arr.get(mid).y() == point.y() && arr.get(mid).x() == point.x())
                return true;
            // If point greater, ignore left half
            if (arr.get(mid).y() < point.y())
                left = mid + 1;
                // If point is smaller, ignore right half
            else
                right = mid - 1;
        }
        // if we reach here, then element was not present
        return false;
    }


    // copies list with duplicates removed (O nlog(n) time complexity)
    public ArrayList<Point2D> removeDuplicates(ArrayList<Point2D> list){
        ArrayList<Point2D> resultList = new ArrayList<Point2D>();
        for (int index = 0; index < list.size(); index++){
            //add point to result list only if there are no duplicates
            if (!binarySearch(resultList,list.get(index))) {
                resultList.add(list.get(index));
            }
        }
        return resultList;
    }


    //find min y-coordinate and leftmost point (O(n) time complexity)
    public ArrayList<Point2D> sortXValues(ArrayList<Point2D> list){
        for (int index = 0; index < list.size()-1; index++) {
            // check if y values are the same and the higher element x is smaller than the lower element
            if (list.get(index).y() == list.get(index+1).y() && list.get(index).x() > list.get(index+1).x()) {
                //Swap so lower x is on lower index
                Point2D temp = list.get(index);
                Point2D minPoint = list.get(index+1);
                list.set(index+1, temp);
                list.set(index, minPoint);
            }
        }
        return list;
    }


    public Iterable<Point2D> hull(){

        //Sort list by Y order
        pointList.sort( Point2D.Y_ORDER);

        // Copy list with duplicates removed
        ArrayList<Point2D> list =removeDuplicates(pointList);

        //sort the x values for same y, this way min y and leftmost point is index 0
        ArrayList<Point2D> resultList =sortXValues(list);
        /*System.out.println("--------Sorted by Y--------");
        printList(resultList);*/

        //Sort Point2D list by polar degrees
        resultList.sort( resultList.get(0).polarOrder());
       /*System.out.println("--------Sorted by PolarOrder--------");
        printList(resultList);*/

        int ccw=0;
        if (resultList.size()>=3) {
            int index = 0;
            while (index < resultList.size()-2) {
                Point2D a = resultList.get(index);
                Point2D b = resultList.get(index + 1);
                Point2D c = resultList.get(index + 2);

                ccw = resultList.get(index).ccw(a, b, c); //check if point a,b,c is counter clockwise

                /*System.out.println("\n"+"Index = "+index);
                System.out.println("Checking Points = "+ resultList.get(index)+" "+resultList.get(index+1)+" "+resultList.get(index+2)) ;
                System.out.print("ccw = "+ccw);*/
                if (ccw <= 0) { //if points abc are not counter clockwise remove point b
                    //System.out.print("     Removed = "+resultList.get(index+1));
                    resultList.remove(index+1);
                    index=index-2;
                }
                index++;
                //System.out.println("");
                if (index<0){index=0;}
            }
            //check final points with initial
            ccw = resultList.get(index).ccw(resultList.get(resultList.size()-2), resultList.get(resultList.size()-1), resultList.get(0));
            /*System.out.println("Checking Final Points = "+ resultList.get(resultList.size()-2)+" "+resultList.get(resultList.size()-1)+" "+resultList.get(0)) ;
            System.out.print("ccw = "+ccw);*/
            if (ccw <= 0) {
                //System.out.print("     Removed = " + resultList.get(resultList.size() - 1));
                resultList.remove(resultList.size() - 1);
            }
        }
        System.out.println("\n"+"resultList ");
        printList(resultList);
        return resultList;
    }


}