package hr.java.projekt.entiteti;

import hr.java.projekt.login.SessionMenager;

import java.util.ArrayList;
import java.util.List;

public class Kosarica {

    private List<CartItem> elementi;
    private String user;

    public Kosarica() {
        this.elementi = new ArrayList<CartItem>();
        this.user = SessionMenager.getUsername();
    }

    public List<CartItem> getElementi() {
        return elementi;
    }

    public void setElementi(List<CartItem> elementi) {
        this.elementi = elementi;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    public void dodajElement(CartItem novi){
        elementi.add(novi);
    }
    public void ocistiKosaricu(){
        elementi.clear();
    }
}
