package ir.vahidhoseini.testtraining1.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.vahidhoseini.testtraining1.BaseActivity;
import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.adapter.AdapterMain;
import ir.vahidhoseini.testtraining1.adapter.OnClickListener;
import ir.vahidhoseini.testtraining1.databinding.ActivityMainBinding;
import ir.vahidhoseini.testtraining1.model.zomato.Collections;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.utill.MyApplication;
import ir.vahidhoseini.testtraining1.utill.Param;
import ir.vahidhoseini.testtraining1.viewmodel.MainViewModel;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends BaseActivity implements OnClickListener {

    private ActivityMainBinding binding;

    //variables
    private MainViewModel mViewModel;
    private AdapterMain mAdapter;
    private Animation mAnimation;
    private Map<String, Object> mParams = Param.getInstanc().MapResturant();


    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.currentActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        initRecyclerView();
        curatedCollections();
        initSearch();
        setFiltering();
    }

    private void setFiltering() {
        binding.rootFiltering.rootFiltering.setVisibility(View.GONE);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.from_bottom_to_top);
                binding.rootFiltering.rootFiltering.startAnimation(mAnimation);
                binding.rootFiltering.rootFiltering.setVisibility(View.VISIBLE);
            }
        });
        Map<String, Object> param = Param.getInstanc().MapResturant();
        binding.rootFiltering.sortGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rating: {
                        param.put("sort", "rating");

                    }
                    case R.id.cost: {
                        param.put("sort", "cost");

                    }
                    case R.id.distance: {
                        param.put("sort", "real_distance");

                    }
                }
            }
        });
        binding.rootFiltering.orderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.ascending: {
                        param.put("order", "asc");
                    }
                    case R.id.descending: {
                        param.put("order", "desc");
                    }
                }
            }
        });
        binding.rootFiltering.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mParams = param;
                mAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.from_top_to_bottom);
                binding.rootFiltering.rootFiltering.startAnimation(mAnimation);
                binding.rootFiltering.rootFiltering.setVisibility(View.GONE);
                mAdapter.removeResturants();
                mViewModel.reciveResturantApi(mParams);
            }
        });
    }


    private void initSearch() {
        binding.searchview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ResturantActivity.class));
            }
        });
    }


    private void curatedCollections() {
        mAdapter.displayLoading(false);
        reciveCuratedCollections();
        mViewModel.getCollections().observe(this, new Observer<List<Collections>>() {
            @Override
            public void onChanged(List<Collections> c) {
                dataIsFinishedDontRequestMore = false;
                if (c != null) {
                    mViewModel.queryFinished();
                    mAdapter.setCuratedCollections(c, onCollectionListener());
                    setMainResturants();
                    mViewModel.reciveResturantApi(mParams);
                }
            }
        });
    }

    private void reciveCuratedCollections() {
        mViewModel.reciveColletionApi(Param.getInstanc().MapCuratedCollections());
    }

    private boolean dataIsFinishedDontRequestMore = false;
    private boolean isDoneTheLastResturantRequest = false;

    private void setMainResturants() {
        mAdapter.displayLoading(false);
        mViewModel.getMainListResturants().observe(this, new Observer<List<Restaurants>>() {
            @Override
            public void onChanged(List<Restaurants> restaurants) {
                isDoneTheLastResturantRequest = true;
                mViewModel.queryFinished();
                if (restaurants != null) {
                    mAdapter.setMainResturant(restaurants);
                } else {
                    dataIsFinishedDontRequestMore = true;
                    mAdapter.displayExhausted();
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
                if (!dataIsFinishedDontRequestMore && isDoneTheLastResturantRequest) {
                    mAdapter.displayLoading(false);
                    if (!recyclerView.canScrollVertically(1)) {
                        if (!mViewModel.isPerformingQuery()) {
                            isDoneTheLastResturantRequest = false;
                            mViewModel.nextReciveResturantApi();
                        }
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
//            Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, CollectionActivity.class));
        } else {
        }
    }
}