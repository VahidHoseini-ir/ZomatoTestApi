package ir.vahidhoseini.testtraining1.model.zomato;

import android.os.Parcel;
import android.os.Parcelable;

public class Categories implements Parcelable {
    private Category categories;

    protected Categories(Parcel in) {
        categories = in.readParcelable(Category.class.getClassLoader());
    }

    public static final Creator<Categories> CREATOR = new Creator<Categories>() {
        @Override
        public Categories createFromParcel(Parcel in) {
            return new Categories(in);
        }

        @Override
        public Categories[] newArray(int size) {
            return new Categories[size];
        }
    };

    @Override
    public String toString() {
        return "Categories{" + "category=" + categories + '}';
    }

    public Category getCategories() {
        return categories;
    }

    public void setCategories(Category categories) {
        this.categories = categories;
    }

    public Categories() {
    }

    public Categories(Category category) {
        this.categories = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(categories, i);
    }
}
