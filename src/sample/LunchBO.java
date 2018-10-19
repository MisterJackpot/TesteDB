package sample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class LunchBO {

    public LunchBO() {
    }

    /**
     * @param votesAux A HashMap containing <Restaurant,Number of Votes>
     * @param restAux A ArrayList containing restaurants that can win
     * @return The winner Restaurant
     */
    public String calculateVotes(HashMap<String,Integer> votesAux, ArrayList<String> restAux){
        System.out.println("VOTOS:" + votesAux + "\n Restaurantes" + restAux);
        String resultado = "";
        int aux = 0;

        for (Map.Entry<String, Integer> voto : votesAux.entrySet()) {
            if (voto.getValue() > aux) {
                resultado = voto.getKey();
                aux = voto.getValue();
            }
        }
        if(votesAux.isEmpty()) return "Erro";
        if(restAux.contains(resultado)) {
            return resultado;
        }else{
            votesAux.remove(resultado);
            return calculateVotes(votesAux,restAux);
        }
    }

    /**
     * @param date Some date
     * @return The week of the year of the date
     */
    public int getWeek(LocalDate date){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt = defaultFormat.parse(date.toString());
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
     * @param lunch The actual Lunch
     * @param worker The worker voting
     * @param restaurant The restaurant voted
     * @return The object Lunch updated with the new vote
     */
    public Lunch computeVote(Lunch lunch,String worker, String restaurant){
        if(lunch.getMissing().contains(worker)) {
            HashMap<String,Integer> votes = lunch.getVotes();
            if(lunch.getVotes().containsKey(restaurant)){
                votes.replace(restaurant,votes.get(restaurant)+1);
            }else{
                votes.put(restaurant,1);
            }
            lunch.setVotes(votes);
            ArrayList<String> missing = lunch.getMissing();
            missing.remove(worker);
            lunch.setMissing(missing);

        }else{
            return null;
        }
        return lunch;
    }
}
