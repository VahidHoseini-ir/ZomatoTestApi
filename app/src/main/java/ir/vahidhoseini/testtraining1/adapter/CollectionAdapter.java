package ir.vahidhoseini.testtraining1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.model.zomato.Collection;
import ir.vahidhoseini.testtraining1.view.CollectionActivity;

public class CollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Collection> collections;
    public int viewHolderType;
    public final int CollectionView = 2;
    private OnClickListener onClickListener;

    public CollectionAdapter(OnClickListener onCollectionClickListener) {
        this.onClickListener = onCollectionClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case CollectionView: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_collection, parent, false);
                return new CollectionViewHolder(view, onClickListener);
            }
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == CollectionView) {
            if (!collections.get(position).getImage_url().isEmpty()) {
                Picasso.get().load(collections.get(position).getImage_url()).placeholder(R.drawable.returant_placeholder).error(R.drawable.returant_placeholder).into(((CollectionViewHolder) holder).imageView);
            } else {
                Picasso.get().load(R.drawable.returant_placeholder).into(((CollectionViewHolder) holder).imageView);
            }
            ((CollectionViewHolder) holder).title.setText(collections.get(position).getTitle());
            ((CollectionViewHolder) holder).description.setText(collections.get(position).getDescription());
        }

    }

    public int getCollectionSize() {
        return collections.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (viewHolderType == CollectionView) {
            return CollectionView;
        }
        return 0;
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
        notifyDataSetChanged();
    }


    public void setViewType(int collectionView) {
        viewHolderType = collectionView;
    }
}
