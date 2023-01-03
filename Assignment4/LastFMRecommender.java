import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.util.Arrays;

public class LastFMRecommender {

    /** Fields */
    Digraph graphFriends;
    EdgeWeightedDigraph graphArtists;
    HashMap totalArtistWeight;
    HashMap artistInfo;


    /** default constructor */
    public LastFMRecommender() throws FileNotFoundException {
        Digraph graphFriend = new Digraph(2101);
        EdgeWeightedDigraph graphArtist = new EdgeWeightedDigraph(20000);
        HashMap<Integer, Integer> totalArtistWeight = new HashMap<Integer, Integer>();
        HashMap<Integer, String> artistInfo = new HashMap<Integer, String>();

        // scan user_artist and store user, artist, and weight into EdgeWeightedDigraph
        int count = 0;
        Scanner scUserArtists = new Scanner(new BufferedReader(new FileReader("user_artists.dat")));
        while (scUserArtists.hasNextLine()) {             //scan each line O(n)
            String line = scUserArtists.nextLine();
            String[] splitString = line.split("\t", -1);
            if (count!=0) {
                int userID = Integer.parseInt(splitString[0]);
                int artistID = Integer.parseInt(splitString[1]);
                int weight = Integer.parseInt(splitString[2]);
                DirectedEdge DE = new DirectedEdge(userID,artistID,weight);
                graphArtist.addEdge(DE);// O(1)

                //add total weight of artist store into hashmap totalArtistWeight
                if (totalArtistWeight.get(artistID) != null && totalArtistWeight.containsKey(artistID)){ // O(1)
                    int weightValue = (Integer)totalArtistWeight.get(artistID); // O(1)
                    weightValue+=weight;
                    totalArtistWeight.put(artistID,weightValue);// O(1)
                }else{
                    totalArtistWeight.put(artistID,weight);// O(1)
                }
            }
            count++;
        }

        // scan user_friends and store user, and friend into Digraph
        count=0;
        Scanner scFriends = new Scanner(new BufferedReader(new FileReader("user_friends.dat")));
        while (scFriends.hasNextLine()) {            //scan each line O(n)
            String line = scFriends.nextLine();
            String[] splitString = line.split("\t", -1);
            if (count!=0) {
                int userID = Integer.parseInt(splitString[0]);
                int friendID = Integer.parseInt(splitString[1]);
                graphFriend.addEdge(userID,friendID); // O(1)
            }
            count++;
        }

        // scan artists and store artist name, url, and picture url into Hashmap
        count=0;
        Scanner scArtists = new Scanner(new BufferedReader(new FileReader("artists.dat")));
        while (scArtists.hasNextLine()) {             //scan each line O(n)
            String line = scArtists.nextLine();
            String[] splitString = line.split("\t", -1);
            if (count!=0) {
                int artistID = Integer.parseInt(splitString[0]);
                String name = splitString[1];
                String url = splitString[2];
                String pictureURL = splitString[3];
                String info = name + "\t"+url + "\t"+ pictureURL;
                artistInfo.put(artistID,info); // O(1)
            }
            count++;
        }
        this.graphFriends=graphFriend;
        this.graphArtists=graphArtist;
        this.totalArtistWeight=totalArtistWeight;
        this.artistInfo=artistInfo;
    }


    /** prints the list of friends of the given user  O(n) time complexity */
    public String listFriends(int user){
        String result = "";
        System.out.print('\n'+"Friends list of user "+user+": ");
        Iterator itr = graphFriends.adj(user).iterator();
        while(itr.hasNext()){ // O(n)
            Object element =itr.next();
            System.out.print(element+" ");
            result += String.valueOf(element) + " ";
        }
        System.out.println();
        return result;
    }


    /** prints the user1’s friends in common with user2 */
    public String commonFriends(int user1, int user2){
        String resultString = "";
        int temp[] = new int [2200];
        int result[] = new int [2200];

        //get friends of user1 store into array temp & sort
        int count = 0;
        Iterator itrUser1 = graphFriends.adj(user1).iterator();
        while(itrUser1.hasNext()){  //O(n)
            int element1 =(int) itrUser1.next();
            temp[count] =element1; //O(1)
            count++;
        }
        Arrays.sort(temp); //O(nlogn)

        //get friends of user2, search for match with temp array, if match exists add to result array (nlogn)
        count = 0;
        Iterator itrUser2 = graphFriends.adj(user2).iterator();
        while(itrUser2.hasNext()){  //O(n)
            int element2 =(int) itrUser2.next();
            int searchResult= binarySearch(temp, element2); //O(log n)
            if (searchResult>0){
                result[count] = temp[searchResult];//O(1)
                count++;
            }
        }

        // Print result and concatenate string result to return
        if (count>0){
            System.out.print('\n'+"Common friends of "+user1+" & "+user2+":  ");
            for (int i=0; i < count; i++ ){ //O(n)
                System.out.print(result[i]+"  ");
                resultString+= String.valueOf(result[i])+" ";
            }
            System.out.println('\n');
        }else{
            System.out.println('\n'+"No matches found for common friends "+user1+" & "+user2);
            resultString = "No matches were found";
        }
        return resultString;
    }


