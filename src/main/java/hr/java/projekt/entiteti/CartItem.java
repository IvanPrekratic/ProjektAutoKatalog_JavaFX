package hr.java.projekt.entiteti;

public class CartItem {
    private CarPart proizvod;
    private Integer kolicina;

    public CartItem(CarPart proizvod, Integer kolicina) {
        this.proizvod = proizvod;
        this.kolicina = kolicina;
    }

    public CarPart getProizvod() {
        return proizvod;
    }

    public void setProizvod(CarPart proizvod) {
        this.proizvod = proizvod;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

}
