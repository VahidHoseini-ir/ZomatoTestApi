package ir.vahidhoseini.testtraining1.utill;

public class MyCurrentLocation {
    private static MyCurrentLocation instance;
    private Double lat;
    private Double lon;
    private String city;
    private String city_id;
    private String address;

    public static MyCurrentLocation getInstance() {
        if (instance == null) {
            instance = new MyCurrentLocation();
        }
        return instance;
    }

    public double getLat() {
        if (lat == null) {
            lat = 40.742052;  // lat of new yourk city
        }
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        if (lon == null) {
            lon = -74.004822;  // lon of new yourk city
        }
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getCity_id() {
        if (city_id == null || city_id.isEmpty()) {
            city_id = "280"; // city id of new yourk city
        }
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity() {
        if (city == null || city.isEmpty()) {
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
