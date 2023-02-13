package hr.java.projekt.glavna.controllers;

import hr.java.projekt.entiteti.CarPart;
import hr.java.projekt.glavna.AutoKatalog;
import hr.java.projekt.iznimke.BazaPodatakaException;
import hr.java.projekt.login.Database;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class UkloniProizvodController {
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
    List<CarPart> dijelovi;


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



    public void ukloniProizvod() throws BazaPodatakaException {
        AtomicBoolean ok = new AtomicBoolean(false);
        String confirmationMessage = "Želite li obrisati odabrani element?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Brisanje elementa!");
        alert.setContentText(confirmationMessage);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                ok.set(true);
            }
        });
        if(dijeloviTable.getSelectionModel().getSelectedItems() != null && ok.get()){

            Integer partID = dijeloviTable.getSelectionModel().getSelectedItem().getId();
            Database.obrisiProizvod(partID);
            dijelovi = Database.dohvatiDijelove();
            dijeloviTable.refresh();
            BorderPane root;
            try {
                root = FXMLLoader.load(getClass().getResource("/ukloni-proizvod-view.fxml"));
                AutoKatalog.setMainPage(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
