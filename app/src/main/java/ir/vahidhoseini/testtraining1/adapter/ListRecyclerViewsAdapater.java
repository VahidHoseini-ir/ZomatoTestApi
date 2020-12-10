package ir.vahidhoseini.testtraining1.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;

public class ListRecyclerViewsAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MutableLiveData<List<List<Restaurants>>> mAllListOfResturants;

    public ListRecyclerViewsAdapater(LifecycleOwner lifecycleOwner) {
        mAllListOfResturants = new MutableLiveData<>();
        mAllListOfResturants.observe(lifecycleOwner, new Observer<List<List<Restaurants>>>() {
            @Override
            public void onChanged(List<List<Restaurants>> lists) {

            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void addListOfResturants(List<Restaurants> listOfResturants) {
        List<List<Restaurants>> listOfListResturants = mAllListOfResturants.getValue();
        listOfListResturants.add(listOfResturants);
        mAllListOfResturants.postValue(listOfListResturants);

    }
}
