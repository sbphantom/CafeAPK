package neilson.cafe.sandwichAdapters;

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

        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        textView.setPadding(10, 10, 10, 10);

        return new ProteinViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProteinViewHolder holder, int index) {
        SandwichProtein protein = proteinList.get(index);
        holder.bind(protein);
        holder.itemView.setSelected(index == 0); // Select the first item
    }

    @Override
    public int getItemCount() {
        return proteinList.size();
    }

    class ProteinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView proteinTextView;

        public ProteinViewHolder(@NonNull View itemView) {
            super(itemView);
            proteinTextView = (TextView) itemView; // A
            itemView.setOnClickListener(this);

        }

        public void bind(SandwichProtein protein) {
            proteinTextView.setText(protein.toString());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }

        }
    }
}