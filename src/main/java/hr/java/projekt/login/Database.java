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
}
