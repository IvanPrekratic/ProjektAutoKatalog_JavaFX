package hr.java.projekt.glavna.controllers;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import hr.java.projekt.entiteti.*;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.text.DecimalFormat;

public class KosaricaController {
    private static final Logger logger = LoggerFactory.getLogger(KosaricaController.class);
    @FXML
    private Label ukupnoLabel;
    @FXML
    private Button makniButton;
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
    public static String vrati;


    public void initialize() {
        dijeloviTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue != null)
                makniButton.setVisible(true);
            else
                makniButton.setVisible(true);
        });
        ObservableList<CartItem> observableListKosarica = FXCollections.observableArrayList(PrikazDijelovaController.kosarica.getElementi());

        kataloskiBrojColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getProizvod().getPartNumber()));
        modelAutaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().
                getProizvod().getCar().getMake() + " " + dijelovi.getValue().getProizvod().getCar().getModel()));
        nazivDijelaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getProizvod().getName()));
        proizvodacDijelaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getProizvod().getPartManufactor()));
        narucenaKolicina.setCellValueFactory(dijelovi -> new SimpleIntegerProperty(dijelovi.getValue().getKolicina()).asObject());
        cijenaDijelaColumn.setCellValueFactory(dijelovi -> new SimpleDoubleProperty(dijelovi.getValue().getProizvod().getPartPrice()).asObject());
        dijeloviTable.setItems(observableListKosarica);
        ukupnoLabel.setText(ukupno());
    }
    public void makniIzKosarice() throws BazaPodatakaException {
        CartItem novi = dijeloviTable.getSelectionModel().getSelectedItem();


        Integer broj = novi.getKolicina();
        for (int i = 0; i < PrikazDijelovaController.dijelovi.size(); i++) {
            CarPart itm =PrikazDijelovaController.dijelovi.get(i);
            if(itm.getPartNumber().equals(novi.getProizvod().getPartNumber())) {
                Integer uKosarici = novi.getKolicina();
                Database.povecajStanje(itm, uKosarici);
            }
        }
        for (int i = 0; i < PrikazDijelovaController.kosarica.getElementi().size(); i++) {
            CarPart itm = PrikazDijelovaController.kosarica.getElementi().get(i).getProizvod();
            if (itm.getPartNumber().equals(novi.getProizvod().getPartNumber())){
                PrikazDijelovaController.kosarica.getElementi().remove(i);
            }
        }
        dijeloviTable.refresh();
        BorderPane root;
        try {
            root = FXMLLoader.load(getClass().getResource("/kosarica-view.fxml"));
            AutoKatalog.setMainPage(root);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("Problem s ucitavanjem scene");
        }
    }
    public void izveziKosaricu(){
        StvoriDokument.stvoriDokument(PrikazDijelovaController.kosarica.getElementi());
    }
    private String ukupno(){
        Double ukupno = Double.valueOf(0);
        DecimalFormat df = new DecimalFormat("#.00");
        for (CartItem itm: PrikazDijelovaController.kosarica.getElementi()) {
            ukupno+=itm.getProizvod().getPartPrice()*itm.getKolicina();
        }
        if (ukupno != 0){
            vrati = df.format(ukupno) + " EUR";
            return vrati;
        }
        else
            return null;
    }
}
