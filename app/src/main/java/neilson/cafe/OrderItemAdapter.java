package neilson.cafe;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedHashMap;
import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemHolder> {
    private List<Order> dataList;
    private CafeMain main = CafeMain.getInstance();

    private LinkedHashMap<Integer, Order> history = main.getOrderHistory();

    private Context cartView;


    public OrderItemAdapter(List<Order> dataList, Context context ){
        this.dataList = dataList;
        this.cartView = context;

    }

    @Override
    public OrderItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);

        return new OrderItemHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderItemHolder holder, int position) {
        Order data = dataList.get(position);
//        holder.itemImage.setImageResource(data.getImageResource());
        holder.bind(data, cartView);

//        holder.name.setText(data.name());
//        holder.quantity.setText(Integer.toString(order.itemCount(data)));
//        holder.subtotal.setText(String.format("%.2f", data.price() * order.itemCount(data)));
//        holder.subtext.setText(data.addOnString());

    }

    public interface OnButtonClickListener {
        void onButtonClick(int position, MenuItem item);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
