package sample;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.ArrayList;

public class Util {


    /**
     * @return A List of Restaurants mocked
     */
    public static ArrayList<String> getRestaurantes(){
        ArrayList<String> restaurantes = new ArrayList<>();
        restaurantes.add("Palatus");
        restaurantes.add("Predio 32");
        restaurantes.add("Novo Sabor");
        restaurantes.add("Subway");
        restaurantes.add("Madero Container");
        restaurantes.add("Severo Garage");
        return restaurantes;
    }

    /**
     * @return A List of Professionals mocked
     */
    public static ArrayList<String> getProfissionais(){
        ArrayList<String> profissionais = new ArrayList<>();
        profissionais.add("Juca");
        profissionais.add("João");
        profissionais.add("Luisa");
        profissionais.add("Ariel");
        profissionais.add("Leo");
        return profissionais;
    }

    /**
     * @return A DayCellFactory for format the DataPicker
     */
    public static Callback<DatePicker, DateCell> getDayCellRule(){
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
        return dayCellFactory;
    }

}
