package ir.vahidhoseini.testtraining1.view;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.vahidhoseini.testtraining1.BaseActivity;
import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.adapter.OnClickListener;
import ir.vahidhoseini.testtraining1.adapter.ResturantAdapter;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.repository.DataBase;
import ir.vahidhoseini.testtraining1.viewmodel.ResturantViewModel;

public class ResturantActivity extends BaseActivity implements OnClickListener {

    private ResturantViewModel mResturantViewModel;
    private RecyclerView resturantRecyclerView;
    private ResturantAdapter resturantAdapter;
    private double lat = 40.742051;
    private double lon = -74.004821;
    private int resturantsPageNumber = 1;
    private DataBase dataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant);
        dataBase = DataBase.getInstance(this.getApplicationContext());
        setSupportActionBar(findViewById(R.id.toolbar));
        resturantRecyclerView = findViewById(R.id.searche_recycler_resturant_view);

        SearchView searchView = findViewById(R.id.searchview);
        mResturantViewModel = new ViewModelProvider(this).get(ResturantViewModel.class);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                resturantAdapter.displayLoading();
                resturantsPageNumber = 1;
                RepeatStartPage = 1;
                mResturantViewModel.reciveResturantApi(query, resturantsPageNumber, 10, lat, lon, null, null, null, null);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        ResturantObserver();
        initRecyclerView();

    }


    private int result_found_count = 0;

    private void initRecyclerView() {
        resturantAdapter = new ResturantAdapter((OnClickListener) this);
        resturantAdapter.setViewType(resturantAdapter.ResturantView);
        resturantRecyclerView.setAdapter(resturantAdapter);
        resturantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resturantRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)) {
                    if (resturantAdapter.getResturantSize() >= result_found_count - 10) {
                        return;
                    } else {
                        resturantsPageNumber += 10;
                        mResturantViewModel.nextReciveResturantApi();
                    }
                }
            }
        });
    }

    int RepeatStartPage = 1;

    private void ResturantObserver() {
        mResturantViewModel.getResturants().observe(this, new Observer<List<Restaurants>>() {
            @Override
            public void onChanged(List<Restaurants> restaurants) {
                mResturantViewModel.performingQuery();
                if (restaurants != null) {
                    //                    Log.e(TAG, "ZomatoApiClient observer:");
                    result_found_count = Integer.parseInt(restaurants.get(1).getResults_found());
                    RepeatStartPage = Integer.parseInt(restaurants.get(1).getResults_start());
                    resturantAdapter.setResturants(restaurants);
                    //                    inserResturantsToDb(restaurants);

                } else {
                    Log.e(TAG, "ZomatoApiClient resturants is null");
                    mResturantViewModel.reciveResturantApi(null, RepeatStartPage, 10, lat, lon, null, null, null, null);

                }
            }
        });
    }

    @Override
    public void onClickListener(int position) {

    }


}