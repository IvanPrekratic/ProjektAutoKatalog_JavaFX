package hr.java.projekt.login;

import hr.java.projekt.entiteti.Car;
import hr.java.projekt.entiteti.CarPart;
import hr.java.projekt.iznimke.BazaPodatakaException;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public interface Database {

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
            throw new IOException("Error while reading properties file for DB.", e);
        }catch (SQLException e){
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

                CarPart part = new CarPart(id, naziv, kategorija, c, proizvodac, kataloskiBroj, cijena, dostupnost);
                listaDijelova.add(part);
            }
        } catch (SQLException | IOException | ClassNotFoundException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, ex);
        }

        return listaDijelova;
    }

    static void azurirajStanje(CarPart proizvod, Integer promjena) throws BazaPodatakaException {
        try (Connection veza = connectingToDatabase()) {
            PreparedStatement statement = veza.prepareStatement("update DIJELOVI set DOSTUPNOST = ? where ID = ?");
            statement.setInt(1, proizvod.getPartStock()-promjena);
            statement.setInt(2, proizvod.getId());

        } catch (SQLException | IOException | ClassNotFoundException ex) {
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
        return proizvodaci;
    }
}
