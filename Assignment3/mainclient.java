import java.util.Scanner;
import java.io.*;
import java.util.*;

public class mainclient {

    static int binarySearch(ArrayList <String[]> arr, String x) {
        int l = 0, r = arr.size() - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            int res = x.compareTo(arr.get(m)[0]);
            // Check if x is present at mid
            if (res == 0)
                return m;
            // If x greater, ignore left half
            if (res > 0)
                l = m + 1;
            // If x is smaller, ignore right half
            else
                r = m - 1;
        }
        return -1;
    }


    public static void main(String[] args) throws Exception {

        int count = 0;
        Hashtable<String, RedBlackBST> hash = new Hashtable<>();

        // The text file is scanned from the src directory
        Scanner sc = new Scanner(new BufferedReader(new FileReader("movies.csv")));
        while (sc.hasNextLine()) {             //scan each line
            String line = sc.nextLine();
            String[] splitString = line.split(",",-1);

            //Create a new redBlackTree in the hash for each category per instructions
            if (count==0){
                hash.put("year", new RedBlackBST<Integer,ArrayList<String[]>>());
                hash.put("imbd_score",new RedBlackBST<Double,ArrayList<String[]>>());
                hash.put("content_rating",new RedBlackBST<String,ArrayList<String[]>>());
                hash.put("language",new RedBlackBST<String,ArrayList<String[]>>());
            }

            //Retreive year, score, content_rating, and language from each line
            if (count !=0 && 0 < splitString[splitString.length-2].length() ) {
                int year = Integer.parseInt(splitString[splitString.length-2]);
                double score = Double.parseDouble(splitString[splitString.length-1]);
                String contentRating = splitString[splitString.length-3];
                String language = splitString[splitString.length-5];

                //Print values
                /* System.out.println(splitString[0]+"  year = "+ year);
                System.out.println(splitString[0]+"  score = "+ score);
                System.out.println(splitString[0]+"  contentRating = "+ contentRating);
                System.out.println(splitString[0]+"  language = "+ language);
                */

                // insert for Year
                if (hash.get("year").get(year)==null){
                    ArrayList<String[]> yearList = new ArrayList<String[]>();
                    yearList.add(splitString);
                    hash.get("year").put(year,yearList);
                }else{
                    ArrayList<String[]> yearList = (ArrayList<String[]>) hash.get("year").get(year);
                    yearList.add(splitString);
                    hash.get("year").put(year,yearList);
                }

                // insert for imbd_score
                if (hash.get("imbd_score").get(score)==null){
                    ArrayList<String[]> scoreList = new ArrayList<String[]>();
                    scoreList.add(splitString);
                    hash.get("imbd_score").put(score,scoreList);
                }else{
                    ArrayList<String[]> scoreList = (ArrayList<String[]>) hash.get("imbd_score").get(score);
                    scoreList.add(splitString);
                    hash.get("imbd_score").put(score,scoreList);
                }

                // insert for contentRating
                if (hash.get("content_rating").get(contentRating)==null){
                    ArrayList<String[]> contentList = new ArrayList<String[]>();
                    contentList.add(splitString);
                    hash.get("content_rating").put(contentRating,contentList);
                }else{
                    ArrayList<String[]> contentList = (ArrayList<String[]>) hash.get("content_rating").get(contentRating);
                    contentList.add(splitString);
                    hash.get("content_rating").put(contentRating,contentList);
                }

                // insert for language
                if (hash.get("language").get(language)==null){
                    ArrayList<String[]> languageList = new ArrayList<String[]>();
                    languageList.add(splitString);
                    hash.get("language").put(language,languageList);
                }else{
                    ArrayList<String[]> languageList = (ArrayList<String[]>) hash.get("language").get(language);
                    languageList.add(splitString);
                    hash.get("language").put(language,languageList);
                }
            }
            count++;
        }

        //Retreive inputs from user console
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter Year:");
        String yearStr = keyboard.next();
        char yearChar = yearStr.charAt(0);

        System.out.print("Enter Score:");
        String scoreStr = keyboard.next();
        char scoreChar = scoreStr.charAt(0);

        System.out.print("Enter Language:");
        String langInput = keyboard.next();
        char langChar = langInput.charAt(0);

        System.out.print("Enter Rating:");
        String ratingInput = keyboard.next();
        char ratingChar = ratingInput.charAt(0);
        System.out.println("Results (Movies -> year:"+yearStr+" score:"+scoreStr+")");


        //----------Search Movies portion----------------
        ArrayList<String[]> finalResult = new ArrayList<String[]>();

        //ignore year but not score
        if (yearChar == '-' && scoreChar != '-') {
            double scoreInput = Double.parseDouble(scoreStr);
            ArrayList<String[]> resultscore= (ArrayList<String[]>) hash.get("imbd_score").get(scoreInput);
            for (int i = 0; i < resultscore.size(); i++) {
                    finalResult.add(resultscore.get(i));
            }

        //ignore score but not year
        } else if (scoreChar == '-' && yearChar != '-') {
            int yearInput = Integer.parseInt(yearStr);
            ArrayList<String[]> resultscore= (ArrayList<String[]>) hash.get("year").get(yearInput);
            for (int i = 0; i < resultscore.size(); i++) {
                finalResult.add(resultscore.get(i));
            }

        //include score and year match results using binary search
        } else if (scoreChar != '-' && yearChar != '-'){
            int yearInput = Integer.parseInt(yearStr);
            double scoreInput = Double.parseDouble(scoreStr);
            ArrayList<String[]> resultyear= (ArrayList<String[]>) hash.get("year").get(yearInput);
            ArrayList<String[]> resultscore= (ArrayList<String[]>) hash.get("imbd_score").get(scoreInput);
            for (int i = 0; i < resultyear.size(); i++) {
                int foundIndex = binarySearch(resultscore, resultyear.get(i)[0]);

                if (foundIndex > 0) {
                    finalResult.add(resultyear.get(i));
                }
            }
        }

        // include language match results if ignore not selected
        if (langChar != '-'){
            ArrayList<String[]> resultLang= (ArrayList<String[]>) hash.get("language").get(langInput);
            for (int i=finalResult.size()-1;i>=0;i--){
                if (resultLang==null) {
                    finalResult = null;
                }else {
                    int foundIndex = binarySearch(resultLang, finalResult.get(i)[0]);
                    if (foundIndex < 0) {
                        finalResult.remove(finalResult.get(i));
                    }
                }
                //System.out.println("  resultLang = "+ resultLang.get(i)[0]);
            }
        }

        // include rating if ignore not selected
        if (ratingChar != '-'){
            ArrayList<String[]> resultContent= (ArrayList<String[]>) hash.get("content_rating").get(ratingInput);
            for (int i=finalResult.size()-1;i>=0;i--){
                if (resultContent==null) {
                    finalResult = null;
                }else {
                    int foundIndex = binarySearch(resultContent, finalResult.get(i)[0]);
                    if (foundIndex < 0) {
                        finalResult.remove(finalResult.get(i));
                    }
                }
                //System.out.println("  resultContent = "+ resultContent.get(i)[0]);
            }
        }


        //Print final results
        if (finalResult==null || finalResult.size()<1){
            System.out.println("  No matches found ");
        }else {
            for (int i = 0; i < finalResult.size(); i++) {
                System.out.println("-----------------------------" );
                System.out.println("id:" + finalResult.get(i)[0]);
                System.out.println("color:" + finalResult.get(i)[1]);
                System.out.println("title:" + finalResult.get(i)[2]);
                System.out.println("duration:" + finalResult.get(i)[3]);
                System.out.println("director_name:" + finalResult.get(i)[4]);
                System.out.println("act1:" + finalResult.get(i)[5]);
                System.out.println("act2:" + finalResult.get(i)[6]);
                System.out.println("act3:" + finalResult.get(i)[7]);
                System.out.println("movie_imdb_link:" + finalResult.get(i)[8]);
                System.out.println("language:" + finalResult.get(i)[9]);
                System.out.println("country:" + finalResult.get(i)[10]);
                System.out.println("content_rating:" + finalResult.get(i)[11]);
                System.out.println("title_year:" + finalResult.get(i)[12]);
                System.out.println("imdb_score:" + finalResult.get(i)[13]);
                System.out.println("-----------------------------" );
            }
        }

    }
}
