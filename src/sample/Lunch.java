package sample;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Lunch {
    private LocalDate date;
    private HashMap<String,Integer> votos;
    private ArrayList<String> faltam;
    private String winner;

    public Lunch(LocalDate date, ArrayList faltam, HashMap votos){
        this.date = date;
        this.faltam = faltam;
        this.votos = votos;
        this.winner = "";
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public HashMap<String, Integer> getVotos() {
        return votos;
    }

    public void setVotos(HashMap<String, Integer> votos) {
        this.votos = votos;
    }

    public ArrayList<String> getFaltam() {
        return faltam;
    }

    public void setFaltam(ArrayList<String> faltam) {
        this.faltam = faltam;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}