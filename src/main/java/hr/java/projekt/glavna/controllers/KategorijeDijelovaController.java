package hr.java.projekt.glavna.controllers;

import hr.java.projekt.glavna.AutoKatalog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class KategorijeDijelovaController {

    public static String filter = null;
    @FXML
    private ImageView motor;
    @FXML
    private ImageView karoserija;
    @FXML
    private ImageView kocnice;
    @FXML
    private ImageView ovjesIUpravljanje;
    @FXML
    private ImageView elektronika;
    @FXML
    private ImageView uljaIAntifriz;
    @FXML
    private ImageView osovinaIPrijenos;
    @FXML
    private ImageView hladenje;

    public void prikaziDijeloveMotora(){
        BorderPane root;
        try {
            filter = "motor";
            root = FXMLLoader.load(getClass().getResource("/admin-pocetni-view.fxml"));
            AutoKatalog.setMainPage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void prikaziDijeloveKaroserije(){
        BorderPane root;
        try {
            filter = "karoserija";
            root = FXMLLoader.load(getClass().getResource("/admin-pocetni-view.fxml"));
            AutoKatalog.setMainPage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void prikaziDijeloveKocnica(){
        BorderPane root;
        try {
            filter = "kocnice";
            root = FXMLLoader.load(getClass().getResource("/prikaz-dijelova-view.fxml"));
            AutoKatalog.setMainPage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void prikaziDijeloveOvjesaIUpravljanja(){
        BorderPane root;
        try {
            filter = "ovjesIUpravljanje";
            root = FXMLLoader.load(getClass().getResource("/admin-pocetni-view.fxml"));
            AutoKatalog.setMainPage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void prikaziDijeloveElektronike(){
        BorderPane root;
        try {
            filter = "elektronika";
            root = FXMLLoader.load(getClass().getResource("/admin-pocetni-view.fxml"));
            AutoKatalog.setMainPage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void prikaziUljaIAntifiz(){
        BorderPane root;
        try {
            filter = "uljeIAntifriz";
            root = FXMLLoader.load(getClass().getResource("/admin-pocetni-view.fxml"));
            AutoKatalog.setMainPage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void prikaziDijeloveOsovineIPrijenosa(){
        BorderPane root;
        try {
            filter = "osovinaIPrijenos";
            root = FXMLLoader.load(getClass().getResource("/admin-pocetni-view.fxml"));
            AutoKatalog.setMainPage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void prikaziDijeloveHladenja(){
        BorderPane root;
        try {
            filter = "hladenje";
            root = FXMLLoader.load(getClass().getResource("/admin-pocetni-view.fxml"));
            AutoKatalog.setMainPage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
