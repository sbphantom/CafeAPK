package neilson.cafe;

//
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.scene.layout.*;
//
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView;
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















//
//    /**
//     * Initializes the sandwichView window
//     */
//    @FXML
//    public void initialize() {
//
//        addRadioButtonsToBreadColumn();
//        addRadioButtonsToProteinColumn();
//        addCheckboxesToAddOnColumn();
//
//        sandwich.setBread((SandwichBread) breadToggleGroup.getSelectedToggle().getUserData());
//        sandwich.setProtein((SandwichProtein) proteinToggleGroup.getSelectedToggle().getUserData());
//        updateSubtotal();
//
//
//    }
//
//    /**
//     * Adds radio buttons of bread options to gridPane column.
//     */
//    private void addRadioButtonsToBreadColumn() {
//        int row = 1;
//        for (SandwichBread bread : SandwichBread.values()) {
//            RadioButton radioButton = new RadioButton(bread.toString());
//            radioButton.setToggleGroup(breadToggleGroup);
//            radioButton.setUserData(bread);
//
//            sandwichGridPane.add(radioButton, 0, row);
//
//            if (sandwichGridPane.getRowConstraints().size() < row) {
//                RowConstraints rowConstraints = new RowConstraints();
//                rowConstraints.setMinHeight(35);
//                rowConstraints.setPrefHeight(35);
//                rowConstraints.setVgrow(Priority.SOMETIMES);
//                sandwichGridPane.getRowConstraints().add(rowConstraints);
//            }
//            if (row == 1) {
//                radioButton.setSelected(true);
//            }
//
//            row++;
//
//        }
//
//        breadToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null && sandwich != null) {
//                sandwich.setBread((SandwichBread) breadToggleGroup.getSelectedToggle().getUserData());
//                updateSubtotal();
//            }
//        });
//    }
//
//    /**
//     * Adds radio buttons of protein options to gridpane column.
//     */
//    private void addRadioButtonsToProteinColumn() {
//        int row = 1;
//        for (SandwichProtein protein : SandwichProtein.values()) {
//            RadioButton radioButton = new RadioButton(protein.toString());
//            radioButton.setToggleGroup(proteinToggleGroup);
//            radioButton.setUserData(protein);
//
//            sandwichGridPane.add(radioButton, 1, row);
//
//           /* if(sandwichGridPane.getRowConstraints().size() < row){
//                RowConstraints rowConstraints = new RowConstraints();
//                rowConstraints.setMinHeight(35);
//                rowConstraints.setPrefHeight(35);
//                rowConstraints.setVgrow(Priority.SOMETIMES);
//                sandwichGridPane.getRowConstraints().add(rowConstraints);
//            }*/
//
//            if (row == 1) {
//
//                radioButton.setSelected(true);
//            }
//
//            row++;
//
//        }
//
//        proteinToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null && sandwich != null) {
//                sandwich.setProtein((SandwichProtein) proteinToggleGroup.getSelectedToggle().getUserData());
//                updateSubtotal();
//            }
//        });
//    }
//
//    /**
//     * Adds checkboxes of sandwich add-ons to gridPane column.
//     */
//    private void addCheckboxesToAddOnColumn() {
//        int row = 1;
//        for (SandwichAddOn addOn : SandwichAddOn.values()) {
//            CheckBox checkBox = new CheckBox((addOn.toString()));
//            checkBox.setUserData(addOn);
//            checkBox.setOnAction(event -> {
//                if (checkBox.isSelected()) {
//                    sandwich.addAddOn((SandwichAddOn) checkBox.getUserData());
//                } else {
//                    sandwich.removeAddOn((SandwichAddOn) checkBox.getUserData());
//                }
//                updateSubtotal();
//            });
//            sandwichAddOnOptions.add(checkBox);
//            sandwichGridPane.add(checkBox, 2, row);
//            if (sandwichGridPane.getRowConstraints().size() <= row) {
//                RowConstraints rowConstraints = new RowConstraints();
//                rowConstraints.setMinHeight(35);
//                rowConstraints.setPrefHeight(35);
//                rowConstraints.setVgrow(Priority.SOMETIMES);
//                sandwichGridPane.getRowConstraints().add(rowConstraints);
//            }
//            row++;
//        }
//    }
//
//    /**
//     * Sets SandwichViewController as the main controller
//     *
//     * @param controller main screen controller.
//     */
//    public void setMainController(CafeViewController controller) {
//        app = controller;
//    }
//
//    /**
//     * updates the subtotal of the sandwich order.
//     */
//    private void updateSubtotal() {
//        double subtotal = sandwich.price();
//        String formattedSubtotal = String.format("%.2f", subtotal);
//        sandwichSubtotalTextField.setText("$" + formattedSubtotal);
//    }
//
//
//    /**
//     * Adds sandwich order to main cart.
//     */
//    public void onAddOrderButtonClick() {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Confirmation Dialog");
//        alert.setHeaderText("Add to Order");
//        if (sandwich.addOnCount() == 0) {
//            alert.setContentText("Add " + sandwich.name() + " to order?");
//        } else {
//            alert.setContentText("Add " + sandwich.name() + " with " + sandwich.addOnString() + " to order?");
//        }
//
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.isPresent() && result.get() == ButtonType.OK) {
//            app.addItemToOrder(sandwich, 1);
//
//            sandwich = null;
//
//            proteinToggleGroup.selectToggle(proteinToggleGroup.getToggles().getFirst());
//            breadToggleGroup.selectToggle(breadToggleGroup.getToggles().getFirst());
//            for (CheckBox box : sandwichAddOnOptions) {
//                box.setSelected(false);
//            }
//
//            sandwich = new Sandwich((SandwichBread) breadToggleGroup.getSelectedToggle().getUserData(),
//                    (SandwichProtein) proteinToggleGroup.getSelectedToggle().getUserData(), new ArrayList<>());
//            updateSubtotal();
//        }
//    }
//
}
