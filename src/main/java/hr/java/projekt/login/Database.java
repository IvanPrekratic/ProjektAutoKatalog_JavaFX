package hr.java.projekt.login;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import hr.java.projekt.entiteti.Car;
import hr.java.projekt.entiteti.CarPart;
import hr.java.projekt.iznimke.BazaPodatakaException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public interface Database {
    Logger logger = LoggerFactory.getLogger(Database.class);
    String DATABASE_FILE = "database.properties";
    static Connection connectingToDatabase() throws IOException, SQLException, ClassNotFoundException {
        Connection conn;
        try{
            Properties properties = new Properties();
            properties.load(new FileReader(DATABASE_FILE));
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String pass = properties.getProperty("pass");
            conn = DriverManager.getConnection(url, user, pass);

        }catch (IOException e){
            logger.info("Problem kod spajanja s bazom podataka");
            throw new IOException("Error while reading properties file for DB.", e);
        }catch (SQLException e){
            logger.info("Problem kod spajanja s bazom podataka");
            throw new SQLException("Error while connecting to database.", e);
        }
        return conn;
    }

    static List<Car> dohvatiAute() throws BazaPodatakaException {
        List<Car> carList = new ArrayList<>();

        try (Connection veza = connectingToDatabase()) {
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery("SELECT * FROM AUTI");

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String marka = resultSet.getString("marka");
                String model = resultSet.getString("model");

                carList.add(new Car(id, marka, model));
            }
        } catch (SQLException | IOException | ClassNotFoundException ex) {
            logger.info("Problem pri radu s bazom podataka");
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, ex);
        }
        return carList;
    }

    static List<CarPart> dohvatiDijelove() throws BazaPodatakaException {
        List<CarPart> listaDijelova = new ArrayList<>();
        try (Connection veza = connectingToDatabase()) {
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery("SELECT * FROM DIJELOVI");

            List<Car> allCars = dohvatiAute();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Integer autoID = resultSet.getInt("auto_id");
                String kataloskiBroj = resultSet.getString("kataloski_broj");
                String naziv = resultSet.getString("naziv");
                String kategorija = resultSet.getString("kategorija");
                String proizvodac = resultSet.getString("proizvodac");
                Integer dostupnost = resultSet.getInt("dostupnost");
                Double cijena = resultSet.getDouble("cijena");

                Car c = null;
                for (Car car : allCars) {
                    if(car.getId().toString().equals(autoID.toString()))
                        c = car;
                }

                CarPart novi = new CarPart(naziv,kategorija,c,id,proizvodac,kataloskiBroj,cijena,dostupnost);
                listaDijelova.add(novi);
            }
        } catch (SQLException | IOException | ClassNotFoundException ex) {
            logger.info("Problem pri radu s bazom podataka");
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, ex);
        }
        return listaDijelova;
    }

    static void smanjiStanje(CarPart proizvod, Integer promjena) throws BazaPodatakaException {
        try (Connection veza = connectingToDatabase()) {
            PreparedStatement statement = veza.prepareStatement("update DIJELOVI set DOSTUPNOST = ? where ID = ?");
            statement.setInt(1, proizvod.getPartStock()-promjena);
            statement.setInt(2, proizvod.getId());
            statement.executeUpdate();

        } catch (SQLException | IOException | ClassNotFoundException ex) {
            logger.info("Problem pri radu s bazom podataka");
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, ex);
        }
    }
    static void povecajStanje(CarPart proizvod, Integer promjena) throws BazaPodatakaException {
        try (Connection veza = connectingToDatabase()) {
            PreparedStatement statement = veza.prepareStatement("update DIJELOVI set DOSTUPNOST = ? where ID = ?");
            statement.setInt(1, proizvod.getPartStock()+promjena);
            statement.setInt(2, proizvod.getId());
            statement.executeUpdate();

        } catch (SQLException | IOException | ClassNotFoundException ex) {
            logger.info("Problem pri radu s bazom podataka");
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, ex);
        }
    }
    static List<String> dohvatiMarke(List<CarPart> lista){
        Boolean postoji = false;
        List<String> marke = new ArrayList<>();
        marke.add("");
        for (CarPart c : lista) {
            String make = c.getCar().getMake();
            for (int i=0;i < marke.size();i++)
                if (marke.get(i).equals(make))
                    postoji = true;
            if (!postoji)
                marke.add(make);
            postoji = false;
        }
        marke.sort(String::compareToIgnoreCase);
        return marke;
    }
    static List<String> dohvatiModele(List<CarPart> lista){
        Boolean postoji = false;
        List<String> modeli = new ArrayList<>();
        modeli.add("");
        for (CarPart c : lista) {
            String model = c.getCar().getModel();
            for (int i=0;i < modeli.size();i++)
                if (modeli.get(i).equals(model))
                    postoji = true;
            if (!postoji)
                modeli.add(model);
            postoji = false;
        }
        modeli.sort(String::compareToIgnoreCase);
        return modeli;
    }
    static List<String> dohvatiKategorije(List<CarPart> lista){
        Boolean postoji = false;
        List<String> kategorije = new ArrayList<>();
        kategorije.add("");
        for (CarPart c : lista) {
            String kategorija = c.getCategory();
            for (int i=0;i < kategorije.size();i++)
                if (kategorije.get(i).equals(kategorija))
                    postoji = true;
            if (!postoji)
                kategorije.add(kategorija);
            postoji = false;
        }
        kategorije.sort(String::compareToIgnoreCase);
        return kategorije;
    }
    static List<String> dohvatiProizvodace(List<CarPart> lista){
        Boolean postoji = false;
        List<String> proizvodaci = new ArrayList<>();
        proizvodaci.add("");
        for (CarPart c : lista) {
            String proizvodac = c.getPartManufactor();
            for (int i=0;i < proizvodaci.size();i++)
                if (proizvodaci.get(i).equals(proizvodac))
                    postoji = true;
            if (!postoji)
                proizvodaci.add(proizvodac);
            postoji = false;
        }
        proizvodaci.sort(String::compareToIgnoreCase);
        return proizvodaci;
    }
    static void dodajProizvod(CarPart proizvod, Integer auto_id) throws BazaPodatakaException {
        try (Connection veza = connectingToDatabase()) {
            PreparedStatement statement = veza.prepareStatement(
                    "insert into DIJELOVI(auto_id, naziv, kategorija, proizvodac, kataloski_broj, cijena, dostupnost) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, auto_id);
            statement.setString(2, proizvod.getName());
            statement.setString(3, proizvod.getCategory());
            statement.setString(4, proizvod.getPartManufactor());
            statement.setString(5, proizvod.getPartNumber());
            statement.setDouble(6, proizvod.getPartPrice());
            statement.setInt(7, proizvod.getPartStock());
            statement.executeUpdate();

        } catch (SQLException | IOException | ClassNotFoundException ex) {
            logger.info("Problem pri radu s bazom podataka");
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, ex);
        }
    }
    static void dodajNoviAuto(Car auto) throws BazaPodatakaException {
        try (Connection veza = connectingToDatabase()) {
            PreparedStatement statement = veza.prepareStatement(
                    "insert into auti(marka, model) VALUES (?, ?)");
            statement.setString(1, auto.getMake());
            statement.setString(2, auto.getModel());
            statement.executeUpdate();

        } catch (SQLException | IOException | ClassNotFoundException ex) {
            logger.info("Problem pri radu s bazom podataka");
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, ex);
        }
    }
    static void dodajProizvodSNovimAutom(CarPart proizvod, Car auto) throws BazaPodatakaException {
        List<Car> listaAuta = dohvatiAute();
        Integer auto_id = -1;
        for (Car c: listaAuta) {
            if (c.getMake().equals(auto.getMake()) && c.getModel().equals(auto.getModel()))
                auto_id =c.getId();
        }
        try (Connection veza = connectingToDatabase()) {
            PreparedStatement statement = veza.prepareStatement(
                    "insert into DIJELOVI(auto_id, naziv, kategorija, proizvodac, kataloski_broj, cijena, dostupnost) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, auto_id);
            statement.setString(2, proizvod.getName());
            statement.setString(3, proizvod.getCategory());
            statement.setString(4, proizvod.getPartManufactor());
            statement.setString(5, proizvod.getPartNumber());
            statement.setDouble(6, proizvod.getPartPrice());
            statement.setInt(7, proizvod.getPartStock());
            statement.executeUpdate();

        } catch (SQLException | IOException | ClassNotFoundException ex) {
            logger.info("Problem pri radu s bazom podataka");
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, ex);
        }
    }
    static void obrisiProizvod(Integer id) throws BazaPodatakaException {
        try (Connection veza = connectingToDatabase()) {
            PreparedStatement statement = veza.prepareStatement(
                    "DELETE FROM DIJELOVI WHERE ID = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException ex) {
            logger.info("Problem pri radu s bazom podataka");
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, ex);
        }
    }
    static void promijeniKolicinu(Integer id, Integer kolicina) throws BazaPodatakaException {
        try (Connection veza = connectingToDatabase()) {
            PreparedStatement statement = veza.prepareStatement(
                    "UPDATE DIJELOVI SET DOSTUPNOST = ? WHERE ID = ?");
            statement.setInt(1, kolicina);
            statement.setInt(2,id);
            statement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException ex) {
            logger.info("Problem pri radu s bazom podataka");
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, ex);
        }
    }
}
