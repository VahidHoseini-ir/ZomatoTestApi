package ir.vahidhoseini.testtraining1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.viewmodel.MainViewModel;

public class ListRecyclerViewsAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context Context;
    private Map<Integer, Integer> mWhichCategoryList = new HashMap<>();
    private Map<Integer, String> mWhichResturanList = new HashMap<>();
    private Map<Integer, List<Restaurants>> mAllListOfResturants = new HashMap<>();
    private int mPosition = 0;
    private final int mRecyclerviewType = 1, mLoadingviewType = 2, mBannerView = 3;
    private OnClickListener onMoreClickListener;

    public ListRecyclerViewsAdapater(OnClickListener onMoreClickListener,  Context context) {
        this.Context = context;
        this.onMoreClickListener = onMoreClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        switch (viewType) {
            case mRecyclerviewType: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_recylerview, viewGroup, false);
                return new RecyclerviewViewHolder(view, onMoreClickListener);
            }
            case mLoadingviewType: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.loading_progress, viewGroup, false);
                return new LoadingViewHolder(view);
            }
            case mBannerView: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_bannerview, viewGroup, false);
                return new BannerViewHolder(view);
            }

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == mRecyclerviewType) {
            if (mAllListOfResturants.get(position) != null) {
                ((RecyclerviewViewHolder) holder).setData(mAllListOfResturants.get(position), mWhichResturanList.get(position), mWhichCategoryList.get(position));
            }
        } else if (getItemViewType(position) == mBannerView) {
            ((BannerViewHolder) holder).mBannerView.setImageDrawable(Context.getDrawable(R.drawable.restaurant_banner));
        }

    }

    @Override
    public int getItemCount() {
        if (mAllListOfResturants != null) {
            return mAllListOfResturants.size();

        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (mWhichResturanList != null) {
            if (mWhichResturanList.get(position).equals("LOADING...")) {
                return mLoadingviewType;
            } else if (mWhichResturanList.get(position).equals("BANNER...")) {
                return mBannerView;
            } else if (mWhichResturanList.get(position) != null) {
                return mRecyclerviewType;
            }
        }
        return 0;
    }

    public void showLoading() {
        mAllListOfResturants.put(mPosition, null);
        mWhichResturanList.put(mPosition, "LOADING...");
        notifyDataSetChanged();
    }

    public void setBannerView() {
        if (mAllListOfResturants.get(mPosition) == null) {
            mAllListOfResturants.remove(mPosition);
            mWhichResturanList.remove(mPosition);
        }
        mAllListOfResturants.put(mPosition, null);
        mWhichResturanList.put(mPosition, "BANNER...");
        mPosition++;
        notifyDataSetChanged();

    }

    public void addListOfResturants(List<Restaurants> listOfResturants, int idListOfResturant, String nameListOfResturant) {
        if (mAllListOfResturants.get(mPosition) == null) {
            mAllListOfResturants.remove(mPosition);
            mWhichResturanList.remove(mPosition);
        }
        mWhichResturanList.put(mPosition, nameListOfResturant);
        mAllListOfResturants.put(mPosition, listOfResturants);
        mWhichCategoryList.put(mPosition, idListOfResturant);
        mPosition++;
        notifyDataSetChanged();
    }
}
