package hr.java.projekt.entiteti;

import java.io.Serializable;

public class Car implements Serializable {
    private Integer id;
    private String make;
    private String model;

    public Car(Integer id, String make, String model) {
        this.id = id;
        this.make = make;
        this.model = model;
    }
    public Car( String make, String model) {
        this.make = make;
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
