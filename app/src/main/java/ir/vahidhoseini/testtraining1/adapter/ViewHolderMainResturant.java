package ir.vahidhoseini.testtraining1.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ir.vahidhoseini.testtraining1.R;

public class ViewHolderMainResturant extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView featured_image;
    TextView timing;
    TextView cuisines;
    TextView name;
    TextView average_cost;
    OnClickListener onResturantListener;

    public ViewHolderMainResturant(@NonNull View itemView, OnClickListener onResturantListener) {
        super(itemView);
        this.onResturantListener = onResturantListener;
        featured_image = itemView.findViewById(R.id.featured_image);
        timing = itemView.findViewById(R.id.timing);
        cuisines = itemView.findViewById(R.id.cuisines);
        name = itemView.findViewById(R.id.name);
        average_cost = itemView.findViewById(R.id.average_cost);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        onResturantListener.onClickListener(getAdapterPosition());
    }
}
