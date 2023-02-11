package hr.java.projekt.entiteti;

public class CarPart extends Item{
    private Car car;
    private String partManufactor;
    private String partNumber;
    private Double partPrice;
    private Integer partStock;

    public CarPart(Integer id, String name, String category, Car car, String partManufactor, String partNumber, Double partPrice, Integer partStock) {
        super(id, name, category);
        this.car = car;
        this.partManufactor = partManufactor;
        this.partNumber = partNumber;
        this.partPrice = partPrice;
        this.partStock = partStock;
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
