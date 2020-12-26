package ir.vahidhoseini.testtraining1.model.zomato.searchresturants;

import android.os.Parcel;
import android.os.Parcelable;

public class Rate implements Parcelable {
    private String aggregate_rating;
    private String rating_text;
    private String rating_color;
    private int votes;

    protected Rate(Parcel in) {
        aggregate_rating = in.readString();
        rating_text = in.readString();
        rating_color = in.readString();
        votes = in.readInt();
    }

    public static final Creator<Rate> CREATOR = new Creator<Rate>() {
        @Override
        public Rate createFromParcel(Parcel in) {
            return new Rate(in);
        }

        @Override
        public Rate[] newArray(int size) {
            return new Rate[size];
        }
    };

    @Override
    public String toString() {
        return "Rate{" + "aggregate_rating='" + aggregate_rating + '\'' + ", rating_text='" + rating_text + '\'' + ", rating_color='" + rating_color + '\'' + ", votes=" + votes + '}';
    }

    public Rate() {
    }

    public String getAggregate_rating() {
        return aggregate_rating;
    }

    public void setAggregate_rating(String aggregate_rating) {
        this.aggregate_rating = aggregate_rating;
    }

    public String getRating_text() {
        return rating_text;
    }

    public void setRating_text(String rating_text) {
        this.rating_text = rating_text;
    }

    public String getRating_color() {
        return rating_color;
    }

    public void setRating_color(String rating_color) {
        this.rating_color = rating_color;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(aggregate_rating);
        dest.writeString(rating_text);
        dest.writeString(rating_color);
        dest.writeInt(votes);
    }
}
