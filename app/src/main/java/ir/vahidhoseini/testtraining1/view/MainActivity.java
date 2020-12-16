package ir.vahidhoseini.testtraining1.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import ir.vahidhoseini.testtraining1.adapter.AdapterMain;
import ir.vahidhoseini.testtraining1.adapter.OnClickListener;
import ir.vahidhoseini.testtraining1.databinding.ActivityMainBinding;
import ir.vahidhoseini.testtraining1.model.zomato.Collections;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.utill.Param;
import ir.vahidhoseini.testtraining1.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private ActivityMainBinding binding;

    //variables
    private MainViewModel mViewModel;
    private AdapterMain mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initRecyclerView();
        curatedCollections();

    }


    private void curatedCollections() {
        mAdapter.displayLoading(false);
        reciveCuratedCollections();
        mViewModel.getCollections().observe(this, new Observer<List<Collections>>() {
            @Override
            public void onChanged(List<Collections> collections) {
                mViewModel.queryFinished();
                mAdapter.setCuratedCollections(collections, onCollectionListener());
                setMainResturants();
                mViewModel.reciveResturantApi(Param.getInstanc().MapResturant());
            }
        });
    }

    private void reciveCuratedCollections() {
        mViewModel.reciveColletionApi(Param.getInstanc().MapCuratedCollections());
    }

    private void setMainResturants() {
        mAdapter.displayLoading(false);
        mViewModel.getMainListResturants().observe(this, new Observer<List<Restaurants>>() {
            @Override
            public void onChanged(List<Restaurants> restaurants) {
                mViewModel.queryFinished();
                if (restaurants != null) {
                    Log.e("TAG", "onChanged: restaurants = " + restaurants.toString());
                    mAdapter.setFirstMainResturant(restaurants);
                }
            }
        });

    }


    private void initRecyclerView() {
        mAdapter = new AdapterMain(this);
        binding.mainRecyclerview.setAdapter(mAdapter);
        binding.mainRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.mainRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)) {
                    if (!mViewModel.isPerformingQuery()) {
                        mAdapter.displayLoading(false);

                        mViewModel.nextReciveResturantApi();

                    }
                }
            }
        });
    }


    public OnClickListener onCollectionListener() {
        return new OnClickListener() {
            @Override
            public void onClickListener(int collectionId) {
                Toast.makeText(MainActivity.this, "" + collectionId, Toast.LENGTH_SHORT).show();

            }
        };
    }


    /**
     * in this @onClickListener
     * for clicking see all in curated collections
     * in its view holder we told that it returns -1 that can be identifiable
     */
    private final int seeAll = -1;

    @Override
    public void onClickListener(int id) { // its for item list listener
        if (id == seeAll) {
            Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, CollectionActivity.class));

        }
    }
}