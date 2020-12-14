package ir.vahidhoseini.testtraining1.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.vahidhoseini.testtraining1.BaseActivity;
import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.adapter.AdapterCollection;
import ir.vahidhoseini.testtraining1.adapter.OnClickListener;
import ir.vahidhoseini.testtraining1.databinding.ActivityCollectionBinding;
import ir.vahidhoseini.testtraining1.model.zomato.Collections;
import ir.vahidhoseini.testtraining1.viewmodel.CollectionViewModel;

public class CollectionActivity extends BaseActivity implements OnClickListener {


    private ActivityCollectionBinding binding;

    //variablels
    private CollectionViewModel collectionViewModel;
    private AdapterCollection collectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCollectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        collectionViewModel = new ViewModelProvider(this).get(CollectionViewModel.class);
        initRecyclerView();
        collectionObserver();

    }

    private void collectionObserver() {
        reciveCollections();
        collectionViewModel.getCollections().observe(this, new Observer<List<Collections>>() {
            @Override
            public void onChanged(List<Collections> collections) {
                Log.e(TAG, "response have an error :" + collections.toString());
                //                if (collections != null) {
                collectionAdapter.setCollections(collections);
                //                } else {
                //                    collectionViewModel.reciveColletionApi(280, 20);
                //                }
            }
        });
    }

    private void reciveCollections() {
        Map<String, Object> params = new HashMap<>();
        params.put("city_id", 280);
        //        params.put("lat", lat);
        //        params.put("lon", lon);
        params.put("count", 20);
        collectionViewModel.reciveColletionApi(params);
    }


    private void initRecyclerView() {
        collectionAdapter = new AdapterCollection(this);
        binding.collectionRecyclerview.setAdapter(collectionAdapter);
        binding.collectionRecyclerview.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onClickListener(int position) {

    }
}