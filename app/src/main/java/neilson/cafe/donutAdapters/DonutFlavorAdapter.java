package neilson.cafe.donutAdapters;

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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_recyclerview_item2, parent, false);
        return new FlavorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FlavorViewHolder holder, int position) {
        DonutFlavor flavor = donutFlavorList.get(position);
        holder.bind(flavor);
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
            flavorTextView = itemView.findViewById(R.id.donutFlavorTextView);
            itemView.setOnClickListener(this);
        }

        public void bind(DonutFlavor flavor) {
            flavorTextView.setText(flavor.toString());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        }
    }
}
