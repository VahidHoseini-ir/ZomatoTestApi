package ir.vahidhoseini.testtraining1.model.zomato;

import android.os.Parcel;
import android.os.Parcelable;


public class Collections implements Parcelable {
    private Collection collection;
    private int viewType;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public Collections() {
    }

    protected Collections(Parcel in) {
        collection = in.readParcelable(Collection.class.getClassLoader());
    }

    public static final Creator<Collections> CREATOR = new Creator<Collections>() {
        @Override
        public Collections createFromParcel(Parcel in) {
            return new Collections(in);
        }

        @Override
        public Collections[] newArray(int size) {
            return new Collections[size];
        }
    };

    public Collection getCollection() {
        return collection;
    }

    @Override
    public String toString() {
        return "Collections{" + "collection=" + collection + '}';
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Collections(Collection collection) {
        this.collection = collection;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(collection, i);
    }
}
