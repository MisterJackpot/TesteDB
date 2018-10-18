package sample;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.time.MonthDay;
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

    @FXML
    private Button BtnVotar;

    private ArrayList<String> profissionais;
    private ArrayList<String> restaurantes;
    private ArrayList<String> profissionaisAux;
    private ArrayList<String> restaurantesAux;
    private HashMap<String,Integer> votos;
    private HashMap<String,Lunch> days;
    private HashMap<Integer,ArrayList> week;
    private LunchBO lunchBO;


    public Controller()
    {
        restaurantes = new ArrayList<>();
        profissionais = new ArrayList<>();
        profissionaisAux = new ArrayList<>();
        restaurantesAux = new ArrayList<>();
        votos = new HashMap<>();
        days = new HashMap<>();
        week = new HashMap<>();
        lunchBO = new LunchBO();
    }

    @FXML
    private void initialize()
    {
        profissionais.addAll(Util.getProfissionais());
        Profissional.getItems().setAll(profissionais);

        restaurantes.addAll(Util.getRestaurantes());
        Restaurante.getItems().setAll(restaurantes);

        profissionaisAux.addAll(profissionais);
        restaurantesAux.addAll(restaurantes);

        Data.setValue(LocalDate.now());

        Data.setDayCellFactory(Util.getDayCellRule());
        Data.setEditable(false);
        if(lunchBO.isPastNoon(new Date())){
            BtnVotar.setDisable(true);
            changeText("Finalizado");
        }
    }

    @FXML
    private void vote(){
        String profissionalAux = Profissional.getValue().toString();
        String restauranteAux = Restaurante.getValue().toString();
        HashMap<String,Lunch> result = lunchBO.computeVote(Data.getValue(),profissionaisAux,votos,days,profissionalAux,restauranteAux);
        if(result != null) {
           Resultado.setText("Voto Computado");
           days = result;
           profissionaisAux = result.get(Data.getValue().toString()).getFaltam();
           votos = result.get(Data.getValue().toString()).getVotos();
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
        BtnVotar.setDisable(false);
        System.out.println(Data.getValue().toString());
        if(days.containsKey(Data.getValue().toString())){
            Lunch aux = days.get(Data.getValue().toString());
            votos = aux.getVotos();
            profissionaisAux = aux.getFaltam();
            if(!aux.getWinner().contentEquals("")){
                changeText(aux.getWinner());
            }else {
                if (Data.getValue().isEqual(LocalDate.now())) {
                    if (lunchBO.isPastNoon(new Date())) {
                        BtnVotar.setDisable(true);
                        changeText("Finalizado");
                    }
                }else {
                    changeText("");
                }
            }
        }else {
            if(Data.getValue().isEqual(LocalDate.now())){
                if(lunchBO.isPastNoon(new Date())){
                    BtnVotar.setDisable(true);
                    changeText("Finalizado");
                }
            }else {
                votos = new HashMap<>();
                profissionaisAux = profissionais;

                changeText("");
            }
        }
        int weekAux = lunchBO.getWeek(Data.getValue());
        if(week.containsKey(weekAux)){
            restaurantesAux = week.get(weekAux);
        }else{
            restaurantesAux = restaurantes;
        }
        Restaurante.getItems().setAll(restaurantesAux);
    }

    private void changeText(String res){
        if(res.equals("") || res.equals("Finalizado")) {
            if (profissionaisAux.isEmpty() || (res.equals("Finalizado") && profissionaisAux.size() != profissionais.size())) {
                String resultado = lunchBO.calculateVotes(votos,restaurantesAux);
                Resultado.setText("Almoço: " + resultado);
                Lunch winnerAux = days.get(Data.getValue().toString());
                winnerAux.setWinner(resultado);
                days.replace(Data.getValue().toString(), winnerAux);
                restaurantesAux.remove(resultado);
                int weekAux = lunchBO.getWeek(Data.getValue());
                if (week.containsKey(weekAux)) {
                    week.replace(weekAux, restaurantesAux);
                } else {
                    week.put(weekAux, restaurantesAux);
                }

            } else {
                if(res.equals("Finalizado")){
                    Resultado.setText("Nenhum Restaurante foi Decidido");
                }else {
                    Resultado.setText("Aguardando Conclusão");
                }
            }
        }else{
            Resultado.setText("Almoço: " + res);
        }
    }


}
