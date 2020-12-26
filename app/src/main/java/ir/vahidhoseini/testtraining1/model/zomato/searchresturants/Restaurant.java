package ir.vahidhoseini.testtraining1.model.zomato.searchresturants;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Restaurant implements Parcelable {

    private String name;
    private String cuisines;
    private String timings;
    private int average_cost_for_two;
    private String currency;
    private String thumb;
    private String[] highlights;
    private String[] establishment;
    private String featured_image;
    private String phone_numbers;
    private Location location;
    private Rate user_rating;
    private Object R;
    private int has_online_delivery;
    private int is_delivering_now;
    private int is_table_reservation_supported;
    private int has_table_booking;
    private int opentable_support;
    private int is_zomato_book_res;
    private int is_book_form_web_view;
    private String book_form_web_view_url;
    private int all_reviews_count;

    public Restaurant() {
    }

    protected Restaurant(Parcel in) {
        name = in.readString();
        cuisines = in.readString();
        timings = in.readString();
        average_cost_for_two = in.readInt();
        currency = in.readString();
        thumb = in.readString();
        highlights = in.createStringArray();
        establishment = in.createStringArray();
        featured_image = in.readString();
        phone_numbers = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
        user_rating = in.readParcelable(Rate.class.getClassLoader());
        has_online_delivery = in.readInt();
        is_delivering_now = in.readInt();
        is_table_reservation_supported = in.readInt();
        has_table_booking = in.readInt();
        opentable_support = in.readInt();
        is_zomato_book_res = in.readInt();
        is_book_form_web_view = in.readInt();
        book_form_web_view_url = in.readString();
        all_reviews_count = in.readInt();
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
        return "Restaurant{" + "name='" + name + '\'' + ", cuisines='" + cuisines + '\'' + ", timings='" + timings + '\'' + ", average_cost_for_two=" + average_cost_for_two + ", currency='" + currency + '\'' + ", thumb='" + thumb + '\'' + ", highlights=" + Arrays.toString(highlights) + ", establishment=" + Arrays.toString(establishment) + ", featured_image='" + featured_image + '\'' + ", phone_numbers='" + phone_numbers + '\'' + ", location=" + location + ", user_rating=" + user_rating + ", R=" + R + ", has_online_delivery=" + has_online_delivery + ", is_delivering_now=" + is_delivering_now + ", is_table_reservation_supported=" + is_table_reservation_supported + ", has_table_booking=" + has_table_booking + ", opentable_support=" + opentable_support + ", is_zomato_book_res=" + is_zomato_book_res + ", is_book_form_web_view=" + is_book_form_web_view + ", book_form_web_view_url='" + book_form_web_view_url + '\'' + ", all_reviews_count=" + all_reviews_count + '}';
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

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String[] getHighlights() {
        return highlights;
    }

    public void setHighlights(String[] highlights) {
        this.highlights = highlights;
    }

    public String[] getEstablishment() {
        return establishment;
    }

    public void setEstablishment(String[] establishment) {
        this.establishment = establishment;
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

    public Rate getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(Rate user_rating) {
        this.user_rating = user_rating;
    }

    public Object getR() {
        return R;
    }

    public void setR(Object r) {
        R = r;
    }

    public int getHas_online_delivery() {
        return has_online_delivery;
    }

    public void setHas_online_delivery(int has_online_delivery) {
        this.has_online_delivery = has_online_delivery;
    }

    public int getIs_delivering_now() {
        return is_delivering_now;
    }

    public void setIs_delivering_now(int is_delivering_now) {
        this.is_delivering_now = is_delivering_now;
    }

    public int getIs_table_reservation_supported() {
        return is_table_reservation_supported;
    }

    public void setIs_table_reservation_supported(int is_table_reservation_supported) {
        this.is_table_reservation_supported = is_table_reservation_supported;
    }

    public int getHas_table_booking() {
        return has_table_booking;
    }

    public void setHas_table_booking(int has_table_booking) {
        this.has_table_booking = has_table_booking;
    }

    public int getOpentable_support() {
        return opentable_support;
    }

    public void setOpentable_support(int opentable_support) {
        this.opentable_support = opentable_support;
    }

    public int getIs_zomato_book_res() {
        return is_zomato_book_res;
    }

    public void setIs_zomato_book_res(int is_zomato_book_res) {
        this.is_zomato_book_res = is_zomato_book_res;
    }

    public int getIs_book_form_web_view() {
        return is_book_form_web_view;
    }

    public void setIs_book_form_web_view(int is_book_form_web_view) {
        this.is_book_form_web_view = is_book_form_web_view;
    }

    public String getBook_form_web_view_url() {
        return book_form_web_view_url;
    }

    public void setBook_form_web_view_url(String book_form_web_view_url) {
        this.book_form_web_view_url = book_form_web_view_url;
    }

    public int getAll_reviews_count() {
        return all_reviews_count;
    }

    public void setAll_reviews_count(int all_reviews_count) {
        this.all_reviews_count = all_reviews_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(cuisines);
        dest.writeString(timings);
        dest.writeInt(average_cost_for_two);
        dest.writeString(currency);
        dest.writeString(thumb);
        dest.writeStringArray(highlights);
        dest.writeStringArray(establishment);
        dest.writeString(featured_image);
        dest.writeString(phone_numbers);
        dest.writeParcelable(location, flags);
        dest.writeParcelable(user_rating, flags);
        dest.writeInt(has_online_delivery);
        dest.writeInt(is_delivering_now);
        dest.writeInt(is_table_reservation_supported);
        dest.writeInt(has_table_booking);
        dest.writeInt(opentable_support);
        dest.writeInt(is_zomato_book_res);
        dest.writeInt(is_book_form_web_view);
        dest.writeString(book_form_web_view_url);
        dest.writeInt(all_reviews_count);
    }
}
