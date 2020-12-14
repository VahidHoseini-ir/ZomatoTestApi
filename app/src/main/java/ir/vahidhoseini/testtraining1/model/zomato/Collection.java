package ir.vahidhoseini.testtraining1.model.zomato;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

public class Collection implements Parcelable {

    private String res_count;
    private String collection_id;
    private String title;
    private String url;
    private String description;
    private String image_url;
    private String share_url;


    public Collection() {
    }

    protected Collection(Parcel in) {
        res_count = in.readString();
        collection_id = in.readString();
        title = in.readString();
        url = in.readString();
        description = in.readString();
        image_url = in.readString();
        share_url = in.readString();
    }

    public static final Creator<Collection> CREATOR = new Creator<Collection>() {
        @Override
        public Collection createFromParcel(Parcel in) {
            return new Collection(in);
        }

        @Override
        public Collection[] newArray(int size) {
            return new Collection[size];
        }
    };

    @Override
    public String toString() {
        return "Collection{" + "res_count='" + res_count + '\'' + ", collection_id='" + collection_id + '\'' + ", title='" + title + '\'' + ", url='" + url + '\'' + ", description='" + description + '\'' + ", image_url='" + image_url + '\'' + ", share_url='" + share_url + '\'' + '}';
    }

    public Collection(String res_count, String collection_id, String title, String url, String description, String image_url, String share_url) {
        this.res_count = res_count;
        this.collection_id = collection_id;
        this.title = title;
        this.url = url;
        this.description = description;
        this.image_url = image_url;
        this.share_url = share_url;
    }

    public String getRes_count() {
        return res_count;
    }

    public void setRes_count(String res_count) {
        this.res_count = res_count;
    }

    public String getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(String collection_id) {
        this.collection_id = collection_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(res_count);
        parcel.writeString(collection_id);
        parcel.writeString(title);
        parcel.writeString(url);
        parcel.writeString(description);
        parcel.writeString(image_url);
        parcel.writeString(share_url);
    }
}
