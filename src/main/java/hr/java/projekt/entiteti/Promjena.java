package hr.java.projekt.entiteti;

import java.util.ArrayList;
import java.util.List;

public class Promjena<T> {
    private List<T> elementiPromjene;

    public Promjena() {
        this.elementiPromjene = new ArrayList<>();
    }
    public List<T> getElementiPromjene() {
        return elementiPromjene;
    }
    public void setElementiPromjene(List<T> elementiPromjene) {
        this.elementiPromjene = elementiPromjene;
    }
    public void dodajElement(T novi){
        this.elementiPromjene.add(novi);
    }
}
