package neilson.cafe.donutAdapters;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import neilson.cafe.DonutFlavor;
import neilson.cafe.OnItemClickListener;
import neilson.cafe.R;

public class DonutFlavorAdapter extends RecyclerView.Adapter<DonutFlavorAdapter.FlavorViewHolder> {
    private List<DonutFlavor> donutFlavorList;
    private OnItemClickListener onItemClickListener;
    private int selectedItem = RecyclerView.NO_POSITION;

    public DonutFlavorAdapter(List<DonutFlavor> flavorList) {
        if (flavorList == null) {
            this.donutFlavorList = new ArrayList<>(); // Initialize an empty list if null is passed
        } else {
            this.donutFlavorList = flavorList;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public FlavorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = new TextView(parent.getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setPadding(15, 15, 15, 15);
        return new FlavorViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull FlavorViewHolder holder, int position) {
        DonutFlavor flavor = donutFlavorList.get(position);
        holder.bind(flavor);
        holder.itemView.setSelected(position == 0); // Select the first item
        holder.itemView.setBackgroundColor(position == selectedItem ? Color.LTGRAY : Color.TRANSPARENT);
    }

    @Override
    public int getItemCount() {
        return donutFlavorList.size();
    }

    public void updateDonutFlavors(List<DonutFlavor> flavors) {
        this.donutFlavorList.clear();
        this.donutFlavorList.addAll(flavors);
        notifyDataSetChanged();
    }

    class FlavorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView flavorTextView;

        public FlavorViewHolder(@NonNull View itemView) {
            super(itemView);
            flavorTextView = (TextView) itemView;
            itemView.setOnClickListener(this);
        }

        public void bind(DonutFlavor flavor) {
            flavorTextView.setText(flavor.toString());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                selectedItem = position;
                notifyDataSetChanged(); // Notify adapter that data has changed to trigger onBindViewHolder
                onItemClickListener.onItemClick(position);
            }
        }
    }
}
