package ir.vahidhoseini.testtraining1.model.zomato;

import android.os.Parcel;
import android.os.Parcelable;

public class Main implements Parcelable {
    private int viewholder;
    private Object object;

    public Main() {
    }

    public Main(Object object) {
        this.object = object;

    }


    public Main(int viewholder, Object object) {
        this.viewholder = viewholder;
        this.object = object;
    }

    protected Main(Parcel in) {
        viewholder = in.readInt();
    }

    public static final Creator<Main> CREATOR = new Creator<Main>() {
        @Override
        public Main createFromParcel(Parcel in) {
            return new Main(in);
        }

        @Override
        public Main[] newArray(int size) {
            return new Main[size];
        }
    };

    public int getViewholder() {
        return viewholder;
    }

    public void setViewholder(int viewholder) {
        this.viewholder = viewholder;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(viewholder);
    }
}
