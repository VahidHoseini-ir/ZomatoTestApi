package ir.vahidhoseini.testtraining1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.model.zomato.Collections;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;

public class AdapterMain extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnClickListener onCollectionListener;
    private OnClickListener onListItemListener;
    Map<Integer, Integer> mainData = new HashMap<>();
    List<Collections> collections;
    List<Restaurants> restaurants;
    private final int LoadingView = 1, CollectionView = 2, ResturantView = 3;

    public AdapterMain(OnClickListener mainListListener) {
        onListItemListener = mainListListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_recylerview, parent, false);
        return new ViewHolderRecyclerview(view, onListItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case CollectionView: {
                ((ViewHolderRecyclerview) holder).setCollection(collections , onCollectionListener);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return CollectionView;

    }

    @Override
    public int getItemCount() {
        return mainData.size();
    }

    public void setCuratedCollections(List<Collections> c , OnClickListener o) {
        onCollectionListener = o;
        if (mainData.get(1) != null) {
            mainData.remove(1);
        }
        mainData.put(1, CollectionView);
        collections = c;
        notifyDataSetChanged();
    }
}
