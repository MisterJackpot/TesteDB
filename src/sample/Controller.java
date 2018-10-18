package sample;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;


public class Controller {
    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    @FXML
    private ChoiceBox Profissional;

    @FXML
    private ChoiceBox Restaurante;

    @FXML
    private Label Resultado;

    @FXML
    private DatePicker Data;

    private ArrayList<String> profissionais;
    private ArrayList<String> restaurantes;
    private ArrayList<String> profissionaisAux;
    private ArrayList<String> restaurantesAux;
    private HashMap<String,Integer> votos;
    private HashMap<String,Lunch> days;
    private HashMap<Integer,ArrayList> week;


    public Controller()
    {
        restaurantes = new ArrayList();
        profissionais = new ArrayList();
        profissionaisAux = new ArrayList();
        restaurantesAux = new ArrayList();
        votos = new HashMap<>();
        days = new HashMap<>();
        week = new HashMap<>();
    }

    @FXML
    private void initialize()
    {
        profissionais.add("Juca");
        profissionais.add("João");
        profissionais.add("Luisa");
        profissionais.add("Ariel");
        Profissional.getItems().setAll(profissionais);

        restaurantes.add("Palatus");
        restaurantes.add("Predio 32");
        restaurantes.add("Novo Sabor");
        restaurantes.add("Subway");
        restaurantes.add("Madero Container");
        restaurantes.add("Severo Garage");
        Restaurante.getItems().setAll(restaurantes);

        profissionaisAux.addAll(profissionais);
        restaurantesAux.addAll(restaurantes);

        Data.setValue(LocalDate.now());


    }

    @FXML
    private void vote(){
        if(profissionaisAux.contains(Profissional.getValue())) {
            Resultado.setText("Voto Computado " + Profissional.getValue());
            if(votos.containsKey(Restaurante.getValue())){
                votos.replace(Restaurante.getValue().toString(),votos.get(Restaurante.getValue())+1);
            }else{
                votos.put(Restaurante.getValue().toString(),1);
            }
            profissionaisAux.remove(Profissional.getValue());
            if(days.containsKey(Data.getValue().toString())){
                Lunch aux = days.get(Data.getValue().toString());
                aux.setFaltam(profissionaisAux);
                aux.setVotos(votos);
                days.replace(Data.getValue().toString(),aux);
            }else{
                Lunch aux = new Lunch(Data.getValue(),profissionaisAux,votos);
                days.put(Data.getValue().toString(),aux);
            }
        }else{
            Resultado.setText("Profissional já votou nesta data");
        }
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(2500),
                ae -> changeText("")));
        timeline.play();
    }

    @FXML
    private void changeDate(){
        System.out.println(Data.getValue().toString());
        if(days.containsKey(Data.getValue().toString())){
            Lunch aux = days.get(Data.getValue().toString());
            votos = aux.getVotos();
            profissionaisAux = aux.getFaltam();
            if(!aux.getWinner().contentEquals("")){
                changeText(aux.getWinner());
            }
            changeText("");
        }else {
            votos = new HashMap<>();
            profissionaisAux = profissionais;
            changeText("");
        }
        int weekAux = getWeek();
        if(week.containsKey(weekAux)){
            restaurantesAux = week.get(weekAux);
        }else{
            restaurantesAux = restaurantes;
        }
        Restaurante.getItems().setAll(restaurantesAux);
    }

    private void changeText(String res){
        if(res.equals("")) {
            if (profissionaisAux.isEmpty()) {
                HashMap<String,Integer> votesAux = votos;
                String resultado = calculateVotes(votesAux);
                Resultado.setText("Almoço hoje: " + resultado);
                Lunch winnerAux = days.get(Data.getValue().toString());
                winnerAux.setWinner(resultado);
                days.replace(Data.getValue().toString(), winnerAux);
                restaurantesAux.remove(resultado);
                int weekAux = getWeek();
                if (week.containsKey(weekAux)) {
                    week.replace(weekAux, restaurantesAux);
                } else {
                    week.put(weekAux, restaurantesAux);
                }

            } else {
                Resultado.setText("Aguardando Conclusão");
            }
        }else{
            Resultado.setText("Almoço hoje: " + res);
        }
    }

    private int getWeek(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt = defaultFormat.parse(Data.getValue().toString());
            cal.setTime(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    private String calculateVotes(HashMap<String,Integer> votosAux){
        String resultado = "";
        int aux = 0;

        for (Map.Entry<String, Integer> voto : votos.entrySet()) {
            if (voto.getValue() > aux) {
                resultado = voto.getKey();
                aux = voto.getValue();
            }
        }

        if(restaurantesAux.contains(resultado)) {
            return resultado;
        }else{
            votosAux.remove(resultado);
            return calculateVotes(votosAux);
        }
    }


}
