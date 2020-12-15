package ir.vahidhoseini.testtraining1.utill;

public class MyCurrentLocation {
    private static MyCurrentLocation instance;
    private Double lat;
    private Double lon;
    private String city;
    private String address;

    public static MyCurrentLocation getInstance() {
        if (instance == null) {
            instance = new MyCurrentLocation();
        }
        return instance;
    }

    public double getLat() {
        if (lat == null) {
            lat = 40.742052;
        }
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        if (lon == null) {
            lon = -74.004822;
        }
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getCity() {
        if (city == null) {
            city = "NewYork";
        }
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        if (address == null) {
            address = "Racefeller center Street";
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
