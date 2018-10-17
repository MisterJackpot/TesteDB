package sample;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


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

    private ArrayList profissionais;
    private ArrayList restaurantes;
    private ArrayList profissionaisAux;
    private ArrayList restaurantesAux;
    private HashMap<String,Integer> votos;


    public Controller()
    {
        restaurantes = new ArrayList();
        profissionais = new ArrayList();
        profissionaisAux = new ArrayList();
        restaurantesAux = new ArrayList();
        votos = new HashMap<>();
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
        }else{
            Resultado.setText("Profissional já votou nesta data");
        }
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(2500),
                ae -> changeText()));
        timeline.play();
    }

    private void changeText(){
        if(profissionaisAux.isEmpty()){
            String resultado = "";
            int aux = 0;
            for (Map.Entry<String, Integer> voto : votos.entrySet()) {
                if(voto.getValue() > aux){
                    resultado = voto.getKey();
                    aux = voto.getValue();
                }
            }
            Resultado.setText("Almoço hoje: " + resultado);
            restaurantesAux.remove(resultado);
        }else {
            if (Resultado.getText() != "Aguardando Conclusão") Resultado.setText("Aguardando Conclusão");
        }
    }



}
