package neilson.cafe.sandwichAdapters;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import neilson.cafe.SandwichBread;

public class BreadAdapter extends RecyclerView.Adapter<BreadAdapter.BreadViewHolder> {
    private final List<SandwichBread> breadList;
    private OnItemClickListener onItemClickListener;
    private int selectedItem = RecyclerView.NO_POSITION;

    public BreadAdapter(List<SandwichBread> breadList) {
        this.breadList = breadList;
    }

 /*   public interface OnItemClickListener {
        void onItemClick(int position);
    }*/

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public BreadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creating TextViews for each sandwichBread in the list
        TextView textView = new TextView(parent.getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        textView.setPadding(10, 10, 10, 10);

        return new BreadViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull BreadViewHolder holder, int position) {
        SandwichBread bread = breadList.get(position);
        holder.bind(bread);
        if (position == 0) {
            holder.itemView.setSelected(true); // Select the first item
        } else {
            holder.itemView.setSelected(false); // Deselect other items
        }
    }

    @Override
    public int getItemCount() {
        return breadList.size();
    }

    class BreadViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView breadTextView;

        public BreadViewHolder(@NonNull View itemView) {
            super(itemView);
            breadTextView = (TextView) itemView;
            itemView.setOnClickListener(this);
        }

        public void bind(SandwichBread bread) {
            breadTextView.setText(bread.toString());
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