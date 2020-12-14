package ir.vahidhoseini.testtraining1.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.adapter.AdapterMain;
import ir.vahidhoseini.testtraining1.adapter.AdapterResturant;
import ir.vahidhoseini.testtraining1.adapter.OnClickListener;
import ir.vahidhoseini.testtraining1.databinding.ActivityMainBinding;
import ir.vahidhoseini.testtraining1.model.zomato.Collections;
import ir.vahidhoseini.testtraining1.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private ActivityMainBinding binding;

    //variables
    private MainViewModel mViewModel;
    private AdapterMain mAdapter;
    private double lat = 40.742051;
    private double lon = -74.004821;


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
        reciveCuratedCollections();
        mViewModel.getCollections().observe(this, new Observer<List<Collections>>() {
            @Override
            public void onChanged(List<Collections> collections) {
                mAdapter.setCuratedCollections(collections, onCollectionListener());
            }
        });

    }

    private void reciveCuratedCollections() {
        Map<String, Object> params = new HashMap<>();
        //        params.put("city_id", 1);
        params.put("lat", lat);
        params.put("lon", lon);
        params.put("count", 10);

        mViewModel.reciveColletionApi(params);

    }

    private void initRecyclerView() {
        mAdapter = new AdapterMain(this);
        binding.mainRecyclerview.setAdapter(mAdapter);
        binding.mainRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.mainRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)) {


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

    @Override
    public void onClickListener(int position) { // its for item list listener

    }
}