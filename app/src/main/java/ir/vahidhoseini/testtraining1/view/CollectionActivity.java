package ir.vahidhoseini.testtraining1.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ir.vahidhoseini.testtraining1.BaseActivity;
import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.adapter.CollectionAdapter;
import ir.vahidhoseini.testtraining1.adapter.ResturantAdapter;
import ir.vahidhoseini.testtraining1.adapter.OnClickListener;
import ir.vahidhoseini.testtraining1.model.zomato.Categories;
import ir.vahidhoseini.testtraining1.model.zomato.Collection;
import ir.vahidhoseini.testtraining1.model.zomato.Collections;
import ir.vahidhoseini.testtraining1.repository.DataBase;
import ir.vahidhoseini.testtraining1.repository.ZomatoRepository;
import ir.vahidhoseini.testtraining1.viewmodel.CollectionViewModel;

public class CollectionActivity extends BaseActivity implements OnClickListener {

    private CollectionViewModel collectionViewModel;
    private RecyclerView collection_recyclerview;
    private CollectionAdapter collectionAdapter;
    public static Context context;
    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        dataBase = DataBase.getInstance(this);
        setContentView(R.layout.activity_collection);
        collection_recyclerview = findViewById(R.id.collection_recyclerview);

        collectionViewModel = new ViewModelProvider(this).get(CollectionViewModel.class);
        collectionObserver();
        initRecyclerView();

        collectionViewModel.getDataFromDatabase().observe(this, new Observer<List<Collection>>() {
            @Override
            public void onChanged(List<Collection> collections) {
                collectionAdapter.setCollections(collections);
            }
        });

        //        collectionViewModel.reciveColledtionApi(280, 20);
    }

    private void collectionObserver() {
        collectionViewModel.getCollections().observe(this, new Observer<List<Collections>>() {
            @Override
            public void onChanged(List<Collections> collections) {
                if (collections != null) {
                    //                    zomatoAdapter.setCollections(collections);
                    //                    setDataToDataBase(collections);
                    //                    for (Collections collection : collections) {
                    //                        Log.e(TAG, "Collections Response: " + collection.getCollection().getTitle());
                    //                    }
                } else {
                    collectionViewModel.reciveColledtionApi(280, 20);
                }
            }
        });
    }

    List<Collection> wantedDataForDataBase;

    public void setDataToDataBase(List<Collections> data) {
        wantedDataForDataBase = new ArrayList<>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                for (Collections Datas : data) {
                    Collection collection = new Collection();
                    collection.setCollection_id(Datas.getCollection().getCollection_id());
                    collection.setTitle(Datas.getCollection().getTitle());
                    collection.setDescription(Datas.getCollection().getDescription());
                    collection.setCollection_id(Datas.getCollection().getCollection_id());
                    collection.setDescription(Datas.getCollection().getDescription());
                    collection.setImage_url(Datas.getCollection().getImage_url());
                    collection.setShare_url(Datas.getCollection().getShare_url());
                    collection.setUrl(Datas.getCollection().getUrl());
                    wantedDataForDataBase.add(collection);
                }
                dataBase.getZomatoDao().insertColleciton(wantedDataForDataBase);
            }
        });
        thread.start();

    }

    private void initRecyclerView() {
        collectionAdapter = new CollectionAdapter(this);
        collectionAdapter.setViewType(collectionAdapter.CollectionView);
        collection_recyclerview.setAdapter(collectionAdapter);
        collection_recyclerview.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onClickListener(int position) {

    }
}