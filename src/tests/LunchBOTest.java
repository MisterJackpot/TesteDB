package tests;

import org.junit.Before;
import org.junit.Test;
import sample.Lunch;
import sample.LunchBO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static org.junit.Assert.*;

public class LunchBOTest {

    private ArrayList<String> restaurantesAux;
    private ArrayList<String> profissionaisAux;
    private HashMap<String,Integer> votos;
    private HashMap<String,Lunch> days;
    private LunchBO lunchBO;
    private LocalDate date;
    private LocalDate today;

    @Before
    public void setUp() throws Exception {
        restaurantesAux = new ArrayList();
        profissionaisAux = new ArrayList<>();
        votos = new HashMap<>();
        days = new HashMap<>();
        lunchBO = new LunchBO();
        date = LocalDate.of(2018,10,18);
        today = LocalDate.now();


        restaurantesAux.add("Palatus");
        restaurantesAux.add("Predio 32");
        restaurantesAux.add("Novo Sabor");
        restaurantesAux.add("Subway");
        restaurantesAux.add("Madero Container");
        restaurantesAux.add("Severo Garage");

        profissionaisAux.add("Joca");
        profissionaisAux.add("Julia");
        profissionaisAux.add("Braga");

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

    @Test
    public void getWeekToday(){
        assertEquals(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR),lunchBO.getWeek(today));
    }

    @Test
    public void computeVote(){
        HashMap<String,Lunch> aux = days;
        aux = lunchBO.computeVote(date,profissionaisAux,votos,days,"Joca","Palatus");
        assertNotNull(aux);
        assertEquals(4,aux.get(date.toString()).getVotos().get("Palatus").intValue());
        aux = lunchBO.computeVote(date,profissionaisAux,votos,days,"Joca","Palatus");
        assertNull(aux);
    }


}