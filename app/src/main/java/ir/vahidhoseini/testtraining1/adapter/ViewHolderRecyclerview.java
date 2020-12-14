package ir.vahidhoseini.testtraining1.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.model.zomato.Collections;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.utill.MyApplication;

public class ViewHolderRecyclerview extends RecyclerView.ViewHolder implements View.OnClickListener {

    private AdapterMainResturant resturantAdapter;
    TextView category_name;
    TextView more;
    RecyclerView main_resturant_list;
    OnClickListener onResturantListener;
    int startPoint = 0;
    int categoryId = 0;


    public ViewHolderRecyclerview(@NonNull View itemView, OnClickListener onResturantListener) {
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

            resturantAdapter = new AdapterMainResturant(categoryId);
            main_resturant_list.setAdapter(resturantAdapter);
            main_resturant_list.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, true));

            resturantAdapter.setResturants(restaurants);
            category_name.setText(categoryName);

        }
    }

    @Override
    public void onClick(View view) {
        onResturantListener.onClickListener(getAdapterPosition());
    }

    public void setCollection(List<Collections> collections, OnClickListener onCollectionListener) {
        category_name.setText(MyApplication.instance.getString(R.string.MainCollection));
        more.setText(MyApplication.instance.getString(R.string.SeeAll));
        AdapterCollection collectionAdapter = new AdapterCollection(onCollectionListener);
        main_resturant_list.setAdapter(collectionAdapter);
        main_resturant_list.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, true));
        collectionAdapter.setCollectionsInMainActivity(collections);

    }
}
