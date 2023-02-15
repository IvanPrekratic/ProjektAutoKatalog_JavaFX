package hr.java.projekt.glavna.controllers;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import hr.java.projekt.glavna.AutoKatalog;
import hr.java.projekt.login.LoginPristupBazi;
import hr.java.projekt.login.SessionMenager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class PocetniController {
    private static final Logger logger = LoggerFactory.getLogger(PocetniController.class);
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button signinButton;

    public void buttonAction() throws IOException {
        String username = usernameField.getText();
        String pass = passwordField.getText();
        int hash = pass.hashCode();

        String role = LoginPristupBazi.autentikacija(username, hash);
        if (role.equals("")){
            String errorMessage = "Krivi podaci za prijavu!";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pogresan unos podataka!");
            alert.setContentText(errorMessage);
            alert.show();
        }
        else {
            SessionMenager.setRole(role);
            SessionMenager.setUsername(username);
            if(SessionMenager.getRole().equals("Admin")) {
                BorderPane root;
                try {
                    root = FXMLLoader.load(getClass().getResource("/admin-pocetni-view.fxml"));
                    AutoKatalog.setMainPage(root);
                } catch (IOException e) {
                    logger.info("Problem s ucitavanjem scene");
                    e.printStackTrace();
                }
            } else if (SessionMenager.getRole().equals("User")) {
                BorderPane root;
                try {
                    root = FXMLLoader.load(getClass().getResource("/kategorije-dijelova-view.fxml"));
                    AutoKatalog.setMainPage(root);
                } catch (IOException e) {
                    logger.info("Problem s ucitavanjem scene");
                    e.printStackTrace();
                }
            }
        }

    }
}