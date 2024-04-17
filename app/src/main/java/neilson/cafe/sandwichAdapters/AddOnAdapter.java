package neilson.cafe.sandwichAdapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import neilson.cafe.SandwichAddOn;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;


public class AddOnAdapter extends ArrayAdapter<SandwichAddOn> {
    private boolean[]  checkedItems;
    public AddOnAdapter(Context context, List<SandwichAddOn> addonList) {
        super(context, 0, addonList);
        checkedItems = new boolean[addonList.size()];
        Arrays.fill(checkedItems, false); // Initialize all items as unchecked

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LinearLayout itemView;

        if (convertView == null) {
            // Create a new LinearLayout for each item
            itemView = new LinearLayout(getContext());
            itemView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            itemView.setOrientation(LinearLayout.HORIZONTAL);

            // Create a checkbox and set its layout parameters
            CheckBox checkbox = new CheckBox(getContext());
            LinearLayout.LayoutParams checkboxParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            checkbox.setLayoutParams(checkboxParams);

            // Create a text view and set its layout parameters
            TextView textView = new TextView(getContext());
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            textParams.setMargins(5, 0, 0, 0); // Add margin to the left of the text view
            textView.setLayoutParams(textParams);

            // Add the checkbox and text view to the linear layout
            itemView.addView(checkbox);
            itemView.addView(textView);
        } else {
            // Reuse the convertView if available
            itemView = (LinearLayout) convertView;
        }

        //Get current Item.
        SandwichAddOn addon = getItem(position);
        if (addon != null) {
            // Find the checkbox and text view in the linear layout
            CheckBox checkbox = (CheckBox) itemView.getChildAt(0);
            TextView textView = (TextView) itemView.getChildAt(1);

            // Set the text of the text view
            textView.setText(addon.toString());

            // Set a tag to the checkbox to identify its position
            checkbox.setTag(position);

            // Set the checkbox state based on the checkedItems array
            checkbox.setChecked(checkedItems[position]);

            // Set a listener to handle checkbox state changes
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Get the position of the checkbox from its tag
                    int position = (int) buttonView.getTag();
                    // Update the state of the checkbox at the specified position
                    checkedItems[position] = isChecked;
                }
            });
        }

        return itemView;
    }

    // Method to get the list of selected items
    public ArrayList<SandwichAddOn> getSelectedItems() {
        ArrayList<SandwichAddOn> selectedItems = new ArrayList<>();
        for (int i = 0; i < getCount(); i++) {
            if (checkedItems[i]) {
                selectedItems.add(getItem(i));
            }
        }
        return selectedItems;
    }

    // Method to set the listener for checkbox changes
    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        for (int i = 0; i < checkedItems.length; i++) {
            checkedItems[i] = false; // Reset the checked items
        }
        notifyDataSetChanged(); // Refresh the ListView
        for (int i = 0; i < getCount(); i++) {
            checkedItems[i] = false; // Reset the checked items
        }
        notifyDataSetChanged(); // Refresh the ListView
        notifyDataSetChanged();
    }




}