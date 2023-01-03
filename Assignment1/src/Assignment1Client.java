import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;


public class Assignment1Client {

    public static void main(String[] args) throws Exception{
        int count = 0;
        List<int[]> input = new ArrayList<>();

        // The text file is scanned from the src directory, change to test other text files
        Scanner sc = new Scanner(new BufferedReader(new FileReader("File-1.txt")));
        while(sc.hasNextLine()) {             //scan each line
           String line = sc.nextLine();
           String[] splitString =  line.split("\t");   //split each number that's tabbed
           int[] tempArray = new int[splitString.length];
           // add numbers to input array
           for (int i = 0; i < splitString.length; i++) {
                tempArray[i]=Integer.parseInt(splitString[i]);
                count++;
            }
            input.add(tempArray);
        }

        WeightedQuickUnion wqu = new WeightedQuickUnion(count);
        wqu.connectWithUnion(input); //make connections
        wqu.printArrays();
        System.out.println(wqu.pathCompFind());  //determine drainage

    }
}
