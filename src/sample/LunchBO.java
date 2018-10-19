package sample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class LunchBO {

    public LunchBO() {
    }

    /**
     * @param votosAux A HashMap containing <Restaurant,Number of Votes>
     * @param restAux A ArrayList containing restaurants that can win
     * @return The winner Restaurant
     */
    public String calculateVotes(HashMap<String,Integer> votosAux, ArrayList<String> restAux){
        System.out.println("VOTOS:" + votosAux + "\n Restaurantes" + restAux);
        String resultado = "";
        int aux = 0;

        for (Map.Entry<String, Integer> voto : votosAux.entrySet()) {
            if (voto.getValue() > aux) {
                resultado = voto.getKey();
                aux = voto.getValue();
            }
        }
        if(votosAux.isEmpty()) return "Erro";
        if(restAux.contains(resultado)) {
            return resultado;
        }else{
            votosAux.remove(resultado);
            return calculateVotes(votosAux,restAux);
        }
    }

    /**
     * @param data Some date
     * @return The week of the year of the date
     */
    public int getWeek(LocalDate data){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt = defaultFormat.parse(data.toString());
            cal.setTime(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * @param dt Some date
     * @return true if the date is past noon and false if it's not
     */
    public boolean isPastNoon(Date dt){
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        System.out.println(cal.get(Calendar.HOUR_OF_DAY));
        if(cal.get(Calendar.HOUR_OF_DAY) >= 12){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @param dt The date of voting
     * @param faltam The workers that donsen't vote yet
     * @param votos The current votes
     * @param days A HashMap containing <Days,Lunchs>
     * @param profissional The worker voting
     * @param restaurante The restaurant voted
     * @return A HashMap containing <Days,Lunchs> updated with the new vote or null if the worker already had voted this date
     */
    public HashMap<String,Lunch> computeVote(LocalDate dt, ArrayList<String> faltam, HashMap<String,Integer> votos, HashMap<String,Lunch> days,String profissional, String restaurante){
        if(faltam.contains(profissional)) {
            if(votos.containsKey(restaurante)){
                votos.replace(restaurante,votos.get(restaurante)+1);
            }else{
                votos.put(restaurante,1);
            }
            faltam.remove(profissional);
            Lunch aux = new Lunch(dt,faltam,votos);
            if(days.containsKey(dt.toString())){
                days.replace(dt.toString(),aux);
            }else{
                days.put(dt.toString(),aux);
            }
        }else{
            return null;
        }
        return days;
    }
}
