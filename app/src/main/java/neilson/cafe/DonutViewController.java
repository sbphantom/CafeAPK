package neilson.cafe;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

//
//import javafx.fxml.FXML;
//import javafx.stage.Stage;
//import javafx.scene.control.*;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.ColumnConstraints;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.Priority;
//import javafx.scene.layout.RowConstraints;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import neilson.cafe.donutAdapters.DonutFlavorAdapter;
import neilson.cafe.donutAdapters.DonutQuanityAdapter;
import neilson.cafe.donutAdapters.PreOrderAdapter;
//
/**
 * This class serves as the main controller for the donut ordering window.
 * Orders from this window are sent back to the main cart.
 *
 * @author Adeola Asimolowo
 */

public class DonutViewController extends AppCompatActivity {

    private CafeMain main = CafeMain.getInstance();
    private RecyclerView flavorRecycler;
    private Spinner quantitySpinner;
    private ImageButton addPreOrder;
    private ImageButton deletePreOrder;
    private RecyclerView  preOrderRecycler;
    private EditText subtotalText;
    private Button addToOrderButton;
    private DonutQuanityAdapter donutQuanityAdapter;
    private PreOrderAdapter preOrderAdapter; 
    private List<Donut> preOrderList = new ArrayList<>();;
    private Donut donut = new Donut();
    private DonutFlavorAdapter flavorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donut_view_test);

        // Initialize UI components
        inflateDonutRadioButtons();
        inflateQuantitySpinner();
        initalizePreOrderRecycler();

        // Set click listeners for add and delete buttons
        onAddPreOrderClick();
        onDeletePreOrderClick();
        onAddToCart();
    }

    /**
     * Inflate radio buttons for selecting donut types
     */
    private void inflateDonutRadioButtons(){
        RadioGroup donutTypeRadioGroup = findViewById(R.id.donutTypeRadioGroup);
        DonutType[] donutTypes = DonutType.values();

        for (int i = 0; i < donutTypes.length; i++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
            ));
            radioButton.setTag(donutTypes[i]);
            radioButton.setText(donutTypes[i].toString());
            donutTypeRadioGroup.addView(radioButton);

            if (i == 0) {
                radioButton.setChecked(true);
                setDonutImage(donutTypes[i]);
                donut.setType(donutTypes[i]);
                populateDonutFlavors(donut.getType());
            }
        }
        donutTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton selectedRadioButton = findViewById(checkedId);
                DonutType selectedDonutType = (DonutType) selectedRadioButton.getTag();
                setDonutImage(selectedDonutType);
                donut.setType(selectedDonutType);
                populateDonutFlavors(selectedDonutType);

                Toast.makeText(getApplicationContext(), "Selected Donut Type: " + selectedDonutType, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *  Set the donut image based on the selected donut type
     * @param donutType user selected donut type
     */
    private void setDonutImage(DonutType donutType) {
        ImageView donutImageView = findViewById(R.id.donutImageView);
        int drawableId;
        switch (donutType) {
            case YEAST:
                drawableId = R.drawable.yeast;
                break;
            case CAKE:
                drawableId = R.drawable.cake;
                break;
            case HOLE:
                drawableId = R.drawable.holes;
                break;
            default:
                drawableId = R.drawable.yeast;
                break;
        }
        donutImageView.setImageResource(drawableId);
    }

    /**
     *  Populate the list of donut flavors based on the selected donut type
     * @param donutType user selected donut type
     */
    private void populateDonutFlavors(DonutType donutType){

        flavorRecycler = findViewById(R.id.flavorRecycler);
        flavorAdapter = new DonutFlavorAdapter(new ArrayList<>());
        flavorRecycler.setLayoutManager(new LinearLayoutManager(this));
        flavorRecycler.setAdapter(flavorAdapter);


        List<DonutFlavor> flavors = donutType.getFlavors();
        flavorAdapter.updateDonutFlavors(flavors);
        flavorAdapter.notifyDataSetChanged();
        flavorAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
               DonutFlavor clickedFlavor = flavors.get(position);
                donut.setFlavor(clickedFlavor);
                setDonutFlavorImg(clickedFlavor);
            }
        });
        flavorRecycler.post(new Runnable() {
            @Override
            public void run() {
                flavorRecycler.findViewHolderForAdapterPosition(0).itemView.performClick();
            }
        });
    }

    /**
     * Set the donut flavor image based on the selected flavor
     * @param flavor selected Flavor
     */
    private void setDonutFlavorImg(DonutFlavor flavor){
        ImageView donutFlavorImg = findViewById(R.id.donutFlavorImg);
        int drawableId;

        switch(flavor){
            case JELLY -> drawableId = R.drawable.jelly;
            case MAPLE -> drawableId =  R.drawable.maple;
            case PLAIN -> drawableId = R.drawable.plain;
            case SUGAR -> drawableId = R.drawable.sugar;
            case COFFEE -> drawableId = R.drawable.coffeedonut;
            case GLAZED -> drawableId = R.drawable.glazed;
            case POWDER -> drawableId =R.drawable.powder;
            case CRULLER ->drawableId = R.drawable.cruller;
            case VANILLA -> drawableId = R.drawable.vanilla;
            case BLUEBERRY -> drawableId = R.drawable.blueberry;
            case CHOCOLATE -> drawableId = R.drawable.chocolate;
            case STRAWBERRY -> drawableId = R.drawable.strawberry;
            default -> drawableId = R.drawable.donut_icon;

        }
        donutFlavorImg.setImageResource(drawableId);
    }

    /**
     * Inflate spinner for selecting quantity of donuts
     */
    private void inflateQuantitySpinner(){
        quantitySpinner = findViewById(R.id.quantitySpinner);
        donutQuanityAdapter = new DonutQuanityAdapter(this, createQuanityRange());

        quantitySpinner.setAdapter(donutQuanityAdapter);
        //AutoSelctes the first item in the list.
        quantitySpinner.setSelection(0);
        quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedQuantity = (int) parent.getItemAtPosition(position);
                donut.setQuantity(selectedQuantity);
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

    /**
     * Initializes the RecyclerView for pre-order donuts
     */
    private void initalizePreOrderRecycler(){
        preOrderRecycler = findViewById(R.id.preDonutOrderRecycler);
        preOrderRecycler.setLayoutManager(new LinearLayoutManager(this));
        Log.d("PreOrderRecycler", "preOrderRecycler initialized: " + preOrderRecycler);
        preOrderAdapter = new PreOrderAdapter(preOrderList);
        preOrderRecycler.setAdapter(preOrderAdapter);
        preOrderAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click event here
                // You can get the clicked item position and perform actions accordingly
                // For example, display a toast message
                //Toast.makeText(DonutViewController.this, "Item clicked at position: " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Handles click event for adding a pre-order donut
     */
    private void onAddPreOrderClick(){
      addPreOrder = findViewById(R.id.plusButton);
      addPreOrder.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Log.d("onClick", "Add button clicked");

              Donut finalDonut = new Donut();
              finalDonut.setQuantity(donut.getQuantity());
              finalDonut.setType(donut.getType());
              finalDonut.setFlavor(donut.getFlavor());
              Log.d("PreOrderList", "Adding new donut to preOrderList: " + finalDonut.toString());
              Log.d("PreOrderList", "preOrderList size after addition: " + preOrderList.size());
              preOrderAdapter.addDonut(finalDonut);

              // Update the subtotal after adding the donut
              updateSubtotal("add", finalDonut);

          }
      });

    }

    /**
     * Handles click event for deleting a pre-order donut
     */
    private void onDeletePreOrderClick() {
        deletePreOrder = findViewById(R.id.minusButton);
        deletePreOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!preOrderList.isEmpty()) {
                    int position = preOrderAdapter.getSelectedItemPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        Donut selectedDonutOrder =  preOrderList.remove(position);

                        preOrderAdapter.notifyItemRemoved(position);

                        updateSubtotal("sub", selectedDonutOrder);
                    } else {
                        Toast.makeText(DonutViewController.this, "Please select a donut to delete", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DonutViewController.this, "There are no donuts to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onAddToCart(){
       Button onAddToCartDonut = findViewById(R.id.addToCartDonut);
       onAddToCartDonut.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               for(Donut donut :  preOrderAdapter.getPreOrders()){
                   updateSubtotal("sub",donut);
                   main.addItem(donut, donut.getQuantity());
               }

               preOrderAdapter.clearPreOrders();
               preOrderAdapter.notifyDataSetChanged();
           }
       });




    }
    /**
     *  Update the subtotal based on the operation and the donut details
     * @param operation add for addition, sub for substitution
     * @param donut donut to calculate subtotal
     */
    private void updateSubtotal(String operation, Donut donut){
        subtotalText = findViewById(R.id.subtotalTextCoffee);
        subtotalText.setFocusable(false);
        subtotalText.setFocusableInTouchMode(false);
        String subtotalStr = subtotalText.getText().toString();
        double subtotal = 0;

        if (!subtotalStr.isEmpty() && subtotalStr.startsWith("$")) {
            String numericSubtotalStr = subtotalStr.substring(1);
            subtotal = Double.parseDouble(numericSubtotalStr);
        }

        switch (operation){
            case "add":
                subtotal += donut.price() * donut.getQuantity();
                break;
            case "sub":
                subtotal -= donut.price() * donut.getQuantity();
                break;
            default:
                break;
        }

        String formattedSubtotal = String.format("$%.2f", subtotal);
        subtotalText.setText(formattedSubtotal);
    }


}
