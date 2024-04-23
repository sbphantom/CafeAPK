package neilson.cafe;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemHolder> {
    private List<MenuItem> dataList;
    private CafeMain main = CafeMain.getInstance();

    private Order order = main.getCurrentOrder();

    private Context cartView;
    private OnButtonClickListener mListener;

    public CartItemAdapter(List<MenuItem> dataList, Context context, OnButtonClickListener listener) {
        this.dataList = dataList;
        this.cartView = context;
        mListener = listener;
    }

    @Override
    public CartItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);

        return new CartItemHolder(view);
    }

    @Override
    public void onBindViewHolder(CartItemHolder holder, int position) {
        MenuItem data = dataList.get(position);
//        holder.itemImage.setImageResource(data.getImageResource());
        holder.bind(data, cartView, mListener);

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
