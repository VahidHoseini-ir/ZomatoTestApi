package ir.vahidhoseini.testtraining1.view;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.WindowCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.adapter.AdapterResturant;
import ir.vahidhoseini.testtraining1.adapter.OnClickListener;
import ir.vahidhoseini.testtraining1.databinding.ActivityDetailColocationBinding;
import ir.vahidhoseini.testtraining1.model.zomato.Collection;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.utill.Param;
import ir.vahidhoseini.testtraining1.viewmodel.ResturantViewModel;

public class DetailCollectionActivity extends AppCompatActivity implements OnClickListener {

    public ActivityDetailColocationBinding binding;
    public ResturantViewModel mResturantViewModel;

    //variables
    private Animation mTakeUp;
    private Animation mTakeDown;
    private AdapterResturant resturantAdapter;
    private boolean recyclerExhausted = false;
    private int resturantsPageNumber = 1;
    private boolean firstPutingDataInList = false;
    Map<String, Object> params = Param.getInstanc().MapResturant();


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailColocationBinding.inflate(getLayoutInflater());
        View Layout = binding.getRoot();
        setContentView(Layout);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        binding.toolbarLayout.setTitleEnabled(false);
        binding.toolbar.setTitle("");
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTakeUp = AnimationUtils.loadAnimation(this, R.anim.from_bottom_to_top);
        mTakeDown = AnimationUtils.loadAnimation(this, R.anim.from_top_to_bottom);
        mResturantViewModel = new ViewModelProvider(this).get(ResturantViewModel.class);

        initRecyclerView();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Collection collection = bundle.getParcelable("COLLECTION");
            binding.title.setText(collection.getTitle());
            //            setAnimationToTitle();
            if (!collection.getImage_url().isEmpty()) {
                Picasso.get().load(collection.getImage_url()).placeholder(R.drawable.returant_placeholder).error(R.drawable.returant_placeholder).into(binding.image);
                binding.image.setTag(collection.getImage_url());
            } else {
                Picasso.get().load(R.drawable.returant_placeholder).into(binding.image);
            }
            resturantAdapter.setDataForDetailCollectionActivity(collection);
            params.put("collection_id", collection.getCollection_id());

        }
        ResturantObserver();
        mResturantViewModel.reciveResturantApi(params);
    }


    boolean isAnimationSet = false;

    private void initRecyclerView() {
        resturantAdapter = new AdapterResturant(this);
        binding.resturantList.setAdapter(resturantAdapter);
        binding.resturantList.setLayoutManager(new LinearLayoutManager(this));
        binding.resturantList.addOnScrollListener(new RecyclerView.OnScrollListener() {

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.rootDetailCollectionActivity.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (findViewById(R.id.name) != null) {
                        if (!isViewVisible(findViewById(R.id.name))) {
                            if (!isAnimationSet) {
                                binding.title.startAnimation(mTakeUp);
                                binding.title.setVisibility(View.VISIBLE);
                                isAnimationSet = true;
                            }
                        } else {
                            if (isAnimationSet) {
                                binding.title.startAnimation(mTakeDown);
                                binding.title.setVisibility(View.GONE);
                                isAnimationSet = false;
                            }
                        }

                    }

                }
            });
        }
    }

    private boolean isViewVisible(View view) {
        Rect scrollBounds = new Rect();
        binding.resturantList.getDrawingRect(scrollBounds);

        float top = view.getY();
        float bottom = top + view.getHeight();

        if (scrollBounds.top < top && scrollBounds.bottom > bottom) {
            return true;
        } else {
            return false;
        }
    }

    private void ResturantObserver() {
        mResturantViewModel.getResturants().observe(this, new Observer<List<Restaurants>>() {
            @Override
            public void onChanged(List<Restaurants> restaurants) {
                mResturantViewModel.performingQueryFinished();
                if (firstPutingDataInList) { // in this line i just handl the main live data problem that put data withoud requesting like
                    // below that i handle exhustedview that dont show for the first time that is not in need now.
                    if (restaurants != null) {
                        resturantAdapter.setResturants(restaurants);
                    } else {
                        Log.d("TAG", "ResturantObserver is null");
                        if (resturantsPageNumber > 5) {
                            /**
                             * check if one time this observer return null like:
                             * "when the data from net is finished and we return null in resturants data that means finished data"
                             * dont make first recyclerview item finished data and recyclerview is exhuasted.
                             * to prevent from making the first data of recyclerview exhuasted.
                             * and just when the page is greater then the above would exhuast the view.
                             */
                            recyclerExhausted = true;
                            resturantAdapter.setRecyclerExauhsted(false);
                        }
                    }
                } else {
                    firstPutingDataInList = true;
                }

            }
        });
    }

    @Override
    public void onClickListener(int position) {

    }

    @Override
    protected void onDestroy() {
        resturantAdapter.hideAllExceptCollection();
        super.onDestroy();
    }
}