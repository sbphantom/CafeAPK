package neilson.cafe.donutAdapters;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import neilson.cafe.DonutFlavor;
import neilson.cafe.OnItemClickListener;

public class DonutFlavorAdapter extends RecyclerView.Adapter<DonutFlavorAdapter.FlavorViewHolder>{
    private List<DonutFlavor> donutFlavorList;
    private OnItemClickListener onItemClickListener;


    public DonutFlavorAdapter(List<DonutFlavor> flavorList){this.donutFlavorList = flavorList;}

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public FlavorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            //Creating Textviews for each donutFlavor in the List.
        TextView textView = new TextView(parent.getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        textView.setPadding(10, 10, 10, 10);

        return new FlavorViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull FlavorViewHolder holder, int position){
        DonutFlavor flavor = donutFlavorList.get(position);
        holder.bind(flavor);
        if(position == 0){
            holder.itemView.setSelected(true);
        }
        else{
            holder.itemView.setSelected(false);
        }
    }

    @Override
    public int getItemCount(){return donutFlavorList.size();}

    public void clear() {
        int size = getItemCount();
        donutFlavorList.clear();
        notifyItemRangeRemoved(0,size);
    }

    class FlavorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView flavorTextView;

        public FlavorViewHolder(@NonNull View itemView){
            super(itemView);
            flavorTextView = (TextView) itemView;
            itemView.setOnClickListener(this);
        }

        public void bind(DonutFlavor flavor){flavorTextView.setText(flavor.toString());}

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION && onItemClickListener !=null){
                onItemClickListener.onItemClick(position);
            }
        }
    }
}
