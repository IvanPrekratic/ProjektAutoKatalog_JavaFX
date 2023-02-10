package hr.java.projekt.glavna.controllers;

import hr.java.projekt.entiteti.Car;
import hr.java.projekt.entiteti.CarPart;
import hr.java.projekt.entiteti.Kosarica;
import hr.java.projekt.iznimke.BazaPodatakaException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KosaricaController {
    @FXML
    private TableView<Kosarica> dijeloviTable;
    @FXML
    private TableColumn<Kosarica , String> kataloskiBrojColumn;
    @FXML
    private TableColumn<Kosarica, String> modelAutaColumn;
    @FXML
    private TableColumn<Kosarica, String> nazivDijelaColumn;
    @FXML
    private TableColumn<Kosarica, String> proizvodacDijelaColumn;
    @FXML
    private TableColumn<Kosarica, Double> cijenaDijelaColumn;
    @FXML
    private TableColumn<Kosarica, Integer> narucenaKolicina;

    Kosarica uKosarici = new Kosarica();

    public void initialize() throws BazaPodatakaException {

        ObservableList<Kosarica> observableListDijelovi = FXCollections.observableArrayList(uKosarici);

/*
        kataloskiBrojColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getProizvodi().stream().iterator().forEachRemaining(proizvod -> proizvod.getPartNumber());));
        modelAutaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getCar().getMake() + " " + dijelovi.getValue().getCar().getModel()));
        nazivDijelaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getName()));
        proizvodacDijelaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getPartManufactor()));
        cijenaDijelaColumn.setCellValueFactory(dijelovi -> new SimpleDoubleProperty(dijelovi.getValue().getPartPrice()).asObject());
        narucenaKolicina.setCellValueFactory(kolicina -> new SimpleIntegerProperty(kolicina.getValue()).asObject());
        dijeloviTable.setItems(observableListDijelovi);
        dijeloviTable.setItems();

 */


    }

}
