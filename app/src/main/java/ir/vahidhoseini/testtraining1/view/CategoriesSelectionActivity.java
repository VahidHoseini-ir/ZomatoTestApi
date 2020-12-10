package ir.vahidhoseini.testtraining1.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cn.lankton.flowlayout.FlowLayout;
import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.customviews.CatresButton;
import ir.vahidhoseini.testtraining1.model.zomato.Category;
import ir.vahidhoseini.testtraining1.repository.DataBase;
import ir.vahidhoseini.testtraining1.repository.ZomatoRepository;

public class CategoriesSelectionActivity extends AppCompatActivity {

    // views
    private FlowLayout mCategoriesRecyclerview;

    //variables
    private ZomatoRepository mZomatoRepository;
    private DataBase mZomatoDatabase;
    private MutableLiveData<List<Category>> mCategories;
    private Thread mThread;
    private Thread mThread2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategories = new MutableLiveData<>();
        setContentView(R.layout.activity_categories_selection);
        mZomatoRepository = ZomatoRepository.getInstance();
        mCategoriesRecyclerview = findViewById(R.id.catergories_view);
        mZomatoDatabase = DataBase.getInstance(this);
        getCatFromDB();
        mCategories.observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                if (categories != null) {
                    for (Category category : categories) {

//                        Toast.makeText(CategoriesSelectionActivity.this, categories.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private void getCatFromDB() {
        if (mThread != null) {
            mThread = null;
        }
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (mZomatoDatabase.getZomatoDao().getCategories().size() > 1) {
                    Log.e("TAG", "onChanged: this condition");
                    for (Category category : mZomatoDatabase.getZomatoDao().getCategories()) {
                        CatresButton catresButton = (CatresButton) getLayoutInflater().inflate(R.layout.layout_item_flowlayout, null);
                        catresButton.setText(category.getName());
                        catresButton.setTag(category.getId());
                        handleAddRemoveCate(category, catresButton);
                    }
                } else {
                    insertCatToDb();
                    getCatFromDB();
                }
            }
        });
        mThread.start();
    }

    private void insertCatToDb() {
        String responce = loadJSONFromAsset(this);
        try {
            JSONObject obj = new JSONObject(responce);
            JSONArray jsonArray = obj.getJSONArray("categories");
            for (i = 1; i <= jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i - 1);
                Category category = new Category();
                category.setName(jsonObject.getString("name"));
                category.setId(jsonObject.getString("id"));
                category.setCheck("0");
                mZomatoDatabase.getZomatoDao().inserCategory(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    int i;
    private void handleAddRemoveCate(Category category, CatresButton catresButton) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCategoriesRecyclerview.addView(catresButton);
                catresButton.setChecked(Integer.parseInt(category.getCheck()) == 0 ? false : true);
                catresButton.getCatresValue().observe(CategoriesSelectionActivity.this, new Observer<CatresButton>() {
                    @Override
                    public void onChanged(CatresButton b) {
                        Log.e("TAG", "onChanged: clicked name : " + category.getName() + "is checked : " + b.isChecked());
                        if (b.isChecked()) {
                            if (mThread2 != null) {
                                mThread2 = null;
                            }
                            mThread2 = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("TAG", "onChanged: getcategory : " + mZomatoDatabase.getZomatoDao().getCategory(category.getId()));
                                    if (mZomatoDatabase.getZomatoDao().getCategory(category.getId()) != null) {
                                        category.setCheck("1");
                                        mZomatoDatabase.getZomatoDao().inserCategory(category);
                                    }
                                }
                            });
                            mThread2.start();
                        } else {
                            if (mThread2 != null) {
                                mThread2 = null;
                            }

                            mThread2 = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("TAG", "onChanged: clicked getcategory : " + mZomatoDatabase.getZomatoDao().getCategory(category.getId()));
                                    if (mZomatoDatabase.getZomatoDao().getCategory(category.getId()) != null) {
                                        category.setCheck("0");
                                        mZomatoDatabase.getZomatoDao().inserCategory(category);
                                    }
                                }
                            });
                            mThread2.start();

                        }

                        if (mThread2 != null) {
                            mThread2 = null;
                        }
                        mThread2 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                List<Category> data = mZomatoDatabase.getZomatoDao().getCategories();
                                mCategories.postValue(data);
                            }
                        });
                        mThread2.start();
                    }

                });

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


}