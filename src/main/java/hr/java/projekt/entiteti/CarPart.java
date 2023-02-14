package hr.java.projekt.entiteti;

import java.io.Serializable;

public class CarPart extends Item implements Serializable {
    private Car car;
    private Integer id;
    private String partManufactor;
    private String partNumber;
    private Double partPrice;
    private Integer partStock;

    public CarPart(String name, String category, Car car, Integer id, String partManufactor, String partNumber, Double partPrice, Integer partStock) {
        super(name, category);
        this.car = car;
        this.id = id;
        this.partManufactor = partManufactor;
        this.partNumber = partNumber;
        this.partPrice = partPrice;
        this.partStock = partStock;
    }

    public CarPart(String name, String category, Car car, String partManufactor, String partNumber, Double partPrice, Integer partStock) {
        super(name, category);
        this.car = car;
        this.partManufactor = partManufactor;
        this.partNumber = partNumber;
        this.partPrice = partPrice;
        this.partStock = partStock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getPartManufactor() {
        return partManufactor;
    }

    public void setPartManufactor(String partManufactor) {
        this.partManufactor = partManufactor;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public Double getPartPrice() {
        return partPrice;
    }

    public void setPartPrice(Double partPrice) {
        this.partPrice = partPrice;
    }

    public Integer getPartStock() {
        return partStock;
    }

    public void setPartStock(Integer partStock) {
        this.partStock = partStock;
    }
    public boolean isti(CarPart drugi) {
        if (this.equals(drugi))
            return true;
        else
            return false;
    }

}
