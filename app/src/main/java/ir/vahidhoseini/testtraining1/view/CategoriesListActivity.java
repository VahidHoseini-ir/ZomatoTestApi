package ir.vahidhoseini.testtraining1.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ir.vahidhoseini.testtraining1.BaseActivity;
import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.adapter.AdapaterListRecyclerViews;
import ir.vahidhoseini.testtraining1.adapter.OnClickListener;
import ir.vahidhoseini.testtraining1.model.zomato.Category;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.utill.MyApplication;
import ir.vahidhoseini.testtraining1.utill.Param;
import ir.vahidhoseini.testtraining1.viewmodel.CategoriesViewModel;

import static ir.vahidhoseini.testtraining1.utill.Constant.COUNT_OF_RESTURANT_LIST_MAIN;

/**
 * I dont use this activity i just need it if i some times
 * i wanted to add the categories list
 * and ofcourse the @AdapaterListRecyclerViews is just made for this activity with its viwholder
 */
public class CategoriesListActivity extends BaseActivity implements OnClickListener {

    //views
    private RecyclerView mAllListRecycles;

    //variabls
    private AdapaterListRecyclerViews mListRecyclerViewsAdapter;
    private CategoriesViewModel mViewModel;
    private List<Category> mCategories;
    private int mLastListOfCategory;

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.currentActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_catgegories);

        mViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initRecyclerView();
        whichResturantTypeShow();
        showResturantType();

    }


    private void whichResturantTypeShow() {
        mCategories = new ArrayList<>();
        mListRecyclerViewsAdapter.showLoading();
        String responce = loadJSONFromAsset(this);
        try {
            JSONObject obj = new JSONObject(responce);
            JSONArray jsonArray = obj.getJSONArray("categories");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Category category = new Category();
                category.setName(jsonObject.getString("name"));
                category.setId(jsonObject.getString("id"));
                category.setCheck("1");
                mCategories.add(category);
            }
            mLastListOfCategory = 0;
            Map<String, Object> p = Param.getInstanc().MapResturant();
            p.put("q", mCategories.get(0).getName());
            p.put("category", mCategories.get(0).getId());
            mViewModel.reciveMainResturantsApi(p);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    boolean first = true;

    private void showResturantType() {
        mViewModel.getMainListResturants().observe(this, new Observer<List<Restaurants>>() {
            @Override
            public void onChanged(List<Restaurants> restaurants) {
                if (restaurants != null) {
                    if (first) {
                        first = false;
                        mListRecyclerViewsAdapter.setBannerView();
                    }
                    if (restaurants.size() > 1) {
                        mListRecyclerViewsAdapter.addListOfResturants(restaurants, mLastListOfCategory, mCategories.get(mLastListOfCategory).getName());
                    }

                    if (mCategories.size() > mLastListOfCategory + 1) {
                        mLastListOfCategory += 1;
                        Map<String, Object> p = Param.getInstanc().MapResturant();
                        p.put("q", mCategories.get(mLastListOfCategory).getName());
                        p.put("category", mCategories.get(mLastListOfCategory).getId());
                        mViewModel.reciveMainResturantsApi(p);
                    }

                } else {
                    if (mCategories.size() > mLastListOfCategory + 1) {
                        mLastListOfCategory += 1;
                        Map<String, Object> p = Param.getInstanc().MapResturant();
                        p.put("q", mCategories.get(mLastListOfCategory).getName());
                        p.put("category", mCategories.get(mLastListOfCategory).getId());
                        mViewModel.reciveMainResturantsApi(p);
                    }
                }

            }
        });
    }

    private void initRecyclerView() {
        mListRecyclerViewsAdapter = new AdapaterListRecyclerViews(this, this);
        mAllListRecycles = findViewById(R.id.all_list_of_datas);
        mAllListRecycles.setAdapter(mListRecyclerViewsAdapter);
        mAllListRecycles.setLayoutManager(new LinearLayoutManager(this));
        mAllListRecycles.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)) {

                }
            }
        });
    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("category.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_resturant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_search: {
                TransitionManager.beginDelayedTransition((ViewGroup) this.findViewById(R.id.toolbar));
                MenuItemCompat.expandActionView(item);
                startActivity(new Intent(CategoriesListActivity.this, ResturantActivity.class));
                return true;
            }
            case R.id.zomato_location: {
                Toast.makeText(CategoriesListActivity.this, "zomato_location :", Toast.LENGTH_SHORT).show();

            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickListener(int position) { // This onclick work on the more btn for each recyclerview in main activity.
        Toast.makeText(CategoriesListActivity.this, "POSITION :" + position, Toast.LENGTH_SHORT).show();
    }
}