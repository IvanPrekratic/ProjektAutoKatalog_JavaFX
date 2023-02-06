package hr.java.projekt.glavna.controllers;

import hr.java.projekt.glavna.AutoKatalog;
import hr.java.projekt.login.LoginPodaci;
import hr.java.projekt.login.LoginPristupBazi;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class PocetniController {

    public static String role;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button signinButton;
    /*
    komentaaaar
     */

    public void buttonAction(){
        String username = usernameField.getText();
        String pass = passwordField.getText();

        LoginPodaci podaci = new LoginPodaci(username, pass);
        role = LoginPristupBazi.autentikacija(podaci);
        if(role.equals("Admin")) {
            BorderPane root;
            try {
                root = (BorderPane) FXMLLoader.load(getClass().getResource("/admin-pocetni-view.fxml"));
                AutoKatalog.setMainPage(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (role.equals("User")) {
            BorderPane root;
            try {
                root = (BorderPane) FXMLLoader.load(getClass().getResource("/pretraga-dijelova-view.fxml"));
                AutoKatalog.setMainPage(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}