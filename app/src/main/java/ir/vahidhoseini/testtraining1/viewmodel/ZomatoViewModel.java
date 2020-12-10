package ir.vahidhoseini.testtraining1.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ir.vahidhoseini.testtraining1.model.zomato.Categories;
import ir.vahidhoseini.testtraining1.model.zomato.Category;
import ir.vahidhoseini.testtraining1.repository.ZomatoRepository;

public class ZomatoViewModel extends AndroidViewModel {

    private ZomatoRepository mZomatoRepository;

    public ZomatoViewModel(@NonNull Application application) {
        super(application);
        mZomatoRepository = ZomatoRepository.getInstance();

    }


    public LiveData<List<Category>> getCategory() {
        return mZomatoRepository.getCategory();
    }

    public void reciveCategoryApi() {
        mZomatoRepository.reciveCategoryApi();
    }
}
