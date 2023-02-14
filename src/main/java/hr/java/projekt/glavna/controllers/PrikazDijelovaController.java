package hr.java.projekt.glavna.controllers;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import hr.java.projekt.entiteti.CarPart;
import hr.java.projekt.entiteti.CartItem;
import hr.java.projekt.entiteti.Kosarica;
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
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class PrikazDijelovaController {

    private static final Logger logger = LoggerFactory.getLogger(PrikazDijelovaController.class);
    public static Kosarica kosarica = new Kosarica();
    @FXML
    private Button dodajUKosaricuButton;
    @FXML
    private Label komadaLabel;
    @FXML
    private ComboBox markaBox;
    @FXML
    private ComboBox modelBox;
    @FXML
    private ComboBox kategorijaBox;
    @FXML
    private ComboBox proizvodacBox;
    @FXML
    private TextField brKomada;
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

    public static List<CarPart> dijelovi = null;
    public List<CarPart> filtriraniDijeloviGlavni;
    private Boolean filtrirano = false;
    private List<String> markeAuta;
    private List<String> modeliAuta;
    private List<String> kategorjeDijelova;
    private List<String> proizvodaciDijelova;

    public void initialize() throws BazaPodatakaException {
        dijeloviTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue != null) {
                dodajUKosaricuButton.setVisible(true);
                komadaLabel.setVisible(true);
                brKomada.setVisible(true);
            }
            else {
                dodajUKosaricuButton.setVisible(false);
                komadaLabel.setVisible(false);
                brKomada.setVisible(false);
            }
        });
        if(KategorijeDijelovaController.filter != null){
            dijelovi = Database.dohvatiDijelove();
            dijelovi = filter(dijelovi);
        }
        else
            dijelovi = Database.dohvatiDijelove();
        filtriraniDijeloviGlavni = dijelovi;

        markeAuta = Database.dohvatiMarke(dijelovi);
        modeliAuta = Database.dohvatiModele(dijelovi);
        kategorjeDijelova = Database.dohvatiKategorije(dijelovi);
        proizvodaciDijelova = Database.dohvatiProizvodace(dijelovi);

        ObservableList<CarPart> observableListDijelovi = FXCollections.observableArrayList(dijelovi);
        kataloskiBrojColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getPartNumber()));
        modelAutaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getCar().getMake() + " " + dijelovi.getValue().getCar().getModel()));
        nazivDijelaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getName()));
        proizvodacDijelaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getPartManufactor()));
        dostupnoKomadaColumn.setCellValueFactory(dijelovi -> new SimpleIntegerProperty(dijelovi.getValue().getPartStock()).asObject());
        cijenaDijelaColumn.setCellValueFactory(dijelovi -> new SimpleDoubleProperty(dijelovi.getValue().getPartPrice()).asObject());
        dijeloviTable.setItems(observableListDijelovi);

        ObservableList<String> listaZaMarke = FXCollections.observableArrayList(markeAuta);
        markaBox.setItems(listaZaMarke);
        markaBox.setPromptText("Odaberi marku");


        ObservableList<String> listaZaModele = FXCollections.observableArrayList(modeliAuta);
        modelBox.setItems(listaZaModele);
        modelBox.setPromptText("Odaberi model");

        ObservableList<String> listaZaProizvodaca = FXCollections.observableArrayList(proizvodaciDijelova);
        proizvodacBox.setItems(listaZaProizvodaca);
        proizvodacBox.setPromptText("Odaberi proizvodaca");
    }

    public List<CarPart> filter(List<CarPart> dijelovi){
         return dijelovi.stream().filter(part -> part.getCategory().equals(KategorijeDijelovaController.filter)).collect(Collectors.toList());
    }

    public void dodajUKosaricu() throws BazaPodatakaException {
        boolean postoji = false;
        CarPart novi = dijeloviTable.getSelectionModel().getSelectedItem();
        Integer broj = Integer.parseInt(brKomada.getText());

        for (CartItem itm: PrikazDijelovaController.kosarica.getElementi()) {
            if(itm.getProizvod().getPartNumber().equals(novi.getPartNumber())) {
                Integer stara = itm.getKolicina();
                itm.setKolicina(stara + broj);
                postoji = true;
                Database.smanjiStanje(novi, broj);
            }
        }

        if(!postoji){
            CartItem oznaceni = new CartItem(novi, broj);
            PrikazDijelovaController.kosarica.dodajElement(oznaceni);
            Database.smanjiStanje(novi, broj);
        }
        if(KategorijeDijelovaController.filter != null){
            dijelovi = Database.dohvatiDijelove();
            dijelovi = filter(dijelovi);
        }
        else
            dijelovi = Database.dohvatiDijelove();
        dijeloviTable.refresh();
        BorderPane root;
        try {
            root = FXMLLoader.load(getClass().getResource("/prikaz-dijelova-view.fxml"));
            AutoKatalog.setMainPage(root);
        } catch (IOException e) {
            logger.info("Problem s ucitavanjem scene");
            e.printStackTrace();
        }
    }
    public void filtrirajMarke(){
        List<CarPart> filtriraniDijelovi;
        if (markaBox.getValue().toString().equals(""))
            filtrirano = false;
        if (filtrirano)
            filtriraniDijelovi = filtriraniDijeloviGlavni;
        else
            filtriraniDijelovi = dijelovi;
        filtriraniDijelovi = filtriraniDijelovi.stream().filter(dijelovi -> dijelovi.getCar().getMake().contains(markaBox.getValue().toString())).collect(Collectors.toList());
        ObservableList<CarPart> observableListFiltriraniDijelovi = FXCollections.observableArrayList(filtriraniDijelovi);

        kataloskiBrojColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getPartNumber()));
        modelAutaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getCar().getMake() + " " + dijelovi.getValue().getCar().getModel()));
        nazivDijelaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getName()));
        proizvodacDijelaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getPartManufactor()));
        dostupnoKomadaColumn.setCellValueFactory(dijelovi -> new SimpleIntegerProperty(dijelovi.getValue().getPartStock()).asObject());
        cijenaDijelaColumn.setCellValueFactory(dijelovi -> new SimpleDoubleProperty(dijelovi.getValue().getPartPrice()).asObject());
        dijeloviTable.setItems(observableListFiltriraniDijelovi);
        filtrirano = true;
        filtriraniDijeloviGlavni = filtriraniDijelovi;
    }
    public void filtrirajModele(){
        List<CarPart> filtriraniDijelovi;
        if (modelBox.getValue().toString().equals(""))
            filtrirano = false;
        if (filtrirano)
            filtriraniDijelovi = filtriraniDijeloviGlavni;
        else
            filtriraniDijelovi = dijelovi;
        filtriraniDijelovi = filtriraniDijelovi.stream().filter(dijelovi -> dijelovi.getCar().getModel().contains(modelBox.getValue().toString())).collect(Collectors.toList());
        ObservableList<CarPart> observableListFiltriraniDijelovi = FXCollections.observableArrayList(filtriraniDijelovi);

        kataloskiBrojColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getPartNumber()));
        modelAutaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getCar().getMake() + " " + dijelovi.getValue().getCar().getModel()));
        nazivDijelaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getName()));
        proizvodacDijelaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getPartManufactor()));
        dostupnoKomadaColumn.setCellValueFactory(dijelovi -> new SimpleIntegerProperty(dijelovi.getValue().getPartStock()).asObject());
        cijenaDijelaColumn.setCellValueFactory(dijelovi -> new SimpleDoubleProperty(dijelovi.getValue().getPartPrice()).asObject());
        dijeloviTable.setItems(observableListFiltriraniDijelovi);
        filtrirano = true;
        filtriraniDijeloviGlavni = filtriraniDijelovi;
    }
    public void filtrirajProizvodace(){
        List<CarPart> filtriraniDijelovi;
        if (proizvodacBox.getValue().toString().equals(""))
            filtrirano = false;
        if (filtrirano)
            filtriraniDijelovi = filtriraniDijeloviGlavni;
        else
            filtriraniDijelovi = dijelovi;
        filtriraniDijelovi = filtriraniDijelovi.stream().filter(dijelovi -> dijelovi.getPartManufactor().contains(proizvodacBox.getValue().toString())).collect(Collectors.toList());
        ObservableList<CarPart> observableListFiltriraniDijelovi = FXCollections.observableArrayList(filtriraniDijelovi);

        kataloskiBrojColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getPartNumber()));
        modelAutaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getCar().getMake() + " " + dijelovi.getValue().getCar().getModel()));
        nazivDijelaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getName()));
        proizvodacDijelaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getPartManufactor()));
        dostupnoKomadaColumn.setCellValueFactory(dijelovi -> new SimpleIntegerProperty(dijelovi.getValue().getPartStock()).asObject());
        cijenaDijelaColumn.setCellValueFactory(dijelovi -> new SimpleDoubleProperty(dijelovi.getValue().getPartPrice()).asObject());
        dijeloviTable.setItems(observableListFiltriraniDijelovi);
        filtrirano = true;
        filtriraniDijeloviGlavni = filtriraniDijelovi;
    }
    public void ponistiFiltriranje(){
        proizvodacBox.setValue("");
        markaBox.setValue("");
        modelBox.setValue("");

        ObservableList<CarPart> observableListDijelovi = FXCollections.observableArrayList(dijelovi);
        kataloskiBrojColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getPartNumber()));
        modelAutaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getCar().getMake() + " " + dijelovi.getValue().getCar().getModel()));
        nazivDijelaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getName()));
        proizvodacDijelaColumn.setCellValueFactory(dijelovi -> new SimpleStringProperty(dijelovi.getValue().getPartManufactor()));
        dostupnoKomadaColumn.setCellValueFactory(dijelovi -> new SimpleIntegerProperty(dijelovi.getValue().getPartStock()).asObject());
        cijenaDijelaColumn.setCellValueFactory(dijelovi -> new SimpleDoubleProperty(dijelovi.getValue().getPartPrice()).asObject());
        dijeloviTable.setItems(observableListDijelovi);
    }
}
