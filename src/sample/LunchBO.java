package sample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class LunchBO {

    public LunchBO() {
    }

    public String calculateVotes(HashMap<String,Integer> votosAux, ArrayList<String> restAux){
        String resultado = "";
        int aux = 0;

        for (Map.Entry<String, Integer> voto : votosAux.entrySet()) {
            if (voto.getValue() > aux) {
                resultado = voto.getKey();
                aux = voto.getValue();
            }
        }

        if(restAux.contains(resultado)) {
            return resultado;
        }else{
            votosAux.remove(resultado);
            return calculateVotes(votosAux,restAux);
        }
    }

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
}
