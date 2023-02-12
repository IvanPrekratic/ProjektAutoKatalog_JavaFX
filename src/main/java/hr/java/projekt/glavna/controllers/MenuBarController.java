package hr.java.projekt.glavna.controllers;

import hr.java.projekt.entiteti.Kosarica;
import hr.java.projekt.entiteti.StvoriDokument;
import hr.java.projekt.glavna.AutoKatalog;
import hr.java.projekt.login.SessionMenager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;


public class MenuBarController {
    @FXML
    private Menu urediInventar;

    public void initialize(){
        if (SessionMenager.getRole().equals("User")){
            urediInventar.setVisible(false);
        }
    }
    public void odjaviMe(){
        SessionMenager.username = null;
        SessionMenager.role = null;
        PrikazDijelovaController.kosarica.ocistiKosaricu();
        GridPane root;
        try {
            root = FXMLLoader.load(getClass().getResource("/pocetni-view.fxml"));
            AutoKatalog.setMainPage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void uKosaricu(){
        BorderPane root;
        try {
            root = FXMLLoader.load(getClass().getResource("/kosarica-view.fxml"));
            AutoKatalog.setMainPage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void proizvodiPoKategoriji(){
        BorderPane root;
        try {
            root = FXMLLoader.load(getClass().getResource("/kategorije-dijelova-view.fxml"));
            AutoKatalog.setMainPage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void izveziKosaricu(){
        StvoriDokument.stvoriDokument(PrikazDijelovaController.kosarica.getElementi());
    }
}
