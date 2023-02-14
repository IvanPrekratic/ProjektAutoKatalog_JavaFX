package hr.java.projekt.glavna.controllers;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import hr.java.projekt.entiteti.StvoriDokument;
import hr.java.projekt.glavna.AutoKatalog;
import hr.java.projekt.iznimke.SceneLoadingException;
import hr.java.projekt.login.SessionMenager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class MenuBarController {
    private static final Logger logger = LoggerFactory.getLogger(MenuBarController.class);
    @FXML
    private Menu urediInventar;
    @FXML
    private Menu promjene;

    public void initialize(){
        if (SessionMenager.getRole().equals("User")){
            urediInventar.setVisible(false);
            promjene.setVisible(false);
        }
    }
    public void odjaviMe() throws SceneLoadingException {
        SessionMenager.username = null;
        SessionMenager.role = null;
        PrikazDijelovaController.kosarica.ocistiKosaricu();
        GridPane root;
        try {
            root = FXMLLoader.load(getClass().getResource("/pocetni-view.fxml"));
            AutoKatalog.setMainPage(root);
        } catch (IOException e) {
            logger.info("Problem s ucitavanjem scene");
            String poruka = "Došlo je do pogreške u radu ucitavanju sljedece scene!";
            throw new SceneLoadingException(poruka,e);
        }
    }

    public void uKosaricu() throws SceneLoadingException {
        BorderPane root;
        try {
            root = FXMLLoader.load(getClass().getResource("/kosarica-view.fxml"));
            AutoKatalog.setMainPage(root);
        } catch (IOException e) {
            logger.info("Problem s ucitavanjem scene");
            String poruka = "Došlo je do pogreške u radu ucitavanju sljedece scene!";
            throw new SceneLoadingException(poruka,e);
        }
    }
    public void proizvodiPoKategoriji() throws SceneLoadingException {
        BorderPane root;
        try {
            root = FXMLLoader.load(getClass().getResource("/kategorije-dijelova-view.fxml"));
            AutoKatalog.setMainPage(root);
        } catch (IOException e) {
            logger.info("Problem s ucitavanjem scene");
            String poruka = "Došlo je do pogreške u radu ucitavanju sljedece scene!";
            throw new SceneLoadingException(poruka,e);
        }
    }
    public void proizvodiPoVozilu() throws SceneLoadingException {
        BorderPane root;
        try {
            root = FXMLLoader.load(getClass().getResource("/dijelovi-prema-vozilu-view.fxml"));
            AutoKatalog.setMainPage(root);
        } catch (IOException e) {
            logger.info("Problem s ucitavanjem scene");
            String poruka = "Došlo je do pogreške u radu ucitavanju sljedece scene!";
            throw new SceneLoadingException(poruka,e);
        }
    }
    public void izveziKosaricu(){
        StvoriDokument.stvoriDokument(PrikazDijelovaController.kosarica.getElementi());
    }
    public void dodajProizvod() throws SceneLoadingException {
        BorderPane root;
        try {
            root = FXMLLoader.load(getClass().getResource("/dodaj-proizvod-view.fxml"));
            AutoKatalog.setMainPage(root);
        } catch (IOException e) {
            logger.info("Problem s ucitavanjem scene");
            String poruka = "Došlo je do pogreške u radu ucitavanju sljedece scene!";
            throw new SceneLoadingException(poruka,e);
        }
    }
    public void ukloniProizvod() throws SceneLoadingException {
        BorderPane root;
        try {
            root = FXMLLoader.load(getClass().getResource("/ukloni-proizvod-view.fxml"));
            AutoKatalog.setMainPage(root);
        } catch (IOException e) {
            logger.info("Problem s ucitavanjem scene");
            String poruka = "Došlo je do pogreške u radu ucitavanju sljedece scene!";
            throw new SceneLoadingException(poruka,e);
        }
    }
    public void promjeniStanjeNaSkladistu() throws SceneLoadingException {
        BorderPane root;
        try {
            root = FXMLLoader.load(getClass().getResource("/promjena-stanja-view.fxml"));
            AutoKatalog.setMainPage(root);
        } catch (IOException e) {
            logger.info("Problem s ucitavanjem scene");
            String poruka = "Došlo je do pogreške u radu ucitavanju sljedece scene!";
            throw new SceneLoadingException(poruka,e);
        }
    }
    public void pogledajPromjene() throws SceneLoadingException {
        BorderPane root;
        try {
            root = FXMLLoader.load(getClass().getResource("/promjene-view.fxml"));
            AutoKatalog.setMainPage(root);
        } catch (IOException e) {
            logger.info("Problem s ucitavanjem scene");
            String poruka = "Došlo je do pogreške u radu ucitavanju sljedece scene!";
            throw new SceneLoadingException(poruka,e);
        }
    }
}
