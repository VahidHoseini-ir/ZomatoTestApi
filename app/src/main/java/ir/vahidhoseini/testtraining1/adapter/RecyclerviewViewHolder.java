package ir.vahidhoseini.testtraining1.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.viewmodel.MainViewModel;

import static ir.vahidhoseini.testtraining1.utill.Constant.COUNT_OF_RESTURANT_LIST_MAIN;

public class RecyclerviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private MainResturantAdapter resturantAdapter;
    TextView category_name;
    TextView more;
    RecyclerView main_resturant_list;
    OnClickListener onResturantListener;
    int startPoint = 0;
    int categoryId = 0;


    public RecyclerviewViewHolder(@NonNull View itemView, OnClickListener onResturantListener) {
        super(itemView);
        this.onResturantListener = onResturantListener;
        category_name = itemView.findViewById(R.id.category_name);
        more = itemView.findViewById(R.id.more);
        main_resturant_list = itemView.findViewById(R.id.main_resturant_list);
        more.setOnClickListener(this);
    }

    public void setData(List<Restaurants> restaurants, String categoryName, int categoryid) {
        if (restaurants.size() > 1) {
            startPoint = restaurants.size();
            categoryId = categoryid;
            initRecyclerView();
            resturantAdapter.setResturants(restaurants);
            category_name.setText(categoryName);

        }
    }

    private void initRecyclerView() {
        resturantAdapter = new MainResturantAdapter( categoryId);
        main_resturant_list.setAdapter(resturantAdapter);
        main_resturant_list.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, true));
    }

    @Override
    public void onClick(View view) {
        onResturantListener.onClickListener(getAdapterPosition());
    }
}
