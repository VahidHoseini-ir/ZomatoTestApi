package ir.vahidhoseini.testtraining1.model.zomato.searchresturants;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Ignore;

import java.util.Arrays;

public class Restaurant implements Parcelable {
//    @NonNull
//    @PrimaryKey
//    private String id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "cuisines")
    private String cuisines;
    @ColumnInfo(name = "timings")
    private String timings;
    @ColumnInfo(name = "average_cost_for_two")
    private int average_cost_for_two;
    @ColumnInfo(name = "currency")
    private String currency;
    @ColumnInfo(name = "thumb")
    private String thumb;
    @Ignore
    private String[] highlights;
    @ColumnInfo(name = "featured_image")
    private String featured_image;
    @ColumnInfo(name = "phone_numbers")
    private String phone_numbers;
    @Embedded
    private Location location;


    protected Restaurant(Parcel in) {
        name = in.readString();
        cuisines = in.readString();
        timings = in.readString();
        average_cost_for_two = in.readInt();
        currency = in.readString();
        highlights = in.createStringArray();
        featured_image = in.readString();
        phone_numbers = in.readString();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    @Override
    public String toString() {
        return "Restaurant{" + ", name='" + name + '\'' + ", cuisines='" + cuisines + '\'' + ", timings='" + timings + '\'' + ", average_cost_for_two=" + average_cost_for_two + ", currency='" + currency + '\'' + ", thumb='" + thumb + '\'' + ", highlights=" + Arrays.toString(highlights) + ", featured_image='" + featured_image + '\'' + ", phone_numbers='" + phone_numbers + '\'' + ", location=" + location + '}';
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public int getAverage_cost_for_two() {
        return average_cost_for_two;
    }

    public void setAverage_cost_for_two(int average_cost_for_two) {
        this.average_cost_for_two = average_cost_for_two;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String[] getHighlights() {
        return highlights;
    }

    public void setHighlights(String[] highlights) {
        this.highlights = highlights;
    }

    public String getFeatured_image() {
        return featured_image;
    }

    public void setFeatured_image(String featured_image) {
        this.featured_image = featured_image;
    }

    public String getPhone_numbers() {
        return phone_numbers;
    }

    public void setPhone_numbers(String phone_numbers) {
        this.phone_numbers = phone_numbers;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Restaurant() {
    }

    public Restaurant( String name, String cuisines, String timings, int average_cost_for_two, String currency, String thumb, String[] highlights, String featured_image, String phone_numbers, Location location) {
        this.name = name;
        this.cuisines = cuisines;
        this.timings = timings;
        this.average_cost_for_two = average_cost_for_two;
        this.currency = currency;
        this.thumb = thumb;
        this.highlights = highlights;
        this.featured_image = featured_image;
        this.phone_numbers = phone_numbers;
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(cuisines);
        parcel.writeString(timings);
        parcel.writeInt(average_cost_for_two);
        parcel.writeString(currency);
        parcel.writeStringArray(highlights);
        parcel.writeString(featured_image);
        parcel.writeString(phone_numbers);
    }
}
