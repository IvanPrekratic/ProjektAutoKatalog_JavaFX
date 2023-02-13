package hr.java.projekt.glavna.controllers;

import hr.java.projekt.entiteti.Car;
import hr.java.projekt.entiteti.CarPart;
import hr.java.projekt.glavna.AutoKatalog;
import hr.java.projekt.iznimke.BazaPodatakaException;
import hr.java.projekt.login.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.List;

public class DodajProizvodController {
    @FXML
    private TextField markaField;
    @FXML
    private TextField modelField;
    @FXML
    private TextField nazivField;
    @FXML
    private ChoiceBox kategorijaBox;
    @FXML
    private TextField proizvodacField;
    @FXML
    private TextField kataloskiField;
    @FXML
    private TextField cijenaField;
    @FXML
    private TextField kolicinaField;
    private List<String> kategorjeDijelova;

    public void initialize() throws BazaPodatakaException {
        PrikazPremaVoziluController.dijelovi = Database.dohvatiDijelove();
        kategorjeDijelova = Database.dohvatiKategorije(PrikazPremaVoziluController.dijelovi);
        ObservableList<String> listaKategorija= FXCollections.observableArrayList(kategorjeDijelova);
        kategorijaBox.setItems(listaKategorija);
    }
    public void dodajProizvod() throws BazaPodatakaException {
        String confirmationMessage = "Želite li dodati novi element?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Unos novog elementa!");
        alert.setContentText(confirmationMessage);
        alert.show();
        if(popunjeno()){
            Integer idxAuta = 0;
            boolean postoji = false;
            for (Car car: Database.dohvatiAute()) {
                if(car.getMake().equals(markaField.getText()) && car.getModel().equals(modelField.getText())){
                    postoji = true;
                    CarPart novi = new CarPart(nazivField.getText(), kategorijaBox.getValue().toString(),car,
                            proizvodacField.getText(), kataloskiField.getText(), Double.valueOf(cijenaField.getText()),
                            Integer.valueOf(kolicinaField.getText()));
                    idxAuta = car.getId();
                    Database.dodajProizvod(novi,idxAuta);
                }
            }
            if(!postoji){
                Car noviAuto= new Car(markaField.getText(),modelField.getText());
                CarPart novi = new CarPart(nazivField.getText(), kategorijaBox.getValue().toString(),noviAuto,
                        proizvodacField.getText(), kataloskiField.getText(), Double.valueOf(cijenaField.getText()),
                        Integer.valueOf(kolicinaField.getText()));
                Database.dodajNoviAuto(noviAuto);
                Database.dodajProizvodSNovimAutom(novi,noviAuto);

            }
        }
        kategorijaBox.setValue("");
        kolicinaField.clear();
        cijenaField.clear();
        proizvodacField.clear();
        markaField.clear();
        kataloskiField.clear();
        modelField.clear();
        nazivField.clear();
    }
    private boolean popunjeno(){
        return !markaField.getText().equals("") && !modelField.getText().equals("") && !nazivField.getText().equals("")
                && !proizvodacField.getText().equals("") && !kataloskiField.getText().equals("")
                && !cijenaField.getText().equals("") && !kolicinaField.getText().equals("")
                && !kategorijaBox.getValue().toString().equals("");
    }
}
