package sample;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.net.URL;
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

    @FXML
    private Button BtnVotar;

    private ArrayList<String> workers;
    private ArrayList<String> restaurants;
    private ArrayList<String> restaurantsAux;
    private HashMap<String,Lunch> days;
    private HashMap<Integer,ArrayList> week;
    private LunchBO lunchBO;
    private Lunch actualLunch;


    public Controller()
    {
        restaurants = new ArrayList<>();
        workers = new ArrayList<>();
        restaurantsAux = new ArrayList<>();
        days = new HashMap<>();
        week = new HashMap<>();
        lunchBO = new LunchBO();
        actualLunch = new Lunch(LocalDate.now());
    }

    /**
     * Method called when the screen is initialized, set the auxiliary variables and get the mocked data from Utils
     */
    @FXML
    private void initialize()
    {
        workers.addAll(Util.getProfissionais());
        Profissional.getItems().setAll(workers);

        restaurants.addAll(Util.getRestaurantes());
        Restaurante.getItems().setAll(restaurants);


        actualLunch.setMissing(workers);
        restaurantsAux.addAll(restaurants);

        Data.setValue(LocalDate.now());

        Data.setDayCellFactory(Util.getDayCellRule());
        Data.setEditable(false);
        if(lunchBO.isPastNoon(new Date())){
            BtnVotar.setDisable(true);
            changeText("Finalizado");
        }
    }

    /**
     * Method called when the Button is pressed, it compute the vote of the selected worker on the selected restaurant
     */
    @FXML
    private void vote(){
        String workerAux = Profissional.getValue().toString();
        String restaurantAux = Restaurante.getValue().toString();
        Lunch result = lunchBO.computeVote(actualLunch, workerAux, restaurantAux);
        if(result != null) {
            Resultado.setText("Voto Computado");
            actualLunch = result;
            if(days.containsKey(Data.getValue().toString())){
                days.replace(Data.getValue().toString(),actualLunch);
            }else {
                days.put(Data.getValue().toString(), actualLunch);
            }
        }else{
            Resultado.setText("Profissional já votou nesta data");
        }
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(2500),
                ae -> changeText("")));
        timeline.play();
    }

    /**
     * Method called when the date is changed on DatePicker, it verify if the selected date already had result or votes and set the auxiliary variables
     */
    @FXML
    private void changeDate(){
        BtnVotar.setDisable(false);
        if(days.containsKey(Data.getValue().toString())){
            actualLunch = days.get(Data.getValue().toString());
            if(!actualLunch.getWinner().contentEquals("")){
                changeText(actualLunch.getWinner());
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
                actualLunch = new Lunch(Data.getValue());
                actualLunch.setMissing(workers);
                changeText("");
            }
        }
        int weekAux = lunchBO.getWeek(Data.getValue());
        if(week.containsKey(weekAux)){
            restaurantsAux = week.get(weekAux);
        }else{
            restaurantsAux.clear();
            restaurantsAux.addAll(restaurants);
        }
        Restaurante.getItems().setAll(restaurantsAux);
    }

    /**
     * @param res Some msg for validation
     *
     * The method verify if all workers had voted and call the calculate votes, or set the message of the result on screen
     */
    private void changeText(String res){
        if(!actualLunch.getWinner().equals("")){
            Resultado.setText("Almoço: " + actualLunch.getWinner());
        }else {
            if (actualLunch.getMissing().isEmpty() || (res.equals("Finalizado") && actualLunch.getMissing().size() != workers.size())) {
                String resultado = lunchBO.calculateVotes(actualLunch.getVotes(), restaurantsAux);
                Resultado.setText("Almoço: " + resultado);
                actualLunch.setWinner(resultado);
                days.replace(Data.getValue().toString(), actualLunch);
                restaurantsAux.remove(resultado);
                int weekAux = lunchBO.getWeek(Data.getValue());
                if (week.containsKey(weekAux)) {
                    week.replace(weekAux, restaurantsAux);
                } else {
                    week.put(weekAux, restaurantsAux);
                }

            } else {
                if (res.equals("Finalizado")) {
                    Resultado.setText("Nenhum Restaurante foi Decidido");
                } else {
                    Resultado.setText("Aguardando Conclusão");
                }
            }
        }
    }


}
