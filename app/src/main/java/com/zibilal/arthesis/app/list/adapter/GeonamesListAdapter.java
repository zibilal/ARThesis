package com.zibilal.arthesis.app.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zibilal.arthesis.app.R;
import com.zibilal.arthesis.app.location.Point;
import com.zibilal.arthesis.app.model.Geoname;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmuhamm on 4/15/14.
 */
public class GeonamesListAdapter extends BaseAdapter {

    private List<Geoname> mGeonames;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private int mLayoutStubs;
    private int[] mLayoutStubsIds;

    public GeonamesListAdapter(Context context){
        this(context, null);
    }

    public GeonamesListAdapter(Context context, List<Geoname> geonames) {
        mContext=context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(geonames != null)
            mGeonames = geonames;
        else
            mGeonames = new ArrayList<Geoname>();

        mLayoutStubs= R.layout.item_geoname;
        mLayoutStubsIds=new int[3];
        mLayoutStubsIds[0]=R.id.title_view;
        mLayoutStubsIds[1]=R.id.distance_view;
        mLayoutStubsIds[2]=R.id.distancevector_view;
    }

    public void add(Geoname data) {
        mGeonames.add(data);
        notifyDataSetChanged();
    }

    public void add(List<Geoname> geonames) {
        mGeonames.addAll(geonames);
        notifyDataSetChanged();
    }

    public void clear() {
        mGeonames.clear();
    }

    @Override
    public int getCount() {
        return mGeonames.size();
    }

    @Override
    public Object getItem(int position) {
        return mGeonames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            View view = mLayoutInflater.inflate(mLayoutStubs, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.titleView=(TextView) view.findViewById(mLayoutStubsIds[0]);
            viewHolder.distanceView=(TextView) view.findViewById(mLayoutStubsIds[1]);
            viewHolder.distanceVectorView=(TextView) view.findViewById(mLayoutStubsIds[2]);
            convertView=view;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Geoname geoname = mGeonames.get(position);
        viewHolder.titleView.setText(geoname.getTitle());
        viewHolder.distanceView.setText(geoname.getDistance());
        Point point = geoname.getProjection();
        if(point != null) {
            String str = String.format("x=%.2f , y=%.2f , z=%.2f", point.getX(), point.getY(), point.getZ());
            viewHolder.distanceVectorView.setText(str);
        }

        return convertView;
    }


    static class ViewHolder {
        TextView titleView;
        TextView distanceView;
        TextView distanceVectorView;
    }
}
