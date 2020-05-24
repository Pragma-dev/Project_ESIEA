package com.example.project_esiea.presentation.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_esiea.R;
import com.example.project_esiea.presentation.model.Countries;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements Filterable {
    private List<Countries> values;
    private List<Countries> valuesFull;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Countries item);
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView txtHeader;
        View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader =  v.findViewById(R.id.Title2);
        }
    }

    public void add(int position, Countries item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    private void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListAdapter(List<Countries> myDataset, OnItemClickListener listener) {
        this.values = myDataset;
        this.listener = listener;
        valuesFull = new ArrayList<>(values);
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent,
            int viewType)
    {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Countries currentCountries = values.get(position);
        holder.txtHeader.setText(currentCountries.getCountry());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(currentCountries);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

    @Override
    public Filter getFilter() {
        return valuesFilter;
    }

    private Filter valuesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Countries> filteredList = new ArrayList<>();

            if( constraint == null || constraint.length() == 0){
                filteredList.addAll(valuesFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Countries countries : valuesFull){
                    if(countries.getCountry().toLowerCase().contains(filterPattern)){
                        filteredList.add(countries);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            values.clear();
            values.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
