package ir.vahidhoseini.testtraining1.model.zomato.searchresturants;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "resturant")
public class Restaurants implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @Embedded
    private Restaurant restaurant;

    private String results_found;
    private String results_start;
    private int viewType;

    public String getResults_found() {
        return results_found;
    }

    public void setResults_found(String results_found) {
        this.results_found = results_found;
    }

    public String getResults_start() {
        return results_start;
    }

    public void setResults_start(String results_start) {
        this.results_start = results_start;
    }

    @Override
    public String toString() {
        return "Restaurants{" + "restaurant=" + restaurant + ", results_found='" + results_found + '\'' + ", results_start='" + results_start + '\'' + ", viewType=" + viewType + '}';
    }

    public Restaurants(Restaurant restaurant, String results_found, String results_start) {
        this.restaurant = restaurant;
        this.results_found = results_found;
        this.results_start = results_start;
    }

    public Restaurants() {
    }


    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    protected Restaurants(Parcel in) {
    }

    public static final Creator<Restaurants> CREATOR = new Creator<Restaurants>() {
        @Override
        public Restaurants createFromParcel(Parcel in) {
            return new Restaurants(in);
        }

        @Override
        public Restaurants[] newArray(int size) {
            return new Restaurants[size];
        }
    };


    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
