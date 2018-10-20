package sample;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static org.junit.Assert.*;

public class LunchBOTest {

    private ArrayList<String> restaurantsAux;
    private ArrayList<String> workersAux;
    private HashMap<String,Integer> votes;
    private HashMap<String,Lunch> days;
    private LunchBO lunchBO;
    private LocalDate date;
    private LocalDate today;
    private Lunch lunch;

    @Before
    public void setUp() throws Exception {
        restaurantsAux = new ArrayList<>();
        workersAux = new ArrayList<>();
        votes = new HashMap<>();
        days = new HashMap<>();
        lunchBO = new LunchBO();
        date = LocalDate.of(2018,10,18);
        today = LocalDate.now();
        lunch = new Lunch(date);


        restaurantsAux.add("Palatus");
        restaurantsAux.add("Predio 32");
        restaurantsAux.add("Novo Sabor");
        restaurantsAux.add("Subway");
        restaurantsAux.add("Madero Container");
        restaurantsAux.add("Severo Garage");

        workersAux.add("Joca");
        workersAux.add("Julia");
        workersAux.add("Braga");

        votes.put("Palatus",3);
        votes.put("Subway",2);
        votes.put("Novo Sabor",1);
        votes.put("Predio 32",1);

        lunch.setMissing(workersAux);
        lunch.setVotes(votes);
    }

    @Test
    public void calculateVotesEquals() {
        assertEquals("Palatus",lunchBO.calculateVotes(votes, restaurantsAux));
        restaurantsAux.remove(lunchBO.calculateVotes(votes, restaurantsAux));
        assertEquals("Subway",lunchBO.calculateVotes(votes, restaurantsAux));
    }

    @Test
    public void calculateVotesNotEquals() {
        assertEquals("Palatus",lunchBO.calculateVotes(votes, restaurantsAux));
        restaurantsAux.remove(lunchBO.calculateVotes(votes, restaurantsAux));
        assertNotEquals("Palatus",lunchBO.calculateVotes(votes, restaurantsAux));
    }

    @Test
    public void getWeekEquals(){
        assertEquals(42,lunchBO.getWeek(date));
    }

    @Test
    public void getWeekToday(){
        assertEquals(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR),lunchBO.getWeek(today));
    }

    @Test
    public void computeVote(){
        lunch = lunchBO.computeVote(lunch,"Joca","Palatus");
        assertNotNull(lunch);
        assertEquals(4,lunch.getVotes().get("Palatus").intValue());
        lunch = lunchBO.computeVote(lunch,"Joca","Palatus");
        assertNull(lunch);
    }


}