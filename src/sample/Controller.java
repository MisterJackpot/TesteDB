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
        restaurantes = new ArrayList();
        profissionais = new ArrayList();
        profissionaisAux = new ArrayList();
        restaurantesAux = new ArrayList();
        votos = new HashMap<>();
        days = new HashMap<>();
        week = new HashMap<>();
        lunchBO = new LunchBO();
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

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (MonthDay.from(item).isBefore(MonthDay.from(LocalDate.now()))) {
                            setDisable(true);
                        }
                    }
                };
            }
        };
        Data.setDayCellFactory(dayCellFactory);
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
        if(profissionaisAux.contains(profissionalAux)) {
            Resultado.setText("Voto Computado " + Profissional.getValue());
            if(votos.containsKey(restauranteAux)){
                votos.replace(restauranteAux,votos.get(restauranteAux)+1);
            }else{
                votos.put(restauranteAux,1);
            }
            profissionaisAux.remove(profissionalAux);
            days = lunchBO.computeVote(Data.getValue(),profissionaisAux,votos,days);
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
            }
            changeText("");
        }else {
            votos = new HashMap<>();
            profissionaisAux = profissionais;
            changeText("");
        }
        int weekAux = lunchBO.getWeek(Data.getValue());
        if(week.containsKey(weekAux)){
            restaurantesAux = week.get(weekAux);
        }else{
            restaurantesAux = restaurantes;
        }
        Restaurante.getItems().setAll(restaurantesAux);
        if(Data.getValue().isEqual(LocalDate.now())){
            if(lunchBO.isPastNoon(new Date())){
                BtnVotar.setDisable(true);
                changeText("Finalizado");
            }
        }
    }

    private void changeText(String res){
        if(res.equals("") || res.equals("Finalizado")) {
            if (profissionaisAux.isEmpty()) {
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
