package neilson.cafe.donutAdapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import neilson.cafe.Donut;
import neilson.cafe.OnItemClickListener;

public class PreOrderAdapter extends RecyclerView.Adapter<PreOrderAdapter.PreOrderViewHolder> {
    private List<Donut> preOrders;
    private OnItemClickListener onItemClickListener;
    public PreOrderAdapter(List<Donut> preOrders){
        this.preOrders = preOrders;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PreOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        // Create a TextView to hold the order information
        TextView textView = new TextView(parent.getContext());
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        textView.setPadding(5, 3, 5, 3); //PADDDING ADJUST IF NEEDED
        return new PreOrderViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull  PreOrderViewHolder holder, int position){
        Donut donutPreOder = preOrders.get(position);
        String orderInfo = donutPreOder.toString();
        holder.donutTextView.setText(orderInfo);
    }

    @Override
    public int getItemCount(){return preOrders.size(); }

     class PreOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView donutTextView;
        PreOrderViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            donutTextView = (TextView) itemView;
        }

        @Override
        public void onClick(View v) {
            // Handle the click event
            if (onItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(position);
                }
            }
        }

    }
}
