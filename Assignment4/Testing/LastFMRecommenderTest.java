import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import static org.junit.jupiter.api.Assertions.*;

public class LastFMRecommenderTest {

    @Test //Passing test case for displaying list of friends
    public void listFriendsUser1481() throws FileNotFoundException {
        LastFMRecommender LFMR = new LastFMRecommender();
        String expected = "1132 1087 925 817 5 ";
        String listOfFriends = LFMR.listFriends(1481);
        assertEquals(expected, listOfFriends);
    }

    @Test //Passing test case for displaying list of friends
    public void listFriendsUser2019() throws FileNotFoundException {
        LastFMRecommender LFMR = new LastFMRecommender();
        String expected = "1632 1177 1151 298 ";
        String listOfFriends = LFMR.listFriends(2019);
        assertEquals(expected, listOfFriends);
    }

    @Test //Passing test case for common list of friends
    public void commonFriends11_12() throws FileNotFoundException {
        LastFMRecommender LFMR = new LastFMRecommender();
        String expected = "2004 684 ";
        String listOfFriends = LFMR.commonFriends(11,12);
        assertEquals(expected, listOfFriends);
    }

    @Test //test case for no common list of friends
    public void commonFriends2_3() throws FileNotFoundException {
        LastFMRecommender LFMR = new LastFMRecommender();
        String expected = "No matches were found";
        String listOfFriends = LFMR.commonFriends(2,3);
        assertEquals(expected, listOfFriends);
    }

    @Test //test case no artists listened by both user 2 & 3
    public void listArtist2_3() throws FileNotFoundException {
        LastFMRecommender LFMR = new LastFMRecommender();
        String expected = "No matches were found";
        String listOfFriends = LFMR.listArtists(2,3);
        assertEquals(expected, listOfFriends);
    }

    @Test //test case artists listened by both user 11 & 12
    public void listArtist11_12() throws FileNotFoundException {
        LastFMRecommender LFMR = new LastFMRecommender();
        String expected = "466 344 300 288 230 ";
        String listOfArtists = LFMR.listArtists(11,12);;
        assertEquals(expected, listOfArtists);
    }

    @Test //test case list top 10 artists by greatest weight
    public void top10() throws FileNotFoundException {
        LastFMRecommender LFMR = new LastFMRecommender();
        String expected = "289 72 89 292 498 67 288 701 227 300 ";
        String top10Artist = LFMR.listTop10();
        assertEquals(expected, top10Artist);
    }

    @Test //test case list top 10 artists of user 20 and their friends
    public void recommend10user20() throws FileNotFoundException {
        LastFMRecommender LFMR = new LastFMRecommender();
        String expected = "141 743 744 745 512 746 747 748 749 762 ";
        String top10Artist = LFMR.recommend10(20);
        assertEquals(expected, top10Artist);
    }

    @Test //test case list top 10 artists of user 2 and their friends
    public void recommend10user2() throws FileNotFoundException {
        LastFMRecommender LFMR = new LastFMRecommender();
        String expected = "51 72 67 1246 1104 511 159 1001 993 55 ";
        String top10Artist = LFMR.recommend10(2);
        assertEquals(expected, top10Artist);
    }

}
