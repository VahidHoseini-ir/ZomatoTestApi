package ir.vahidhoseini.testtraining1.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private int HowManyItemsIsInListBeforResturanList;// position of resturant list is not the main data list size becuase we have add currated collection;
    private final int LoadingView = 1, CollectionView = 2, ResturantView = 3;

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
            case ResturantView: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_resturant, parent, false);
                return new ViewHolderResturant(view, onListItemListener, 0);
            }
            case CollectionView: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_recylerview, parent, false);
                return new ViewHolderRecyclerview(view, onListItemListener);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.e(TAG, TAG + " : onBindViewHolder: position =" + position);
        int view_type = getItemViewType(position);
        if (view_type == CollectionView) {
            ((ViewHolderRecyclerview) holder).setCollection(((List<Collections>) mainData.get(position).getObject()), onCollectionListener);
        } else if (view_type == ResturantView) {
            ((ViewHolderResturant) holder).setResturant(((Restaurants) mainData.get(position).getObject()));

        }
    }

    @Override
    public int getItemViewType(int position) {
        Log.e(TAG, TAG + " : getItemViewType: position =" + position);

        if (position == mainData.size() - 1 && mainData.get(position).getViewholder() == LoadingView) {
            return LoadingView;
        } else if (mainData.get(position).getViewholder() == CollectionView && mainData.get(position).getObject() instanceof ArrayList) {
            return CollectionView;
        } else if (mainData.get(position).getViewholder() == ResturantView && mainData.get(position).getObject() instanceof Restaurants) {
            return ResturantView;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mainData.size();
    }

    public void displayLoading(boolean newQuery) {
        if (newQuery) {

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

    //    private void removeData() {
    //        for (int i = 0; i < mainData.size(); i++) {
    //            mainData.remove(i);
    //        }
    //        notifyDataSetChanged();
    //    }

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


    public void setMainResturant(List<Restaurants> r) {
        removeLoading();
//        if (mainData.size() > 1) {
//            Log.e(TAG, TAG + " : setMainResturant: Restaurants =" + ((Restaurants) mainData.get(2).getObject()).getRestaurant().getName() + "\n r :" + r.get(1).getRestaurant().getName());
//            if (!(((Restaurants) mainData.get(2).getObject()).getRestaurant().getName().equals(r.get(1).getRestaurant().getName()))) {
                setListOfResturantsInMainData(r);
                notifyDataSetChanged();
//            } else {
//                return;
//            }
//        } else {
//            setListOfResturantsInMainData(r);
//            notifyDataSetChanged();
//        }
    }

    private void setListOfResturantsInMainData(List<Restaurants> r) {
        HowManyItemsIsInListBeforResturanList = mainData.size();
        for (int i = 0; i < r.size(); i++) {
            Main main = new Main();
            main.setViewholder(ResturantView);
            main.setObject(r.get(i));
            mainData.add(main);
        }
    }

    private int getResturantPosition(int mainDataPosition) {
        return mainDataPosition - HowManyItemsIsInListBeforResturanList;
    }
}
