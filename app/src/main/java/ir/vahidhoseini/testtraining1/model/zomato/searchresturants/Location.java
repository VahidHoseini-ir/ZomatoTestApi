package ir.vahidhoseini.testtraining1.model.zomato.searchresturants;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;

public class Location implements Parcelable {
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "locality")
    private String locality;
    @ColumnInfo(name = "city")
    private String city;
    @ColumnInfo(name = "latitude")
    private String latitude;
    @ColumnInfo(name = "longitude")
    private String longitude;

    protected Location(Parcel in) {
        address = in.readString();
        locality = in.readString();
        city = in.readString();
        latitude = in.readString();
        longitude = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    @Override
    public String toString() {
        return "Location{" + "address='" + address + '\'' + ", locality='" + locality + '\'' + ", city='" + city + '\'' + ", latitude='" + latitude + '\'' + ", longitude='" + longitude + '\'' + '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Location() {
    }

    public Location(String address, String locality, String city, String latitude, String longitude) {
        this.address = address;
        this.locality = locality;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(address);
        parcel.writeString(locality);
        parcel.writeString(city);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
    }
}