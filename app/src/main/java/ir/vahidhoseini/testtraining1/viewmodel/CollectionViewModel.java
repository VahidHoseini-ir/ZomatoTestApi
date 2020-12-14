package ir.vahidhoseini.testtraining1.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Map;

import ir.vahidhoseini.testtraining1.model.zomato.Collections;
import ir.vahidhoseini.testtraining1.repository.Repository;

public class CollectionViewModel extends ViewModel {

    private Repository mZomatoRepository;

    public CollectionViewModel() {
        mZomatoRepository = Repository.getInstance();
    }


    public LiveData<List<Collections>> getCollections() {
        return mZomatoRepository.getCollections();
    }

       public void reciveColletionApi(Map<String , Object> params) {
        mZomatoRepository.reciveColletionApi(params);
    }

}
