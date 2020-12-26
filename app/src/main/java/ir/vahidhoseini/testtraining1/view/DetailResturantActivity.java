package ir.vahidhoseini.testtraining1.view;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.vahidhoseini.testtraining1.BaseActivity;
import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.customviews.CuisinesView;
import ir.vahidhoseini.testtraining1.databinding.ActivityDetailResturantBinding;
import ir.vahidhoseini.testtraining1.model.zomato.Reviews;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Location;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Rate;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurant;
import ir.vahidhoseini.testtraining1.utill.MyApplication;
import ir.vahidhoseini.testtraining1.utill.Param;
import ir.vahidhoseini.testtraining1.viewmodel.DetailViewModel;

public class DetailResturantActivity extends BaseActivity {

    private DetailViewModel mViewModel;
    private ActivityDetailResturantBinding binding;
    private Animation mTakeUp;
    private Animation mTakeDown;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailResturantBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mTakeUp = AnimationUtils.loadAnimation(this, R.anim.from_bottom_to_top);
        mTakeDown = AnimationUtils.loadAnimation(this, R.anim.from_top_to_bottom);
        mViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        binding.toolbarLayout.setTitle(" ");

        if (getIntent().getExtras() != null) {
            Restaurant restaurants = getIntent().getExtras().getParcelable("Restaurants");
            Location location = getIntent().getExtras().getParcelable("Location");
            Rate rate = getIntent().getExtras().getParcelable("Rate");
            setRateToView(rate);
            setLocationToView(location);
            setOtherSecionsToView(restaurants);
            setReviews();

        } else {

        }
    }

    private void setReviews() {
        Map<String , Object> param = Param.getInstanc().MapReviews();
        param.put("res_id", 18902483);
        mViewModel.reciveReviews(param);
        mViewModel.getReviews().observe(this, new Observer<List<Reviews>>() {
            @Override
            public void onChanged(List<Reviews> reviews) {
                if (reviews != null) {
                    for (int i = 0; i < 5; i++) {
                        View view = getLayoutInflater().inflate(R.layout.layout_item_review, binding.include.reviews, false);
                        CircleImageView profileImage = view.findViewById(R.id.profile_image);
                        ImageView rate1 = view.findViewById(R.id.rate1);
                        ImageView rate2 = view.findViewById(R.id.rate2);
                        ImageView rate3 = view.findViewById(R.id.rate3);
                        ImageView rate4 = view.findViewById(R.id.rate4);
                        ImageView rate5 = view.findViewById(R.id.rate5);
                        ImageView[] rate = new ImageView[]{rate1, rate2, rate3, rate4, rate5};

                        TextView name = view.findViewById(R.id.name);
                        TextView reviewText = view.findViewById(R.id.review_text);
                        TextView review_time = view.findViewById(R.id.review_time);
                        review_time.setText(reviews.get(i).getReview().getReview_time_friendly());
//                        name.setText(reviews.get(i).getReview().getName());
                        reviewText.setText(reviews.get(i).getReview().getReview_text());

//                        if (!reviews.get(i).getReview().getProfile_image().isEmpty()) {
//                            Picasso.get().load(reviews.get(i).getReview().getProfile_image()).placeholder(R.drawable.returant_placeholder).error(R.drawable.returant_placeholder).into(profileImage);
//                            profileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                        } else {
//                            profileImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//                            Picasso.get().load(R.drawable.returant_placeholder).into(profileImage);
//                        }

                        for (int j = 0; j < reviews.get(i).getReview().getRating(); j++) {
                            rate[j].setVisibility(View.VISIBLE);
                        }
                        binding.include.reviews.addView(view);
                    }

                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.currentActivity = this;
    }

    private void setOtherSecionsToView(Restaurant restaurants) {
        setAnimationToTitle();
        binding.title.setText(restaurants.getName());
        binding.include.name.setText(restaurants.getName());
        if (!restaurants.getFeatured_image().isEmpty()) {
            Picasso.get().load(restaurants.getFeatured_image()).placeholder(R.drawable.returant_placeholder).error(R.drawable.returant_placeholder).into(binding.image);
        } else {
            Picasso.get().load(R.drawable.returant_placeholder).into(binding.image);
        }

        binding.include.establishment.setText("");
        for (int i = 0; restaurants.getEstablishment().length > i; i++) {
            binding.include.establishment.setText(binding.include.establishment.getText() + restaurants.getEstablishment()[i] + ", ");
        }
        binding.include.timing.setText(restaurants.getTimings().replace(",", ", \n"));

        binding.include.averageCost.setText("cost for two -" + restaurants.getCurrency() + restaurants.getAverage_cost_for_two());
        binding.include.phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] elephantList = restaurants.getPhone_numbers().split(",");
                callPhoneNumber(elephantList[0]);
            }
        });

        setCisines(restaurants.getCuisines());
        setOtherInfo(restaurants.getHighlights());


    }

    private void setAnimationToTitle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.include.scrollbar.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (!isViewVisible(binding.include.name)) {
                        if (binding.title.getVisibility() != View.VISIBLE) {
                            binding.title.startAnimation(mTakeUp);
                            binding.title.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if (binding.title.getVisibility() != View.GONE) {
                            binding.title.startAnimation(mTakeDown);
                            binding.title.setVisibility(View.GONE);
                        }
                    }
                }

                private boolean isViewVisible(View view) {
                    Rect scrollBounds = new Rect();
                    binding.include.scrollbar.getDrawingRect(scrollBounds);

                    float top = view.getY();
                    float bottom = top + view.getHeight();

                    if (scrollBounds.top < top && scrollBounds.bottom > bottom) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }
    }

    private void setCisines(String allCuisine) {
        allCuisine = allCuisine.replace(" ", "");
        String[] cuisines = allCuisine.split(",");
        for (int i = 0; i < cuisines.length; i++) {
            CuisinesView view = new CuisinesView(this);
            view.setText(cuisines[i]);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CuisinesView V = (CuisinesView) v;
                    Toast.makeText(DetailResturantActivity.this, "text is :" + V.getText(), Toast.LENGTH_SHORT).show();
                }
            });
            binding.include.cuisines.addView(view);
        }
    }

    private void setOtherInfo(String[] highlights) {
        for (int i = 0; i < highlights.length; i++) {
            CuisinesView view = new CuisinesView(this);
            view.setText(highlights[i]);
            Drawable img = getDrawable(R.drawable.ic_outline_check_circle_18);
            img.setBounds(0, 0, 32, 32);
            view.setTextColor(Color.parseColor("#525252"));
            view.setCompoundDrawables(img, null, null, null);
            binding.include.otherInfo.addView(view);
        }
    }

    private void setLocationToView(Location location) {
        binding.include.locality.setText(location.getLocality());
        binding.include.address.setText(location.getAddress());
        binding.include.copyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Location", location.getAddress());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(DetailResturantActivity.this, "Address copied", Toast.LENGTH_SHORT).show();
            }
        });
        binding.include.getDirecton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Uri gmmIntentUri = Uri.parse("google.streetview:cbll=" + location.getLatitude() + "," + location.getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }

    ImageView[] rates = new ImageView[5];

    private void setRateToView(Rate rate) {
        rates[0] = binding.include.rate1;
        rates[1] = binding.include.rate2;
        rates[2] = binding.include.rate3;
        rates[3] = binding.include.rate4;
        rates[4] = binding.include.rate5;

        binding.include.AggregateRating.setText(rate.getAggregate_rating());
        binding.include.voteRating.setText("(" + rate.getVotes() + " Reviews)");
        int lastRate = 0;
        String[] arr = rate.getAggregate_rating().split("\\.");
        int firstPart = Integer.parseInt(arr[0]);
        int secondPart = 0;

        for (int i = 0; i < firstPart; i++) {
            rates[i].setVisibility(View.VISIBLE);
            ImageViewCompat.setImageTintList(rates[i], ColorStateList.valueOf(Color.parseColor("#" + rate.getRating_color())));
            lastRate = i;
        }
        if (arr.length > 1) {
            secondPart = Integer.parseInt(arr[1]);
            lastRate++;
        }

        if (secondPart > 0) {
            if (secondPart < 4) {
                rates[lastRate].setImageResource(R.drawable.abc_ic_star_quarter_black_16dp);
            } else if (secondPart >= 4 && (secondPart <= 6)) {
                rates[lastRate].setImageResource(R.drawable.abc_ic_star_half_black_16dp);
            } else if (secondPart > 6) {
                rates[lastRate].setImageResource(R.drawable.abc_ic_star_seventh_black_16dp);
            }
            rates[lastRate].setVisibility(View.VISIBLE);
            ImageViewCompat.setImageTintList(rates[lastRate], ColorStateList.valueOf(Color.parseColor("#" + rate.getRating_color())));

        }
    }

    public void callPhoneNumber(String number) {
        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
                startActivity(callIntent);

            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
                startActivity(callIntent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Log.e(TAG, "Permission not Granted");
            }
        }
    }

}