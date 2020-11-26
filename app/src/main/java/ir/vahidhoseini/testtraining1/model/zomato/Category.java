package ir.vahidhoseini.testtraining1.model.zomato;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {
    private String id;
    private String name;

    public Category(String category_id, String category_name) {
        this.id = category_id;
        this.name = category_name;
    }

    public Category() {
    }

    protected Category(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    @Override
    public String toString() {
        return "categories{" + "category_id='" + id + '\'' + ", category_name='" + name + '\'' + '}';
    }

    public String getCategory_id() {
        return id;
    }

    public void setCategory_id(String category_id) {
        this.id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
    }
}