    /** prints the list of artists listened by both users */
    public String listArtists(int user1, int user2){
        String resultString = "";
        int temp[] = new int [20000];
        int result[] = new int [20000];

        //get artists of user1 store into array temp & sort
        int count = 0;
        Iterator itrUser1 = graphArtists.adj(user1).iterator();
        while(itrUser1.hasNext()){
            DirectedEdge element1 =(DirectedEdge)itrUser1.next();
            temp[count] = element1.to();
            count++;
        }
        Arrays.sort(temp); //O(nlogn)

        //get artists of user2, search for match with temp array, if match exists add to result array O(nlogn)
        count =0;
        Iterator itrUser2 = graphArtists.adj(user2).iterator();
        while(itrUser2.hasNext()){ //O(n)
            DirectedEdge element2 =(DirectedEdge)itrUser2.next();
            int searchResult= binarySearch(temp, element2.to()); //O(log n)
            if (searchResult>0){
                result[count]=temp[searchResult];
                count++;
            }
        }

        // Print results and concatenate string result to return
        if (count>0){
            System.out.println('\n'+"Artists listened by both users "+user1+" & "+user2+":  ");
            for (int i=0; i < count; i++ ){//O(n)
                System.out.print(result[i]+"  ");
                resultString+= String.valueOf(result[i])+" ";
                System.out.println(artistInfo.get(result[i]));
            }
            System.out.println("  ");
        } else {
            System.out.println("No matches found for artists listened by "+user1+" & "+user2);
            resultString = "No matches were found";
        }
        return resultString;
    }


    /** prints the list of top 10 most popular artists listened by all users */
    public String listTop10(){
        Map<Integer, int[]> popularArtist = new TreeMap<Integer, int[]>(Collections.reverseOrder());//Map stores weight (listens) as key artist as value
        String resultString = "";

        //get total artist weights and add to tree map with weight being key in descending order
        for(Object obj: this.totalArtistWeight.keySet()) {//O(n)
            int key = (Integer)obj;
            int weightVal= (int) totalArtistWeight.get(key);//O(1)
            //if duplicate popularity weight exist, add artist to array in value
            if (popularArtist.containsKey(weightVal)){ //O(log n)
                int[] tmp = popularArtist.get(weightVal); //O(log n)
                int[] copy = Arrays.copyOf(tmp, tmp.length+1);
                copy[tmp.length-1] = key;//O(1)
                popularArtist.put(weightVal, copy); //O(log n)
            // if no duplicates of popularity weight exist add artist to new array into value
            }else{
                int[] tmp = new int[1];
                tmp[0]=key;//O(1)
                popularArtist.put(weightVal, tmp); //O(log n)
            }
        }

        // Print result and concatenate result string to return
        System.out.println('\n'+"List of top 10: ");
        int count = 0;
        for(Integer key: popularArtist.keySet()) {//O(log n)
            int[] tmp = popularArtist.get(key);//O(log n)
            for (int j=0;j<tmp.length;j++) {
                if (count>=10) {
                    break;
                }else {
                    int val = (Integer) tmp[j];
                    resultString += String.valueOf(val) + " ";
                    System.out.print(val + " ");
                    System.out.println(artistInfo.get(val));
                    count++;
                }
            }
        }
        System.out.println("  ");
        return resultString;
    }


    /** recommends 10 most popular artists listened by the given user and his/her friends. */
    public String recommend10(int user){
        String resultString = "";
        int[] friends = new int [2200];
        friends[0] = user;
        int count = 1;

        //get friends of user, store into array
        Iterator itrUser = graphFriends.adj(user).iterator();
        while(itrUser.hasNext()){//O(n)
            int element1 =(int) itrUser.next();
            friends[count] = element1;
            count++;
        }

        //get artists from each friend and store into tmp Hashmap
        HashMap<Integer, Double> tmp = new HashMap<Integer, Double>();
        for (int i=0; i < count; i++ ){
            Iterator itr = this.graphArtists.adj(friends[i]).iterator(); //get adjacency of user and each friend
            while(itr.hasNext()){//O(n)
                DirectedEdge element1 =(DirectedEdge) itr.next();
                int artist = element1.to();
                double weight = element1.weight();
                //add weight if artist already exists
                if (tmp.containsKey(artist)){// O(log n)
                    weight += tmp.get(artist);//O(1)
                    tmp.put(artist,weight);//O(1)
                // put weight for new artist in tmp
                }else{
                    tmp.put(artist,weight);//O(1)
                }
            }
        }

        // Fill result map from tmp with total weight as key in descending order
        Map<Double, Integer> resultArtist = new TreeMap<Double, Integer>(Collections.reverseOrder());
        for(Integer key: tmp.keySet()) {//O(n)
            Double weightVal= tmp.get(key);//O(1)
            resultArtist.put(weightVal,key);//O(log n)
        }

        // Print result and concatenate string result to return
        System.out.println('\n'+"recommend 10 of user "+user+" and friends: ");
        count = 0;
        for(Double key: resultArtist.keySet()) {//O(n)
            if (count>=10) {
                break;
            }else {
                int val = (Integer) resultArtist.get(key);//O(log n)
                System.out.print(val + "->");
                System.out.print(key + " ");
                System.out.println(artistInfo.get(val));
                resultString += String.valueOf(val) + " ";
                count++;
            }
        }
        System.out.println("  ");
        return resultString;
    }


    /**  basic binary search method. O(log n)*/
    static int binarySearch(int[] arr, int x) {
        int first = 0;
        int last = arr.length - 1;
        while (first <= last) {
            int mid = first + (last - first) / 2;
            // Check if x is present at mid
            if (arr[mid] == x)
                return mid;
            // If x greater, ignore left half
            if (arr[mid] < x)
                first = mid + 1;
                // If x is smaller, ignore right half
            else
                last = mid - 1;
        }
        return -1;
    }
}
