package neilson.cafe;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CartItemHolder extends RecyclerView.ViewHolder {
    private CafeMain main = CafeMain.getInstance();

    private Order order = main.getCurrentOrder();
    private Context cartView;
    private CartItemAdapter.OnButtonClickListener mListener;

    MenuItem item;
    TextView name;
    TextView quantity;

    TextView subtotal;
    TextView subtext;

    Button decrement;
    Button increment;
    public CartItemHolder(View itemView){
        super(itemView);
        name = itemView.findViewById(R.id.order_number);
        quantity = itemView.findViewById(R.id.item_quantity);
        subtotal = itemView.findViewById(R.id.item_subtotal);
        subtext = itemView.findViewById(R.id.order_subtext);
        decrement = itemView.findViewById(R.id.decrement_button);
        increment = itemView.findViewById(R.id.increment_button);

//        RecyclerView rv = findViewById(R.id.cart_list);
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                // Do something with this item
                order.removeItem(item, 1);
                quantity.setText(Integer.toString(order.itemCount(item)));
                subtotal.setText(String.format("$%.2f", item.price() * order.itemCount(item)));
                mListener.onButtonClick(position, item);


//                Log.d("RecyclerView", "Item " + position + " clicked.");
            }
        });


        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                // Do something with this item
                order.addItem(item, 1);
                quantity.setText(Integer.toString(order.itemCount(item)));
                subtotal.setText(String.format("$%.2f", item.price() * order.itemCount(item)));
                mListener.onButtonClick(position, item);

//                Log.d("RecyclerView", "Item " + position + " clicked.");
            }
        });
    }

    public void bind(MenuItem item, Context context, CartItemAdapter.OnButtonClickListener listner){
        this.item = item;
        this.mListener=listner;
        cartView= context;
        name.setText(item.name());
        quantity.setText(Integer.toString(order.itemCount(item)));
        subtotal.setText(String.format("$%.2f", item.price() * order.itemCount(item)));
        subtext.setText(item.addOnString());



        }




}
