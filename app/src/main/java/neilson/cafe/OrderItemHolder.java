package neilson.cafe;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class OrderItemHolder extends RecyclerView.ViewHolder {
    private CafeMain main = CafeMain.getInstance();

    private Order order;
    private Context cartView;
    private CartItemAdapter.OnButtonClickListener mListener;

    MenuItem item;
    TextView number;

    TextView subtext;

    public OrderItemHolder(View itemView){
        super(itemView);
        number = itemView.findViewById(R.id.order_number);
        subtext = itemView.findViewById(R.id.order_subtext);

    }

    public void bind(Order order, Context context){
        this.item = item;
        number.setText("Order Number: #" + order.getOrderNumber());
        subtext.setText("Items: " + order.cartSize() + "   Total: " + String.format("$%.2f", order.getTotal()));

        cartView= context;

    }




}
