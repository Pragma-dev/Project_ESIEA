package com.example.project_esiea;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements Filterable {
    private List<Countries> values;
    private List<Countries> valuesFull;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView txtHeader;
        TextView txt1;
        TextView txt2;
        TextView txt3;
        TextView txt4;
        TextView txt5;
        TextView txt6;

        View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.Title);
            txt1 = (TextView) v.findViewById(R.id.Line1);
            txt2 = (TextView) v.findViewById(R.id.Line2);
            txt3 = (TextView) v.findViewById(R.id.Line3);
            txt4 = (TextView) v.findViewById(R.id.Line4);
            txt5 = (TextView) v.findViewById(R.id.Line5);
            txt6 = (TextView) v.findViewById(R.id.Line6);

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
    public ListAdapter(List<Countries> myDataset) {
        values = myDataset;
        valuesFull = new ArrayList<>(values);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent,
            int viewType)
    {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Countries currentCountries = values.get(position);
        holder.txtHeader.setText(currentCountries.getCountry());

        holder.txt1.setText("New cases today : "+currentCountries.getNewConfirmed());
        holder.txt2.setText("Total cases : "+currentCountries.getTotalConfirmed());
        holder.txt3.setText("New deaths today : "+currentCountries.getNewDeaths());
        holder.txt4.setText("Total deaths : "+currentCountries.getTotalDeaths());
        holder.txt5.setText("New recovered today : "+currentCountries.getNewRecovered());
        holder.txt6.setText("Total recovered : "+currentCountries.getTotalRecovered());
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
