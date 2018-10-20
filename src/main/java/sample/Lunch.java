package sample;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Lunch {
    private LocalDate date;
    private HashMap<String,Integer> votes;
    private ArrayList<String> missing;
    private String winner;

    public Lunch(LocalDate date, ArrayList missing, HashMap votes){
        this.date = date;
        this.missing = missing;
        this.votes = votes;
        this.winner = "";
    }

    public Lunch(LocalDate dt){
        this.date = dt;
        this.missing = new ArrayList<>();
        this.votes = new HashMap<>();
        this.winner = "";
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public HashMap<String, Integer> getVotes() {
        return votes;
    }

    public void setVotes(HashMap<String, Integer> votes) {
        this.votes = votes;
    }

    public ArrayList<String> getMissing() {
        return missing;
    }

    public void setMissing(ArrayList<String> missing) {
        this.missing = missing;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

}
