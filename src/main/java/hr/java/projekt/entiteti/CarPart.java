package hr.java.projekt.entiteti;

import java.math.BigDecimal;

public class CarPart extends Car {
    private Car car;
    private String partName;
    private String partCategory;
    private String partManufactor;
    private String partNumber;
    private BigDecimal partPrice;

    public CarPart(String make, String model, Car car, String partName, String partCategory, String partManufactor, String partNumber, BigDecimal partPrice) {
        super(make, model);
        this.car = car;
        this.partName = partName;
        this.partCategory = partCategory;
        this.partManufactor = partManufactor;
        this.partNumber = partNumber;
        this.partPrice = partPrice;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartCategory() {
        return partCategory;
    }

    public void setPartCategory(String partCategory) {
        this.partCategory = partCategory;
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

    public BigDecimal getPartPrice() {
        return partPrice;
    }

    public void setPartPrice(BigDecimal partPrice) {
        this.partPrice = partPrice;
    }
}
