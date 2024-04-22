package neilson.cafe.sandwichAdapters;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import neilson.cafe.OnItemClickListener;
import neilson.cafe.SandwichProtein;

public class ProteinAdapter extends RecyclerView.Adapter<ProteinAdapter.ProteinViewHolder> {

    private List<SandwichProtein> proteinList;
    private int selectedItem = RecyclerView.NO_POSITION;

    private OnItemClickListener onItemClickListener;

    public ProteinAdapter(List<SandwichProtein> proteinList) {
        this.proteinList = proteinList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ProteinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creating TextViews for each sandwichBread in the list
        TextView textView = new TextView(parent.getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setPadding(15, 15, 15, 15);

        return new ProteinViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProteinViewHolder holder, int index) {
        SandwichProtein protein = proteinList.get(index);
        holder.bind(protein);
        holder.itemView.setSelected(index == 0); // Select the first item
        holder.itemView.setBackgroundColor(index == selectedItem ? Color.LTGRAY : Color.TRANSPARENT);

    }

    @Override
    public int getItemCount() {
        return proteinList.size();
    }

    class ProteinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView proteinTextView;

        public ProteinViewHolder(@NonNull View itemView) {
            super(itemView);
            proteinTextView = (TextView) itemView;
            itemView.setOnClickListener(this);

        }

        public void bind(SandwichProtein protein) {
            proteinTextView.setText(protein.toString());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                selectedItem = position;
                notifyDataSetChanged(); // Notify adapter that data has changed to trigger onBindViewHolder
                onItemClickListener.onItemClick(position);
            }

        }
    }
}