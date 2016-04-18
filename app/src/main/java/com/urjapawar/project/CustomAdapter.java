package com.urjapawar.project;


import java.util.ArrayList;
import java.util.Locale;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;

public class CustomAdapter extends BaseAdapter implements Filterable{
    ArrayList<RowItem> filteredData;
    Context context;
    ArrayList<RowItem> rowItems;
    CustomFilter filter;
    String searchString="";
    public int t;

    public CustomAdapter(Context context, ArrayList<RowItem> items) {
        this.context = context;
        this.rowItems = items;
        this.filteredData = items;
    }
   @Override
    public int getCount() {
        return filteredData.size();
    }

   @Override
    public Object getItem(int position) {
        return filteredData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }



    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
        TextView pubname;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.pubname = (TextView) convertView.findViewById(R.id.pub);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        RowItem rowItem = (RowItem) getItem(position);

            holder.imageView.setImageResource(rowItem.getImageId());
        String fullText = rowItem.getDesc();
        String fullText2 = rowItem.getTitle();
        String fullText3 = rowItem.getPub();
        if (searchString != null && !searchString.isEmpty()) {
            Spannable spannable = new SpannableString(fullText);
            Spannable spannable1 = new SpannableString(fullText2);
            Spannable spannable3 = new SpannableString(fullText3);
            String[] searchWords = searchString.split(" ");
            for (String searchWord : searchWords) {
                int startPos = fullText.toLowerCase(Locale.US).indexOf(searchWord.toLowerCase(Locale.US));
                int endPos = startPos + searchWord.length();
                int startpos2 = fullText2.toLowerCase(Locale.US).indexOf(searchWord.toLowerCase(Locale.US));
                int endPos2 = startpos2 + searchWord.length();
                int startpos3 = fullText3.toLowerCase(Locale.US).indexOf(searchWord.toLowerCase(Locale.US));
                int endPos3 = startpos3 + searchWord.length();
                if (startPos != -1) {
                    ColorStateList blueColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.BLUE});
                  TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor, null);
                    spannable.setSpan(highlightSpan, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                if (startpos2 != -1) {

                    ColorStateList blueColor2 = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.RED});
                    TextAppearanceSpan highlightSpan2 = new TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor2, null);
                    spannable1.setSpan(highlightSpan2, startpos2, endPos2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                if (startpos3 != -1) {
                    ColorStateList blueColor3 = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.GREEN});
                    TextAppearanceSpan highlightSpan3 = new TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor3, null);
                    spannable3.setSpan(highlightSpan3, startpos3, endPos3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            holder.txtDesc.setText(spannable);
            holder.txtTitle.setText(spannable1);
            holder.pubname.setText(spannable3);
        }
        else {
            holder.txtDesc.setText(fullText);
            holder.txtTitle.setText(fullText2);
            holder.pubname.setText(fullText3);
        }





        return convertView;
    }
    public Filter getFilter() {
        if(filter == null)
        {
            filter=new CustomFilter();
        }

        return filter;}


      class CustomFilter extends Filter {
          @Override
          protected FilterResults performFiltering(CharSequence constraint) {
              filteredData = rowItems;
                searchString = constraint.toString().toLowerCase();
              FilterResults results = new FilterResults();

              if (constraint != null && constraint.length() > 0) {
                  //CONSTARINT TO UPPER
                  constraint = constraint.toString().toLowerCase();

                  ArrayList<RowItem> filters = new ArrayList<>();

                  for (int i = 0; i < filteredData.size(); i++) {
                      if (filteredData.get(i).getTitle().toLowerCase().contains(constraint)) {
                          RowItem p = new RowItem(filteredData.get(i).getImageId(),filteredData.get(i).getTitle(), filteredData.get(i).getDesc(), filteredData.get(i).getPub());
                          filters.add(p);
                      }
                      else if (filteredData.get(i).getDesc().toLowerCase().contains(constraint)) {
                          RowItem p = new RowItem(filteredData.get(i).getImageId(),filteredData.get(i).getTitle(), filteredData.get(i).getDesc(), filteredData.get(i).getPub());
                          filters.add(p);
                      }
                      else if (filteredData.get(i).getPub().toLowerCase().contains(constraint)) {
                          RowItem p = new RowItem(filteredData.get(i).getImageId(),filteredData.get(i).getTitle(), filteredData.get(i).getDesc(), filteredData.get(i).getPub());
                          filters.add(p);
                      }
                  }

                  results.count = filters.size();
                  results.values = filters;
                  t=results.count;

              } else {
                  results.count = filteredData.size();

                  results.values = filteredData;
                  t=results.count;

              }


              return results;
          }
          @SuppressWarnings("unchecked")
          @Override
          protected void publishResults(CharSequence constraint, FilterResults results) {
              // TODO Auto-generated method stub

              if (results.count == 0) {
                  notifyDataSetInvalidated();
              } else {
                  filteredData = (ArrayList<RowItem>) results.values;
                  notifyDataSetChanged();
              }
          }

      }


}