package neilson.cafe;
//
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.*;
//

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import neilson.cafe.coffeeAdapters.CoffeeAddOnAdapter;
import neilson.cafe.coffeeAdapters.CoffeeQuantityAdapter;
import neilson.cafe.donutAdapters.DonutQuanityAdapter;
import neilson.cafe.sandwichAdapters.AddOnAdapter;
//

/**
 * Controller class used in coffee view to place orders for coffee
 *
 * @author Danny Onuorah
 */

public class CoffeeViewController extends AppCompatActivity {
    private CafeMain main = CafeMain.getInstance();

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private Coffee coffee = new Coffee();
    private RadioGroup coffeeTypeRadioGroup;
    private ListView coffeeeAddons;
    private CoffeeAddOnAdapter coffeeAddOnAdapter;
    private EditText subtotalTextNumber;
    private Spinner coffeeQuantitySpinner;
    private CoffeeQuantityAdapter coffeeQuantityAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coffee_view);
        inflateCoffeeRadioButtons();
        inflateCoffeeAddOns();
        inflateQuantitySpinner();
        onAddToCartButton();
        subtotalTextNumber = findViewById(R.id.subtotalTextCoffee);
        subtotalTextNumber.setFocusable(false);
        subtotalTextNumber.setFocusableInTouchMode(false);

    }

    private void inflateCoffeeRadioButtons(){
        coffeeTypeRadioGroup = findViewById(R.id.coffeeTypeRadioGroup);
        CoffeeSize[] coffeeSizes = CoffeeSize.values();

        for(int i= 0; i < coffeeSizes.length;i++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
            ));
            radioButton.setTag(coffeeSizes[i]);
            radioButton.setText(coffeeSizes[i].toString());
            coffeeTypeRadioGroup.addView(radioButton);

            if (i == 0) {
                radioButton.setChecked(true);
                coffee.setCoffeeSize(coffeeSizes[i]);
                updateSubtotal();

            }
        }
        coffeeTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                CoffeeSize selectedCoffeeSize = (CoffeeSize) selectedRadioButton.getTag();
                coffee.setCoffeeSize(selectedCoffeeSize);
                updateSubtotal();
                Toast.makeText(getApplicationContext(), "Selected Coffee Size: " + selectedCoffeeSize, Toast.LENGTH_SHORT).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("Order Coffee");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

            }
        });
    }
    private void inflateCoffeeAddOns(){
        coffeeeAddons = findViewById(R.id.coffeeAddOnListView);
        List<CoffeeAddOn> addOns = Arrays.asList(CoffeeAddOn.values());
        coffeeAddOnAdapter = new CoffeeAddOnAdapter(this, addOns, this);
        coffeeeAddons.setAdapter(coffeeAddOnAdapter);

    }

    private void inflateQuantitySpinner(){
        coffeeQuantitySpinner = findViewById(R.id.coffeeQuantitySpinner);
        coffeeQuantityAdapter = new CoffeeQuantityAdapter(this, createQuanityRange());

        coffeeQuantitySpinner.setAdapter(coffeeQuantityAdapter);
        //AutoSelctes the first item in the list.
        coffeeQuantitySpinner.setSelection(0);
        coffeeQuantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedQuantity = (int) parent.getItemAtPosition(position);
                coffee.setQuantity(selectedQuantity);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Create a list of quantity values for the spinner
     * @return
     */
    private List<Integer> createQuanityRange(){
        List<Integer> values = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            values.add(i);
        }
        return values;
    }
    public void updateSubtotal(){
        ArrayList<CoffeeAddOn> addOns= new ArrayList<>(coffeeAddOnAdapter.getSelectedItems());
        coffee.setAddOns(addOns);


        double price = coffee.price() * coffee.getQuantity();

        String formattedSubtotal = String.format("$%.2f", price);
        subtotalTextNumber.setText(formattedSubtotal);
        Toast.makeText(this, "Subtotal updated: " + formattedSubtotal, Toast.LENGTH_SHORT).show();
    }

    private void onAddToCartButton(){
        Button addToCart = findViewById(R.id.addToCartCoffee);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.addItem(coffee, coffee.getQuantity());
                coffee = null;
                updateSubtotal();
            }
        });


    }

}