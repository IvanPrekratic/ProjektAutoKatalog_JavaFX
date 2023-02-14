package hr.java.projekt.glavna.controllers;

import hr.java.projekt.threads.Thread1;
import hr.java.projekt.threads.Thread2;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PromjeneController {
    @FXML
    public Label kataloskiLabel;
    @FXML
    public Label nazivLabel;
    @FXML
    public Label kategorijaLabel;
    @FXML
    public Label markaLabel;
    @FXML
    public Label modelLabel;
    @FXML
    public Label proizvodacLabel;
    @FXML
    public Label cijenaLabel;
    @FXML
    public Label skladisteLabel;

    public void initialize(){
        Thread1 prvi = new Thread1(DodajProizvodController.dodani, this);
        Thread2 drugi = new Thread2(DodajProizvodController.dodani);
        prvi.start();
        drugi.start();
    }
}
