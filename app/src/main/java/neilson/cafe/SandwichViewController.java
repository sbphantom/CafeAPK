package neilson.cafe;

//
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.scene.layout.*;
//
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import neilson.cafe.sandwichAdapters.AddOnAdapter;
import neilson.cafe.sandwichAdapters.BreadAdapter;
import neilson.cafe.sandwichAdapters.ProteinAdapter;
//
/**
 * This class serves as the main controller for the sandwich ordering window.
 * Orders from this window are sent back to the main cart.
 *
 * @author Adeola Asimolowo, Danny Onuorah
 */
public class SandwichViewController extends AppCompatActivity {
    private RecyclerView breadRecyclerView;
    private RecyclerView protienRecyclerView;
    private BreadAdapter breadAdapter;
    private ProteinAdapter protienAdapter;
    private ListView addOnListView;
    private AddOnAdapter addOnAdapter;
    private EditText subtotalTextNumber;
    private CafeMain main = CafeMain.getInstance();
    private Sandwich sandwich = new Sandwich();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.sandwich_view);



        inflateBreadOptions();
        inflateProteinOptions();
        inflateAddOns();
        subtotalTextNumber = findViewById(R.id.subtotalTextNumber);
        subtotalTextNumber.setFocusable(false);
        subtotalTextNumber.setFocusableInTouchMode(false);
        //Initilizing the subtotal textfield and making it uneditable by the user.


       /* System.out.println("- Sandwich -");
        System.out.println(main.getCurrentOrder().getOrderNumber());
        System.out.println(main.addItem(new Sandwich(SandwichBread.SOUR_DOUGH, SandwichProtein.CHICKEN, new ArrayList<>()), 2));
        System.out.println(main.getCurrentOrder().getSubtotal());
        System.out.println(main.getCurrentOrder().tax());
        System.out.println(main.getCurrentOrder().getTotal());*/
    }

    /**
     * Inflates the RecyclerView with sandwich Bread options and sets up onClickAction
     */
    public void inflateBreadOptions(){
        breadRecyclerView = findViewById(R.id.donutRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        breadRecyclerView.setLayoutManager(layoutManager);
        SandwichBread[] breads = SandwichBread.values();
        breadAdapter = new BreadAdapter(List.of(breads));
        breadRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                breadRecyclerView.findViewHolderForAdapterPosition(0).itemView.performClick();
            }
        });

        breadAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int index) {
                //Handling the bread item click event
                SandwichBread clickedBread = breads[index];
                sandwich.setBread(clickedBread);
            }
        });
        breadRecyclerView.setAdapter(breadAdapter);



    }
    /**
     * Inflates the RecyclerView with sandwich protein  options and sets up onClickAction
     */
    public void inflateProteinOptions(){
        protienRecyclerView = findViewById(R.id.flavorRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        protienRecyclerView.setLayoutManager(layoutManager);
        SandwichProtein[] proteins = SandwichProtein.values();
        protienAdapter = new ProteinAdapter(List.of(proteins));
        protienAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int index) {
                // Handling the  protein item click event
                SandwichProtein clickedProtein = proteins[index];
                sandwich.setProtein(clickedProtein);
                updateSubtotal();
            }
        });
        protienRecyclerView.setAdapter(protienAdapter);

        protienRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                protienRecyclerView.findViewHolderForAdapterPosition(0).itemView.performClick();
            }
        });
    }

    /**
     * Inflates the listview with sandwich AddOn options and sets up Checkbox listener
     */
    public void inflateAddOns() {
        addOnListView = findViewById(R.id.AddOnListView);
        List<SandwichAddOn> addOns = Arrays.asList(SandwichAddOn.values());
        // Create a new AddOnAdapter with the addon list
        addOnAdapter = new AddOnAdapter(this, addOns, this);
        // Set the adapter for the ListView
        addOnListView.setAdapter(addOnAdapter);
        // Set up checkbox listeners directly

    }

    /**
    * updates the subtotal of the sandwich order.
    */
    public void updateSubtotal(){
        ArrayList<SandwichAddOn> addOns = new ArrayList<>(addOnAdapter.getSelectedItems());
        sandwich.setSandwichAddOn(addOns);


        double price = sandwich.price();

        String formattedSubtotal = String.format("$%.2f", price);
        subtotalTextNumber.setText(formattedSubtotal);
        Toast.makeText(this, "Subtotal updated: " + formattedSubtotal, Toast.LENGTH_SHORT).show();

    }

    /**
     * Sends Sandwich item to main cart.
     */
    public void onAddtoCartButtonClick(){
        Button addToCart = findViewById(R.id.addToCartButton);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sandwich finalSandwich = new Sandwich(sandwich.getBread(), sandwich.getProtein(), sandwich.getAddOns());
                main.addItem(finalSandwich , 1);
                sandwich = null;
                updateSubtotal();
            }
        });

    }
}
