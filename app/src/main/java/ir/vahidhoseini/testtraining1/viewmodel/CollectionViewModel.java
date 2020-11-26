package ir.vahidhoseini.testtraining1.viewmodel;

import android.app.Application;
import android.content.Context;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ir.vahidhoseini.testtraining1.model.zomato.Collection;
import ir.vahidhoseini.testtraining1.model.zomato.Collections;
import ir.vahidhoseini.testtraining1.repository.DataBase;
import ir.vahidhoseini.testtraining1.repository.ZomatoRepository;
import ir.vahidhoseini.testtraining1.view.CollectionActivity;

public class CollectionViewModel extends AndroidViewModel {

    private ZomatoRepository mZomatoRepository;
    private DataBase dataBase;

    public CollectionViewModel(@NonNull Application application) {
        super(application);
//        mZomatoRepository = ZomatoRepository.getInstance(application.getApplicationContext());
        dataBase = DataBase.getInstance(CollectionActivity.context);

    }


    public LiveData<List<Collections>> getCollections() {
        return mZomatoRepository.getCollections();
    }

    public LiveData<List<Collection>> getDataFromDatabase() {
        return dataBase.getZomatoDao().getCollections();
    }

    public void reciveColledtionApi(int cityId, int count) {
        mZomatoRepository.reciveColletionApi(cityId, count);
    }

}
