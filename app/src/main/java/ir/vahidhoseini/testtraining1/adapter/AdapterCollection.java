package ir.vahidhoseini.testtraining1.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.model.zomato.Collections;
import ir.vahidhoseini.testtraining1.utill.MyApplication;

public class AdapterCollection extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Collections> collections;
    private OnClickListener onClickListener;

    public AdapterCollection(OnClickListener onCollectionClickListener) {
        this.onClickListener = onCollectionClickListener;
        collections = new ArrayList<>();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_collection, parent, false);
        if (ifListIsInMainActivity) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = 450;
            layoutParams.height = 450;
            view.setLayoutParams(layoutParams);
        }else{
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = 600;
            view.setLayoutParams(layoutParams);
        }
        return new ViewHolderCollection(view, onClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (!collections.get(position).getCollection().getImage_url().isEmpty()) {
            Picasso.get().load(collections.get(position).getCollection().getImage_url()).placeholder(R.drawable.returant_placeholder).error(R.drawable.returant_placeholder).into(((ViewHolderCollection) holder).imageView);
        } else {
            Picasso.get().load(R.drawable.returant_placeholder).into(((ViewHolderCollection) holder).imageView);
        }
        ((ViewHolderCollection) holder).title.setText(collections.get(position).getCollection().getTitle());
        ((ViewHolderCollection) holder).count_places.setText(collections.get(position).getCollection().getRes_count() + " places");
        ((ViewHolderCollection) holder).collection_id = collections.get(position).getCollection().getCollection_id();
        if (!ifListIsInMainActivity) {
            ((ViewHolderCollection) holder).description.setVisibility(View.VISIBLE);
            ((ViewHolderCollection) holder).description.setText(collections.get(position).getCollection().getDescription());
            ((ViewHolderCollection) holder).count_places.setCompoundDrawablesWithIntrinsicBounds( 0 , 0 , R.drawable.ic_baseline_double_arrow_24 , 0);
            ((ViewHolderCollection) holder).title.setPadding(16, 0 , 16, 0);
            ((ViewHolderCollection) holder).description.setPadding(16, 0 , 16, 0);
            ((ViewHolderCollection) holder).count_places.setPadding(16, 0 , 16, 16);

        }else{
            ((ViewHolderCollection) holder).title.setTextSize(12);
            ((ViewHolderCollection) holder).title.setPadding(20, 0 , 20, 0);
            ((ViewHolderCollection) holder).count_places.setTextSize(10);
            ((ViewHolderCollection) holder).count_places.setPadding(20, 0 , 20, 20);
        }

    }


    @Override
    public int getItemCount() {
        if (collections.size() > 0) {
            return collections.size();
        }
        return 0;
    }

    public void setCollections(List<Collections> collections) {
        ifListIsInMainActivity = false;
        Log.e("TAG", "setCollections :" + collections.toString());
        this.collections = collections;
        notifyDataSetChanged();
    }

    private boolean ifListIsInMainActivity = false;

    public void setCollectionsInMainActivity(List<Collections> collections) {
        ifListIsInMainActivity = true;
        Log.e("TAG", "setCollections :" + collections.toString());
        this.collections = collections;
        notifyDataSetChanged();
    }

}