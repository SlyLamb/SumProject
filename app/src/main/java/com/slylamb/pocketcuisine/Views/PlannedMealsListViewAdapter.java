package com.slylamb.pocketcuisine.Views;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.slylamb.pocketcuisine.R;

public class PlannedMealsListViewAdapter extends BaseAdapter {
    public PlannedMealsListViewAdapter(String date) {

    }
    class ViewHolder {
        String recipeTitle;
    }
    // How many items in ListView
    @Override
    public int getCount() {

    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

// BELOW NOT DONE YET
    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        ViewHolder vh;
        if (convertView == null) {
            // If it's not recycled, inflate it from xml
            convertView = getLayoutInflater().inflate(R.layout.ranking_list, viewGroup, false);
            // Create a new ViewHolder for it
            vh = new ViewHolder();
            vh.rankedPlayer = convertView.findViewById(R.id.rankingPlayer);
            // And set the tag to it
            convertView.setTag(vh);
        } else
            vh = (ViewHolder) convertView.getTag(); // Otherwise get the ViewHolder
        // Set its position
        vh.position = i;
        // Set text in TextView
        String text = (vh.position + 1) + ". " + competition.getPlayerAt(vh.position).getName()
                + " " + competition.getPlayerAt(vh.position).getScore();
        vh.rankedPlayer.setText(text);
        return convertView;
    }


}

/*
public class RankingAdapter extends BaseAdapter {
        class ViewHolder {
            int position;
            TextView rankedPlayer;
        }

        // How many items in the GridView
        @Override
        public int getCount() {
            return competition.totalPlayers();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            ViewHolder vh;
            if (convertView == null) {
                // If it's not recycled, inflate it from xml
                convertView = getLayoutInflater().inflate(R.layout.ranking_list, viewGroup, false);
                // Create a new ViewHolder for it
                vh = new ViewHolder();
                vh.rankedPlayer = convertView.findViewById(R.id.rankingPlayer);
                // And set the tag to it
                convertView.setTag(vh);
            } else
                vh = (ViewHolder) convertView.getTag(); // Otherwise get the ViewHolder
            // Set its position
            vh.position = i;
            // Set text in TextView
            String text = (vh.position + 1) + ". " + competition.getPlayerAt(vh.position).getName()
                    + " " + competition.getPlayerAt(vh.position).getScore();
            vh.rankedPlayer.setText(text);
            return convertView;
        }
    }
 */