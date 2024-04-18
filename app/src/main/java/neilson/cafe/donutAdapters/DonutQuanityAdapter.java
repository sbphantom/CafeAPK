package neilson.cafe.donutAdapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DonutQuanityAdapter extends ArrayAdapter<Integer> {
    public DonutQuanityAdapter(@NonNull Context context, @NonNull List<Integer> quantityValues) {
        super(context, android.R.layout.simple_spinner_item, quantityValues);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        TextView textView = (TextView) super.getView(position, convertView, parent);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        return textView;
    }

    @NonNull
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        return textView;
    }

}
