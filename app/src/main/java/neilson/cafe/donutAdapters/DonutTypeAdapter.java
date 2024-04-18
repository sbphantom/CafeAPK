package neilson.cafe.donutAdapters;

import android.util.TypedValue;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import neilson.cafe.DonutType;
import neilson.cafe.OnItemClickListener;

public class DonutTypeAdapter extends RecyclerView.Adapter<DonutTypeAdapter.DonutTypeViewHolder>{
    private List<DonutType> donutTypeList;
    private OnItemClickListener onItemClickListener;

    public DonutTypeAdapter(List<DonutType> donutTypeList){this.donutTypeList = donutTypeList;}

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public DonutTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        //Creating my textviews for each donutType in the list;
        TextView textView = new TextView(parent.getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        textView.setPadding(10, 10, 10, 10);
        return new DonutTypeViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull DonutTypeViewHolder holder, int position){
        DonutType donutType = donutTypeList.get(position);
        holder.bind(donutType);
        if(position == 0){
            holder.itemView.setSelected(true);
        }
        else {
            holder.itemView.setSelected(false);
        }
    }

    @Override
    public int getItemCount(){return donutTypeList.size();}

    class DonutTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView donutTypeTextView;

        public DonutTypeViewHolder(@NonNull View itemView){
            super(itemView);
            donutTypeTextView = (TextView) itemView;
            itemView.setOnClickListener(this);
        }

        public void bind(DonutType donutType){donutTypeTextView.setText(donutType.toString());}

        @Override
        public void onClick(View v){
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION && onItemClickListener !=null){
                onItemClickListener.onItemClick(position);
            }
        }
    }
}


