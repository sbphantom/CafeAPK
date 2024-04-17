package neilson.cafe.sandwichAdapters;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import neilson.cafe.R;
import neilson.cafe.SandwichAddOn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import neilson.cafe.R;
import neilson.cafe.SandwichAddOn;


public class AddOnAdapter extends ArrayAdapter<SandwichAddOn> {

    public AddOnAdapter(Context context, List<SandwichAddOn> addonList) {
        super(context, 0, addonList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = new TextView(getContext());
            textView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textView.setPadding(10, 10, 10, 10);
        } else {
            textView = (TextView) convertView;
        }

        SandwichAddOn addon = getItem(position);
        assert addon != null;
        textView.setText(addon.toString());

        return textView;
    }
}