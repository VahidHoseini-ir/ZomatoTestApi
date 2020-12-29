package ir.vahidhoseini.testtraining1.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.model.zomato.Collections;
import ir.vahidhoseini.testtraining1.model.zomato.Main;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;

public class AdapterMain extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "AdapterMain";
    private OnClickListener onCollectionListener;
    private OnClickListener onListItemListener;
    private List<Main> mainData = new ArrayList<>();
    private int HowManyItemsIsInListExceptResturanList;// position of resturant list is not the main data list size becuase we have add currated collection;
    private final int ExhaustedView = 4, LoadingView = 1, CollectionView = 2, ResturantView = 3;

    public AdapterMain(OnClickListener mainListListener) {
        onListItemListener = mainListListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        Log.e(TAG, TAG + " : onCreateViewHolder: viewType =" + viewType);

        switch (viewType) {
            case LoadingView: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_progress, parent, false);
                return new ViewHolderLoading(view);
            }
            case ExhaustedView: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_viewexhausted, parent, false);
                return new ViewHolderLoading(view);
            }
            case ResturantView: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_resturant, parent, false);
                return new ViewHolderResturant(view, onListItemListener, 0);
            }
            case CollectionView: {
                HowManyItemsIsInListExceptResturanList++;
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_recylerview, parent, false);
                return new ViewHolderRecyclerview(view, onListItemListener);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int view_type = getItemViewType(position);
        if (view_type == CollectionView) {
            ((ViewHolderRecyclerview) holder).setCollection(((List<Collections>) mainData.get(position).getObject()), onCollectionListener);
        } else if (view_type == ResturantView) {
            ((ViewHolderResturant) holder).setResturant(((Restaurants) mainData.get(position).getObject()));
        } else if (view_type == ExhaustedView) {
            if (mainData.size() > 1) {
                ((ViewHolderLoading) holder).textview.setText("No more items found");

            } else {
                ((ViewHolderLoading) holder).textview.setText("What are you looking for?, no items found.");

            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mainData.size() - 1 && mainData.get(position).getViewholder() == LoadingView) {
            return LoadingView;
        } else if (position == mainData.size() - 1 && mainData.get(position).getViewholder() == ExhaustedView) {
            return ExhaustedView;
        } else if (mainData.get(position).getViewholder() == CollectionView && mainData.get(position).getObject() instanceof ArrayList) {
            return CollectionView;
        } else if (mainData.get(position).getViewholder() == ResturantView && mainData.get(position).getObject() instanceof Restaurants) {
            return ResturantView;
        } else {
            removeData();
            Main main = new Main();
            main.setViewholder(LoadingView);
            main.setObject(null);
            mainData.add(main);
            return LoadingView;
        }
    }

    @Override
    public int getItemCount() {
        return mainData.size();
    }

    public void displayLoading(boolean newQuery) {
        if (newQuery) {
            removeData();
            Main main = new Main();
            main.setViewholder(LoadingView);
            main.setObject(null);
            mainData.add(main);
            notifyDataSetChanged();

        } else {
            if (!isLoading()) {
                Main main = new Main();
                main.setViewholder(LoadingView);
                main.setObject(null);
                mainData.add(main);
                notifyDataSetChanged();
            }
        }
    }

    public void displayExhausted() {
        removeLoading();
        Main main = new Main();
        main.setViewholder(ExhaustedView);
        main.setObject(null);
        mainData.add(main);
        notifyDataSetChanged();
    }

    private boolean isLoading() {
        if (mainData != null) {
            if (mainData.size() > 0) {
                if (mainData.get(mainData.size() - 1).getViewholder() == LoadingView) {
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
    }

    public void removeResturants() {
        for (int i = 0; i < mainData.size(); i++) {
            if (mainData.get(i).getViewholder() == ResturantView || mainData.get(i).getViewholder() == LoadingView || ExhaustedView == mainData.get(i).getViewholder()) {
                mainData.remove(i);
            }
        }
        notifyDataSetChanged();
        displayLoading(false);
    }


    private void removeLoading() {
        for (int i = 0; i < mainData.size(); i++) {
            if (mainData.get(i).getViewholder() == LoadingView && mainData.get(i).getObject() == null) {
                mainData.remove(i);
            }
        }
    }

    public void setCuratedCollections(List<Collections> c, OnClickListener o) {
        onCollectionListener = o;
        removeLoading();
        if (mainData.size() < 1) {
            Main main = new Main();
            main.setObject(c);
            main.setViewholder(CollectionView);
            mainData.add(main);
            notifyDataSetChanged();
        }
    }


    private int lastResturantSize = 0;

    public void setMainResturant(List<Restaurants> r) {
        if (mainData.size() > 3) { // if for the first time first page was set and now its repeated dont do that it check by below condition
            if (!(((Restaurants) mainData.get(2).getObject()).getRestaurant().getName().equals(r.get(1).getRestaurant().getName()))) {
                setListOfResturantsInMainData(r);
                notifyDataSetChanged();
            } else if (r.size() > lastResturantSize) {
                setListOfResturantsInMainData(r);
                notifyDataSetChanged();
            }
        } else {
            //            HowManyItemsIsInListBeforResturanList = mainData.size();
            setListOfResturantsInMainData(r);
            notifyDataSetChanged();
        }
        removeLoading();
        lastResturantSize = r.size();
    }

    private void setListOfResturantsInMainData(List<Restaurants> r) {
        for (int i = (mainData.size() - HowManyItemsIsInListExceptResturanList) - 1; i < r.size(); i++) {
            Main main = new Main();
            main.setViewholder(ResturantView);
            main.setObject(r.get(i));
            mainData.add(i + 1, main);
        }
    }

    private int getResturantPosition(int mainDataPosition) {
        return mainDataPosition - HowManyItemsIsInListExceptResturanList;
    }
}
