module hr.java.projekt.projektautokatalog_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    exports hr.java.projekt.glavna;
    opens hr.java.projekt.glavna to javafx.fxml;
    exports hr.java.projekt.glavna.controllers;
    opens hr.java.projekt.glavna.controllers to javafx.fxml;
}