package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
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

    public Controller()
    {
    }

    @FXML
    private void initialize()
    {
        ArrayList profissionais = new ArrayList();
        profissionais.add("Juca");
        profissionais.add("João");
        profissionais.add("Luisa");
        profissionais.add("Ariel");
        Profissional.getItems().setAll(profissionais);

        ArrayList restaurantes = new ArrayList();
        restaurantes.add("Palatus");
        restaurantes.add("Predio 32");
        restaurantes.add("Novo Sabor");
        restaurantes.add("Subway");
        restaurantes.add("Madero Container");
        restaurantes.add("Severo Garage");
        Restaurante.getItems().setAll(restaurantes);
    }




}
