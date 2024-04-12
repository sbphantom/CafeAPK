package neilson.cafe;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Main Controller of the ordering software main menu
 *
 * @author Danny Onuorah
 */
public class CafeViewController  extends AppCompatActivity{
    private CafeMain main;

    /**
     * Links the parent application and stage to class
     *
     * @param main  cafe application
     */
    public void setPrimaryStage(CafeMain main) {
        this.main = main;
    }

    /**
     * Returns the order history
     *
     * @return hashmap of orders
     */
    public LinkedHashMap<Integer, Order> getOrderHistory() {
        return main.getOrderHistory();
    }

    /**
     * Returns the current order
     *
     * @return current order
     */
    public Order getOrder() {
        return main.getCurrentOrder();
    }

    /**
     * Returns the cart of the current order
     *
     * @return cart of the current order
     */
    public HashMap<MenuItem, Integer> getCart() {
        return getOrder().getCart();
    }

    /**
     * Returns the quantity of an item in the current order
     *
     * @param item to check
     * @return quantity in cart
     */
    public int getItemCount(MenuItem item) {
        return main.getCurrentOrder().itemCount(item);
    }

    /**
     * Adds the item and quantity to the current order
     *
     * @param item     to add
     * @param quantity to add
     * @return true if added successfully
     */
    public boolean addItemToOrder(MenuItem item, int quantity) {
        return main.addItem(item, quantity);
    }

    /**
     * Removes the item and quantity from the current order
     *
     * @param item     to remove
     * @param quantity to remove
     * @return true if removed successfully
     */
    public boolean removeItemFromOrder(MenuItem item, int quantity) {
        return main.removeItem(item, quantity);
    }

    /**
     * Places the current order and prepares for the next order
     */
    public void placeOrder() {
        main.addOrder();
    }

    /**
     * Creates a new order
     */
    public void newOrder() {
        main.createOrder();
    }

    /**
     * Remove a previously placed order
     */
    public void removeOrder(int orderNumber) {
        main.removeOrder(orderNumber);
    }

    /**
     * Initialize button ImageViews
     */

    protected void initialize(){


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

//        EdgeToEdge.enable(this);
        setContentView(R.layout.cafe_view);

        ListView listView = findViewById(R.id.menu_list);

        TextView header = new TextView(this);
        header.setText("Order an Item");
        header.setTextSize(20);
        header.setGravity(Gravity.CENTER_HORIZONTAL);
        listView.addHeaderView(header, null, false);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 1:
                    startActivity(new Intent(CafeViewController.this, CoffeeViewController.class));
                    break;
                case 2:
                    startActivity(new Intent(CafeViewController.this, DonutViewController.class));
                    break;
                case 3:
                    startActivity(new Intent(CafeViewController.this, SandwichViewController.class));
                    break;
            }
        });

        String[] menuItems = new String[] {"Coffee ☕", "Donut 🍩", "Sandwich 🥪"};

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItems);

        listView.setAdapter(itemsAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menu_list), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }




















    /**
     * Launches coffee ordering menu
     */

    protected void onCoffeeButtonClick() {

    }

    /**
     * Launches donut ordering menu
     */

    protected void onDonutButtonClick() {

    }

    /**
     * Launches sandwich ordering menu
     */

    protected void onSandwichButtonClick() {
    }

    /**
     * Launches checkout menu
     */

    protected void onCurrentOrderButtonClick() {
    }

    /**
     * Launches order history menu
     */

    protected void onOrderHistoryButtonClick() {

    }




}