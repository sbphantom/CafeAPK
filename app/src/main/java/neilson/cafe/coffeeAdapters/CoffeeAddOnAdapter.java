package neilson.cafe.coffeeAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import neilson.cafe.CafeViewController;
import neilson.cafe.CoffeeAddOn;
import neilson.cafe.CoffeeViewController;
import neilson.cafe.R;
import neilson.cafe.SandwichAddOn;
import neilson.cafe.SandwichViewController;

public class CoffeeAddOnAdapter extends ArrayAdapter<CoffeeAddOn> {
    private List<CoffeeAddOn> addonList;
    private CoffeeViewController viewController;

    private List<CoffeeAddOn> selectedItems = new ArrayList<>();

    public CoffeeAddOnAdapter(Context context, List<CoffeeAddOn> addonList, CoffeeViewController viewController) {
        super(context, 0, addonList);
        this.viewController = viewController;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.addon_list_item, parent, false);
        }

        CoffeeAddOn addon = getItem(position);
        TextView addOnName = convertView.findViewById(R.id.addon_name);

        if(addon != null){
            addOnName.setText(addon.toString());
        }

        CheckBox checkbox = convertView.findViewById(R.id.checkbox);
        checkbox.setOnCheckedChangeListener(null); // Remove previous listener to prevent callback during recycling
        checkbox.setChecked(selectedItems.contains(addon)); // Set checkbox state based on selectedItems

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(addon);
                } else {
                    selectedItems.remove(addon);
                }
                viewController.updateSubtotal();

                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    // Method to get the list of selected items
    public List<CoffeeAddOn> getSelectedItems() {
        return selectedItems;
    }
}
