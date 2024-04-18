package neilson.cafe;


import android.os.Bundle;
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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import neilson.cafe.donutAdapters.DonutFlavorAdapter;
import neilson.cafe.donutAdapters.DonutQuanityAdapter;
import neilson.cafe.donutAdapters.DonutTypeAdapter;
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
    private RecyclerView donutRecycler;
    private RecyclerView flavorRecycler;
    private Spinner quantitySpinner;
    private Button addPreOrder;
    private Button deletePreOrder;
    private RecyclerView  preOrderRecycler;
    private EditText subtotalText;
    private Button addToOrderButton;
    private DonutTypeAdapter donutTypeAdapter;
    private DonutFlavorAdapter donutFlavorAdapter;
    private DonutQuanityAdapter donutQuanityAdapter;
    private PreOrderAdapter preOrderAdapter; 
    private List<Donut> preOrderList = new ArrayList<>();;
    private Donut donut = new Donut();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        main = new CafeMain();

        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.donut_view);

        inflateDonutRecycler();
        initalizePreOrderRecycler();
        onAddPreOrderClick();
        onDeletePreOrderClick();
        /*
        System.out.println("- Donut -");
        System.out.println(main.getCurrentOrder().getOrderNumber());
        System.out.println(main.addItem(new Donut(DonutType.CAKE, DonutFlavor.CHOCOLATE), 2));
        System.out.println(main.getCurrentOrder().getSubtotal());
        System.out.println(main.getCurrentOrder().tax());
        System.out.println(main.getCurrentOrder().getTotal());
        */

    }

    private void inflateDonutRecycler(){
        donutRecycler = findViewById(R.id.donutRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        donutRecycler.setLayoutManager(layoutManager);
        DonutType[] donutTypes =  DonutType.values();
        donutTypeAdapter = new DonutTypeAdapter(List.of(donutTypes));
        donutTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int index) {
                DonutType clickedDonutType = donutTypes[index];
                donut.setType(clickedDonutType);
                populateDonutFlavors(clickedDonutType);
            }
        });
    }

    private void populateDonutFlavors(DonutType donutType){
        flavorRecycler = findViewById(R.id.flavorRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        flavorRecycler.setLayoutManager(layoutManager);
        DonutFlavor[] flavors = donutType.getFlavors().toArray(new DonutFlavor[0]);
        donutFlavorAdapter = new DonutFlavorAdapter(List.of(flavors));


        donutFlavorAdapter.clear();
        donutFlavorAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                DonutFlavor clickedFlavor  = flavors[position];
                donut.setFlavor(clickedFlavor);
            }
        });

        donutRecycler.setAdapter(donutFlavorAdapter);
    }

    private List<Integer> createQuanityRange(){
        List<Integer> values = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            values.add(i);
        }
        return values;
    }

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

    private void initalizePreOrderRecycler(){
        preOrderRecycler = findViewById(R.id.preOrderRecycler);
        preOrderRecycler.setLayoutManager(new LinearLayoutManager(this));
        preOrderAdapter = new PreOrderAdapter(preOrderList);
        preOrderRecycler.setAdapter(preOrderAdapter);
    }

    private void onAddPreOrderClick(){
      addPreOrder = findViewById(R.id.addPreOrder);
      addPreOrder.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Donut finalDonut = new Donut();
            finalDonut.setQuantity(donut.getQuantity());
            finalDonut.setType(donut.getType());
            finalDonut.setFlavor(donut.getFlavor());

            preOrderList.add(finalDonut);
            preOrderAdapter.notifyDataSetChanged();
            updateSubtotal("add", finalDonut);

          }
      });

    }

    private void onDeletePreOrderClick() {
        deletePreOrder = findViewById(R.id.deletePreOrder);
        preOrderAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position != RecyclerView.NO_POSITION) {
                    Donut selectedDonutOrder = preOrderList.get(position);
                    preOrderList.remove(selectedDonutOrder);
                    updateSubtotal("sub", selectedDonutOrder);
                    preOrderAdapter.notifyItemRemoved(position);

                } else{
                    Toast.makeText(DonutViewController.this, "Please select an donut to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateSubtotal(String operation, Donut donut){

        String subtotalStr = subtotalText.getText().toString();
        double subtotal = subtotalStr.isEmpty() ? 0 : Double.parseDouble(subtotalStr.substring(1));

        switch (operation){
            case "add":
                subtotal += donut.price() * donut.getQuantity();

            case "sub":
                subtotal -= donut.price() * donut.getQuantity();
            default:
                break;
        }
        String formattedSubtotal = String.format("$%.2f", subtotal);
        subtotalText.setText(formattedSubtotal);;
    }




























//
//    @FXML
//    public void initialize() {
//        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("img/addToCart2.png")));
//        ImageView imgView = new ImageView(img);
//        imgView.setFitWidth(90);
//        imgView.setFitHeight(50);
//        addOrder.setGraphic(imgView);
//        donutSubtotalTextField.setText("$0.00");
//        OnAddButtonClick();
//        onDeleteButtonClick();
//        populateQuantityComboBox();
//        addRadioToDonutTypeColumn();
//        setListViews();
//
//    }
//
//    /**
//     * Adds available donut types to the screen
//     */
//    private void addRadioToDonutTypeColumn() {
//        flavorListView.getSelectionModel().selectFirst();
//
//        int row = 0;
//        for (DonutType donutRadioType : DonutType.values()) {
//            RadioButton radioButton = new RadioButton(donutRadioType.toString());
//            radioButton.setToggleGroup(donutTypeToggleGroup);
//            radioButton.setUserData(donutRadioType);
//
//            DonutTypesGridPane.add(radioButton, 0, row);
//
//            if (DonutTypesGridPane.getRowConstraints().size() < row) {
//                RowConstraints rowConstraints = new RowConstraints();
//                rowConstraints.setMinHeight(35);
//                rowConstraints.setPrefHeight(20);
//                rowConstraints.setVgrow(Priority.SOMETIMES);
//                DonutTypesGridPane.getRowConstraints().add(rowConstraints);
//            }
//            if (row == 0) {
//                radioButton.setSelected(true);
//            }
//            row++;
//        }
//        flavorListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        populateFlavors((DonutType) donutTypeToggleGroup.getSelectedToggle().getUserData());
//
//        donutTypeToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//
//                DonutType selectedType = (DonutType) newValue.getUserData();
//                populateFlavors(selectedType);
//                String imagePath = changeImage(selectedType);
//                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
//                donutImage.setImage(image);
//            }
//        });
//    }
//
//    /**
//     * Sets new imagePath for the currently selected donutType by user
//     *
//     * @param selectedType Donut Type
//     * @return new image path
//     */
//    public String changeImage(DonutType selectedType) {
//        String imagePath = "";
//        switch (selectedType) {
//            case DonutType.YEAST:
//                imagePath = "img/yeast.jpg";
//                break;
//            case DonutType.HOLE:
//                imagePath = "img/holes.jpg";
//                break;
//            case DonutType.CAKE:
//                imagePath = "img/cake.jpg";
//                break;
//            default:
//                break;
//        }
//        return imagePath;
//    }
//
//    /**
//     * Populates the flavors Listview based on the type of donut selected.
//     *
//     * @param donut
//     */
//    private void populateFlavors(DonutType donut) {
//        flavorListView.setItems(flavors);
//        flavors.clear();
//        flavors.addAll(donut.getFlavors());
//        flavorListView.getSelectionModel().selectFirst();
//
//    }
//
//    /**
//     * initializes the Quantity comboBox from numbers 1 thru 12.
//     * Defaults to 1.
//     */
//    @SuppressWarnings("unchecked")
//    private void populateQuantityComboBox() {
//        for (int i = 1; i <= 12; i++) {
//            donutQuantity.getItems().add(i);
//        }
//        donutQuantity.setValue(1);
//    }
//
//
//    /**
//     * Adds Donut to the preOrder Listview and ObservableList.
//     */
//    public void OnAddButtonClick() {
//        addButtonPreOrder.setOnAction(event -> {
//            // Add a new donut to the preorder list
//
//            Donut donut = new Donut();
//            donut.setType((DonutType) donutTypeToggleGroup.getSelectedToggle().getUserData());
//            donut.setFlavor((DonutFlavor) flavorListView.getSelectionModel().getSelectedItem());
//            donut.setQuantity((int) donutQuantity.getValue());
//            preOrdersList.add(donut);
//            updateSubtotal("add", donut);
//
//        });
//    }
//
//    /**
//     * Initializes all the listview to their corresponding ObservableList
//     */
//    private void setListViews() {
//        preOrders.setItems(preOrdersList);
//        flavorListView.setItems(flavors);
//    }
//
//    /**
//     * Removes selected Donut from the preOrder Listview and ObservableList.
//     */
//    public void onDeleteButtonClick() {
//        deleteButtonPreOrder.setOnAction(event -> {
//            Donut selectedItem = (Donut) preOrders.getSelectionModel().getSelectedItem();
//            if (selectedItem != null) {
//                preOrdersList.remove(selectedItem);
//                updateSubtotal("sub", selectedItem);
//            }
//        });
//    }
//
//    /**
//     * Sends the order of donuts to the main cart.
//     */
//    //Iterate through preOrder arrays and send to main cart, after clear the preOrder list
//    public void OnAddOrderButtonClick() {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Confirmation Dialog");
//        alert.setHeaderText("Add to Order");
//        StringBuilder sb = new StringBuilder();
//        for (Donut donut : preOrdersList) {
//            sb.append(donut.toString()).append(" ");
//        }
//
//        alert.setContentText("Add " + sb + " to order?");
//
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.isPresent() && result.get() == ButtonType.OK) {
//
//            for (Donut donut : preOrdersList) {
//                app.addItemToOrder(donut, donut.getQuantity());
//                updateSubtotal("sub", donut);
//            }
//
//            preOrdersList.clear();
//            donutQuantity.getSelectionModel().selectFirst();
//            donutTypeToggleGroup.selectToggle(donutTypeToggleGroup.getToggles().getFirst());
//
//        }
//    }
//
//    /**
//     * Updates the running subtotal.
//     * Adds to subtotal if we are adding to preOrder Listview .
//     * Subtracts if we are removing a donut from the preOrder Listview.
//     *
//     * @param operation add or subtract from current subtotal
//     * @param donut     pass the donut currently being selected.
//     */
//    private void updateSubtotal(String operation, Donut donut) {
//
//        String subtotalText = donutSubtotalTextField.getText();
//        double subtotal = subtotalText.isEmpty() ? 0 : Double.parseDouble(subtotalText.substring(1));
//
//        switch (operation) {
//            case "add":
//                subtotal += donut.price() * donut.getQuantity();
//                break;
//            case "sub":
//                subtotal -= donut.price() * donut.getQuantity();
//                break;
//            default:
//                break;
//        }
//
//        String formattedSubtotal = String.format("%.2f", subtotal);
//        donutSubtotalTextField.setText("$" + formattedSubtotal);
//
//    }
//
//    /**
//     * Sets DonutViewController as the main screen
//     */
//    public void setMainController(CafeViewController controller) {
//        app = controller;
//    }
//
}
