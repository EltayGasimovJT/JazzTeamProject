package entity;

public class ParcelParameters {
    private double height;
    private double width;
    private double length;
    private double weight;

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public ParcelParameters(double height, double width, double length, double weight) {
        this.height = height;
        this.width = width;
        this.length = length;
        this.weight = weight;
    }
}
