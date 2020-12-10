package ir.vahidhoseini.testtraining1.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
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

import java.util.ArrayList;
import java.util.List;

import ir.vahidhoseini.testtraining1.BaseActivity;
import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.adapter.ListRecyclerViewsAdapater;
import ir.vahidhoseini.testtraining1.adapter.OnClickListener;
import ir.vahidhoseini.testtraining1.adapter.ResturantAdapter;
import ir.vahidhoseini.testtraining1.model.zomato.Category;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity {

    //views
    private RecyclerView mAllListRecycles;

    //variabls
    private ListRecyclerViewsAdapater mListRecyclerViewsAdapter;
    private MainViewModel mViewModel;
    private AppBarConfiguration mAppBarConfiguration;
    private List<Category> mCategories = new ArrayList<>();
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
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        whichResturantTypeShow();
        initRecyclerView();
        showResturantType();

    }

    private void whichResturantTypeShow() {
        mViewModel.getCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                if (categories != null) {
                    //                    mCategories = categories;
                    for (int i = 0; i < categories.size(); i++) {
                        if (Integer.parseInt(categories.get(i).getCheck()) == 1) {
                            mCategories.add(categories.get(i));

                        }
                    }
                    mLastListOfCategory = 1;
                    Log.e(TAG, "onChanged: getId : " + mCategories.get(1).getId());
                    mViewModel.reciveMainResturantsApi(mCategories.get(1).getName(), 1, 5, lat, lon, "", mCategories.get(1).getId(), "", "");
                }

            }
        });
    }

    private void showResturantType() {
        mViewModel.getMainListResturants().observe(this, new Observer<List<Restaurants>>() {
            @Override
            public void onChanged(List<Restaurants> restaurants) {
                if (restaurants != null) {
                    Log.e(TAG, "onChanged: resturants : " + restaurants.toString());
                    Toast.makeText(MainActivity.this, "" + restaurants.toString(), Toast.LENGTH_SHORT).show();
                    if (restaurants.size() > 0 && mCategories.size() > mLastListOfCategory + 1) {
                        mLastListOfCategory += 1;
                        mViewModel.reciveMainResturantsApi(mCategories.get(mLastListOfCategory).getName(), 1, 5, lat, lon, "", mCategories.get(mLastListOfCategory).getId(), "", "");
                    }
                }

            }
        });
    }

    private void initRecyclerView() {
        mListRecyclerViewsAdapter = new ListRecyclerViewsAdapater(this);
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

}