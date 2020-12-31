package ir.vahidhoseini.testtraining1.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.model.zomato.Collection;
import ir.vahidhoseini.testtraining1.utill.MyApplication;
import ir.vahidhoseini.testtraining1.view.DetailCollectionActivity;

public class ViewHolderCollection extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView title, description, count_places;
    public ImageView imageView;
    private Collection mCollection;


    public ViewHolderCollection(@NonNull View itemView, OnClickListener onCollectionListener) {
        super(itemView);
        //        this.onCollectionListener = onCollectionListener;
        count_places = itemView.findViewById(R.id.count_places);
        title = itemView.findViewById(R.id.title);
        description = itemView.findViewById(R.id.description);
        imageView = itemView.findViewById(R.id.imageView);
        itemView.setOnClickListener(this);
    }

    public void setData(Collection collection) {
        mCollection = collection;
        if (!collection.getImage_url().isEmpty()) {
            Picasso.get().load(collection.getImage_url()).placeholder(R.drawable.returant_placeholder).error(R.drawable.returant_placeholder).into(imageView);
            imageView.setTag(collection.getImage_url());
        } else {
            Picasso.get().load(R.drawable.returant_placeholder).into(imageView);
        }
        title.setText(collection.getTitle());
        count_places.setText(collection.getRes_count() + " places");
        description.setText(collection.getDescription());
    }

    @Override
    public void onClick(View view) {
        //        onCollectionListener.onClickListener(Integer.parseInt(collection_id));
        //        Toast.makeText(MyApplication.currentActivity, "" + title.getText(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MyApplication.currentActivity, DetailCollectionActivity.class);
        intent.putExtra("COLLECTION", mCollection);
        MyApplication.currentActivity.startActivity(intent);
    }
}
