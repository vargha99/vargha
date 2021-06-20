import java.io.Serializable;

public class Address implements Cloneable, Comparable<Address>, Serializable {
    private int streetNum;
    private String street;
    private String suburb;
    private String city;

    Address(int streetNum, String street, String suburb, String city) {
        this.streetNum = streetNum;
        this.street = street;
        this.suburb = suburb;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void print() {
        System.out.println("Address:" + streetNum + " " + street + ", " + suburb + ", " + city);
    }

    public String toString() {
        return "Street Number:" + streetNum + " Street:" + street + " Suburb:" + suburb + " City:" + city;
    }

    //Lab 4
    public Address(Address address) {
        this.streetNum = address.streetNum;
        this.street = address.street;
        this.suburb = address.suburb;
        this.city = address.city;
    }

    @Override
    public Address clone() throws CloneNotSupportedException {
        return (Address) super.clone();
    }

    public int compareTo(Address otherAddress) {
        return this.city.compareTo(otherAddress.city);
    }

    //Lab6
    public String toDelimitedString() {
        return streetNum + "," + street + "," + suburb + "," + city;
    }
}
