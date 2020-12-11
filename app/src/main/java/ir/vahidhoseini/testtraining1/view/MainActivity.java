package ir.vahidhoseini.testtraining1.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
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
import ir.vahidhoseini.testtraining1.adapter.ListRecyclerViewsAdapater;
import ir.vahidhoseini.testtraining1.adapter.OnClickListener;
import ir.vahidhoseini.testtraining1.adapter.ResturantAdapter;
import ir.vahidhoseini.testtraining1.model.zomato.Category;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.viewmodel.MainViewModel;

import static ir.vahidhoseini.testtraining1.utill.Constant.COUNT_OF_RESTURANT_LIST_MAIN;

public class MainActivity extends BaseActivity implements OnClickListener {

    //views
    private RecyclerView mAllListRecycles;

    //variabls
    private ListRecyclerViewsAdapater mListRecyclerViewsAdapter;
    private MainViewModel mViewModel;
    private List<Category> mCategories;
    private int mLastListOfCategory;
    private double lat = 40.742051;
    private double lon = -74.004821;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
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
            mViewModel.reciveMainResturantsApi(mCategories.get(0).getName(), 1, COUNT_OF_RESTURANT_LIST_MAIN, lat, lon, "", mCategories.get(0).getId(), "", "");
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
                        mViewModel.reciveMainResturantsApi(mCategories.get(mLastListOfCategory).getName(), 1, COUNT_OF_RESTURANT_LIST_MAIN, lat, lon, "", mCategories.get(mLastListOfCategory).getId(), "", "");
                    }

                } else {
                    if (mCategories.size() > mLastListOfCategory + 1) {
                        mLastListOfCategory += 1;
                        mViewModel.reciveMainResturantsApi(mCategories.get(mLastListOfCategory).getName(), 1, COUNT_OF_RESTURANT_LIST_MAIN, lat, lon, "", mCategories.get(mLastListOfCategory).getId(), "", "");
                    }
                }

            }
        });
    }

    private void initRecyclerView() {
        mListRecyclerViewsAdapter = new ListRecyclerViewsAdapater(this, getResturantsClickListener(), this);
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


    public OnClickListener getResturantsClickListener() {
        return new OnClickListener() {
            @Override
            public void onClickListener(int position) {
                Toast.makeText(MainActivity.this, "POSITION :" + position, Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void onClickListener(int position) { // This onclick work on the more btn for each recyclerview in main activity.
        Toast.makeText(MainActivity.this, "POSITION :" + position, Toast.LENGTH_SHORT).show();
    }
}