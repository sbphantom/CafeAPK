package neilson.cafe.donutAdapters;

import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import neilson.cafe.Donut;
import neilson.cafe.DonutFlavor;
import neilson.cafe.OnItemClickListener;

public class PreOrderAdapter extends RecyclerView.Adapter<PreOrderAdapter.PreOrderViewHolder> {
    private List<Donut> preOrders;
    private int selectedItem = RecyclerView.NO_POSITION;

    private OnItemClickListener onItemClickListener;
    public PreOrderAdapter(List<Donut> preOrders){
        this.preOrders = preOrders != null ? preOrders : new ArrayList<>();

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PreOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        TextView textView = new TextView(parent.getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setPadding(15, 15, 15, 15);
        return new PreOrderViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull  PreOrderViewHolder holder, int position){
        Donut donutPreOrder = preOrders.get(position);
        String orderInfo = donutPreOrder.toString();
        holder.donutTextView.setText(orderInfo);
        holder.itemView.setBackgroundColor(position == selectedItem ? Color.LTGRAY : Color.TRANSPARENT);

        if (position == preOrders.size() - 1) {
            holder.donutTextView.setText(orderInfo);
        }
    }

    @Override
    public int getItemCount(){return preOrders.size(); }


    public void addDonut(Donut donut) {
        Log.d("PreOrderAdapter", "Updating preOrderList with new data");

        this.preOrders.add(donut);
        notifyItemInserted(this.preOrders.size() - 1);
        Log.d("PreOrderAdapter", "preOrderAdapterList size after addDonut(): " + preOrders.size());

    }
    public int getSelectedItemPosition() {
        return selectedItem;
    }

    // Method to remove an item from the list
    public void remove(int position) {
        preOrders.remove(position);
    }

    public void clearPreOrders(){preOrders.clear();}
    public List<Donut> getPreOrders() {
        return preOrders;
    }
     class PreOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView donutTextView;
        PreOrderViewHolder(View itemView) {
            super(itemView);
            donutTextView = (TextView) itemView;
            itemView.setOnClickListener(this);
        }

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
