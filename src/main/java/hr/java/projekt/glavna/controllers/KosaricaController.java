package hr.java.projekt.glavna.controllers;

import hr.java.projekt.entiteti.Car;
import hr.java.projekt.entiteti.CarPart;
import hr.java.projekt.entiteti.CartItem;
import hr.java.projekt.entiteti.Kosarica;
import hr.java.projekt.glavna.AutoKatalog;
import hr.java.projekt.iznimke.BazaPodatakaException;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
    public TableView<CartItem> dijeloviTable;
    @FXML
    private TableColumn<CartItem , String> kataloskiBrojColumn;
    @FXML
    private TableColumn<CartItem, String> modelAutaColumn;
    @FXML
    private TableColumn<CartItem, String> nazivDijelaColumn;
    @FXML
    private TableColumn<CartItem, String> proizvodacDijelaColumn;
    @FXML
    private TableColumn<CartItem, Double> cijenaDijelaColumn;
    @FXML
    private TableColumn<CartItem, Integer> narucenaKolicina;



    public void initialize() throws BazaPodatakaException {
        ObservableList<CartItem> observableListKosarica = FXCollections.observableArrayList(PrikazDijelovaController.kosarica.getElementi());

        kataloskiBrojColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getProizvod().getPartNumber()));
        modelAutaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().
                getProizvod().getCar().getMake() + " " + dijelovi.getValue().getProizvod().getCar().getModel()));
        nazivDijelaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getProizvod().getName()));
        proizvodacDijelaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getProizvod().getPartManufactor()));
        narucenaKolicina.setCellValueFactory(dijelovi -> new SimpleIntegerProperty(dijelovi.getValue().getKolicina()).asObject());
        cijenaDijelaColumn.setCellValueFactory(dijelovi -> new SimpleDoubleProperty(dijelovi.getValue().getProizvod().getPartPrice()).asObject());
        dijeloviTable.setItems(observableListKosarica);

    }

}
