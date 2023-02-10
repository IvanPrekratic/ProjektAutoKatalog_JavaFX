package hr.java.projekt.entiteti;

import hr.java.projekt.login.SessionMenager;

import java.util.ArrayList;
import java.util.List;

public class Kosarica {

    public static List<CarPart> proizvodi = new ArrayList<>();
    public static List<Integer> kolicina = new ArrayList<>();
    private String user = SessionMenager.getUsername();

    public Kosarica(List<CarPart> kosarica, List<Integer> kolicina) {
        proizvodi = kosarica;
        this.kolicina = kolicina;

    }

    public Kosarica() {
        this.user = SessionMenager.getUsername();
    }

    public List<CarPart> getProizvodi() {
        return proizvodi;
    }

    public void setProizvodi(List<CarPart> proizvodi) {
        this.proizvodi = proizvodi;
    }

    public List<Integer> getKolicina() {
        return kolicina;
    }

    public void setKolicina(List<Integer> kolicina) {
        this.kolicina = kolicina;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
