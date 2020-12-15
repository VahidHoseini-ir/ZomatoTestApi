package ir.vahidhoseini.testtraining1.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.model.zomato.Collection;
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
        View view = null;
        Log.e("TAG", "onCreateViewHolder: viewType =" + viewType);

        switch (viewType) {
            case LoadingView: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_progress, parent, false);
                return new ViewHolderLoading(view);
            }
            default: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_recylerview, parent, false);
                return new ViewHolderRecyclerview(view, onListItemListener);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case CollectionView: {
                ((ViewHolderRecyclerview) holder).setCollection(collections, onCollectionListener);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mainData.size()-1 == position && mainData.get(mainData.size()-1) == LoadingView) {
            return LoadingView;
        } else if (mainData.get(position) == CollectionView) {
            return CollectionView;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mainData.size();
    }

    public int mainDataSize() {
        return Math.max(mainData.size(), 0);
    }

    public void displayLoading(boolean newQuery) {
        if (newQuery) {
            removeData();
            mainData.put(mainDataSize(), LoadingView);
            notifyDataSetChanged();
        } else {
            if (!isLoading()) {
                Log.e("TAG", "displayLoading: mainDataSize = " + mainDataSize());
                mainData.put(mainDataSize(), LoadingView);
                notifyDataSetChanged();
            }
        }
    }


    private boolean isLoading() {
        if (mainData != null) {
            if (mainData.size() > 0) {
                if (mainData.get(mainData.size() - 1) == LoadingView) {
                    return true;
                }
            }
        }
        return false;
    }

    private void removeData() {
        for (int i = 0; i < mainData.size(); i++) {
            mainData.remove(i);
        }
        notifyDataSetChanged();
    }

    private void removeLoading() {
        for (int i = 0; i < mainData.size(); i++) {
            if (mainData.get(i) == LoadingView) {
                mainData.remove(i);
            }
        }
    }

    public void setCuratedCollections(List<Collections> c, OnClickListener o) {
        onCollectionListener = o;
        removeLoading();
        mainData.put(mainDataSize(), CollectionView);
        collections = c;
        notifyDataSetChanged();
    }


}
