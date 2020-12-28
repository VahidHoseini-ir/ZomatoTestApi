package ir.vahidhoseini.testtraining1.model.zomato;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String name;
    private String profile_image;
    private String foodie_color;

    public User() {
    }

    protected User(Parcel in) {
        name = in.readString();
        profile_image = in.readString();
        foodie_color = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return "User{" + "name='" + name + '\'' + ", profile_image='" + profile_image + '\'' + ", foodie_color='" + foodie_color + '\'' + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getFoodie_color() {
        return foodie_color;
    }

    public void setFoodie_color(String foodie_color) {
        this.foodie_color = foodie_color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(profile_image);
        dest.writeString(foodie_color);
    }
}
