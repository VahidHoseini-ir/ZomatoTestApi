package ir.vahidhoseini.testtraining1.model.zomato;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {
    private String name;
    private String review_text;
    private String review_time_friendly;
    private String profile_image;
    private int rating;

    public Review() {
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    protected Review(Parcel in) {
        name = in.readString();
        review_text = in.readString();
        review_time_friendly = in.readString();
        profile_image = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public String toString() {
        return "Review{" + "name='" + name + '\'' + ", review_text='" + review_text + '\'' + ", review_time_friendly='" + review_time_friendly + '\'' + ", profile_image='" + profile_image + '\'' + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String review_text) {
        this.review_text = review_text;
    }

    public String getReview_time_friendly() {
        return review_time_friendly;
    }

    public void setReview_time_friendly(String review_time_friendly) {
        this.review_time_friendly = review_time_friendly;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(review_text);
        dest.writeString(review_time_friendly);
        dest.writeString(profile_image);
    }
}