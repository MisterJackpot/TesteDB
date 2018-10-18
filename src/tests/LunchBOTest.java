package tests;

import org.junit.Before;
import org.junit.Test;
import sample.LunchBO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class LunchBOTest {

    private ArrayList<String> restaurantesAux;
    private HashMap<String,Integer> votos;
    private LunchBO lunchBO;
    private LocalDate date;

    @Before
    public void setUp() throws Exception {
        restaurantesAux = new ArrayList();
        votos = new HashMap<>();
        lunchBO = new LunchBO();
        date = LocalDate.of(2018,10,18);


        restaurantesAux.add("Palatus");
        restaurantesAux.add("Predio 32");
        restaurantesAux.add("Novo Sabor");
        restaurantesAux.add("Subway");
        restaurantesAux.add("Madero Container");
        restaurantesAux.add("Severo Garage");

        votos.put("Palatus",3);
        votos.put("Subway",2);
        votos.put("Novo Sabor",1);
        votos.put("Predio 32",1);
    }

    @Test
    public void calculateVotesEquals() {
        assertEquals("Palatus",lunchBO.calculateVotes(votos,restaurantesAux));
        restaurantesAux.remove(lunchBO.calculateVotes(votos,restaurantesAux));
        assertEquals("Subway",lunchBO.calculateVotes(votos,restaurantesAux));
    }

    @Test
    public void calculateVotesNotEquals() {
        assertEquals("Palatus",lunchBO.calculateVotes(votos,restaurantesAux));
        restaurantesAux.remove(lunchBO.calculateVotes(votos,restaurantesAux));
        assertNotEquals("Palatus",lunchBO.calculateVotes(votos,restaurantesAux));
    }

    @Test
    public void getWeekEquals(){
        assertEquals(42,lunchBO.getWeek(date));
    }
}