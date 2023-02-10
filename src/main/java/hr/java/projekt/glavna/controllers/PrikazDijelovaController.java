package hr.java.projekt.glavna.controllers;

import hr.java.projekt.entiteti.CarPart;
import hr.java.projekt.entiteti.Kosarica;
import hr.java.projekt.iznimke.BazaPodatakaException;
import hr.java.projekt.login.Database;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class PrikazDijelovaController {
    @FXML
    private TextField brKomada;
    @FXML
    private TableView<CarPart> dijeloviTable;
    @FXML
    private TableColumn<CarPart, String> kataloskiBrojColumn;
    @FXML
    private TableColumn<CarPart, String> modelAutaColumn;
    @FXML
    private TableColumn<CarPart, String> nazivDijelaColumn;
    @FXML
    private TableColumn<CarPart, String> proizvodacDijelaColumn;
    @FXML
    private TableColumn<CarPart, Integer> dostupnoKomadaColumn;
    @FXML
    private TableColumn<CarPart, Double> cijenaDijelaColumn;

    List<CarPart> dijelovi = null;

    Kosarica kosarica = new Kosarica();
    List<CarPart> zaKosaricu = new ArrayList<>();

    public void initialize() throws BazaPodatakaException {
        dijelovi = Database.dohvatiDijelove();

        ObservableList<CarPart> observableListDijelovi = FXCollections.observableArrayList(dijelovi);

        kataloskiBrojColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getPartNumber()));
        modelAutaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getCar().getMake() + " " + dijelovi.getValue().getCar().getModel()));
        nazivDijelaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getName()));
        proizvodacDijelaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getPartManufactor()));
        dostupnoKomadaColumn.setCellValueFactory(dijelovi -> new SimpleIntegerProperty(dijelovi.getValue().getPartStock()).asObject());
        cijenaDijelaColumn.setCellValueFactory(dijelovi -> new SimpleDoubleProperty(dijelovi.getValue().getPartPrice()).asObject());
        dijeloviTable.setItems(observableListDijelovi);
    }

    public void filter(){

    }

    public void dodajUKosaricu() throws BazaPodatakaException {
        Kosarica.proizvodi.add(dijeloviTable.getSelectionModel().getSelectedItem());
        Kosarica.kolicina.add(Integer.parseInt(brKomada.getText()));
        Database.azurirajStanje(dijeloviTable.getSelectionModel().getSelectedItem(),Integer.parseInt(brKomada.getText()));
    }
}
