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
import java.util.Map;

import ir.vahidhoseini.testtraining1.BaseActivity;
import ir.vahidhoseini.testtraining1.adapter.OnClickListener;
import ir.vahidhoseini.testtraining1.adapter.AdapterResturant;
import ir.vahidhoseini.testtraining1.databinding.ActivityResturantBinding;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.utill.Param;
import ir.vahidhoseini.testtraining1.viewmodel.ResturantViewModel;

public class ResturantActivity extends BaseActivity implements OnClickListener {

    private ActivityResturantBinding mBinding;
    private ResturantViewModel mResturantViewModel;
    private AdapterResturant resturantAdapter;
    private int resturantsPageNumber = 1;
    Map<String, Object> params = Param.getInstanc().MapResturant();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityResturantBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        setSupportActionBar(mBinding.toolbar);

        mResturantViewModel = new ViewModelProvider(this).get(ResturantViewModel.class);

        initSearchView();
        ResturantObserver();
        initRecyclerView();

    }

    private void initSearchView() {
        mBinding.searchview.setFocusable(true);
        mBinding.searchview.onActionViewExpanded();
        mBinding.searchview.requestFocusFromTouch();
        mBinding.searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                resturantAdapter.displayLoading(true); // with true value notify to loading that is new query to clear the list and show new loading
                recyclerExhausted = false;
                resturantsPageNumber = 1;
                params.put("q", query);
                mResturantViewModel.reciveResturantApi(params);
                mBinding.searchview.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }


    private void initRecyclerView() {
        resturantAdapter = new AdapterResturant(this);
        mBinding.searcheRecyclerResturantView.setAdapter(resturantAdapter);
        mBinding.searcheRecyclerResturantView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.searcheRecyclerResturantView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)) {
                    if (!recyclerExhausted) {
                        resturantAdapter.displayLoading(false);// with false value notify to loading that loading should be at the end of list.
                        resturantsPageNumber += 10;
                        mResturantViewModel.nextReciveResturantApi();
                    }
                }
            }
        });
    }

    private boolean recyclerExhausted = false;

    private void ResturantObserver() {
        mResturantViewModel.getResturants().observe(this, new Observer<List<Restaurants>>() {
            @Override
            public void onChanged(List<Restaurants> restaurants) {
                mResturantViewModel.performingQueryFinished();
                if (restaurants != null) {
                    resturantAdapter.setResturants(restaurants);
                } else {
                    Log.d(TAG, "ResturantObserver is null");
                    if (resturantsPageNumber < 5) { // check that page number if page number less than page 2 that means no items find and resturant list has no item.
                        recyclerExhausted = true;
                        resturantAdapter.setRecyclerExauhsted(true);// if list has no items so notify to recycler exauhsted that the list has no item. that can change the message of ExhaustedView

                    } else {
                        recyclerExhausted = true;
                        resturantAdapter.setRecyclerExauhsted(false);// if list has no items so notify to recycler exauhsted that the list has no item. that can change the message of ExhaustedView

                    }
                }
            }
        });
    }


    @Override
    public void onClickListener(int position) {

    }
}