package org.das.coolweather.adapters;

import java.util.ArrayList;

import org.das.coolweather.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DayListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<DayInfo> daysInfo;
	
	public DayListAdapter(Context context, ArrayList<DayInfo> daysInfo) {
		this.context = context;
        this.daysInfo = daysInfo;
	}
	
	@Override
	public int getCount() {
		return daysInfo.size();
	}

	@Override
	public Object getItem(int position) {
		return daysInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = (View) inflater.inflate(
                    R.layout.daylayout, null);
        }

        ImageView image = (ImageView)convertView.findViewById(R.id.imgDay);
        TextView tempMin = (TextView)convertView.findViewById(R.id.txtMinTemp);
        TextView day = (TextView)convertView.findViewById(R.id.txtDay);
        TextView tempMax = (TextView)convertView.findViewById(R.id.txtMaxTemp);
        TextView wind = (TextView)convertView.findViewById(R.id.txtWindDay);
        TextView rain = (TextView)convertView.findViewById(R.id.txtRainDay);
        TextView pressure = (TextView)convertView.findViewById(R.id.txtPressureDay);
        TextView humidity = (TextView)convertView.findViewById(R.id.txtHumidityDay);
        
        DayInfo aDay = daysInfo.get(position);
        
        day.setText(aDay.getDay());
        tempMin.setText(aDay.getTempMin().toString() + " º");
        tempMin.setTextColor(Color.BLUE);
        tempMin.setTypeface(null,Typeface.BOLD);
        tempMax.setText(aDay.getTempMax().toString() + " º");
        tempMax.setTextColor(Color.RED);
        tempMax.setTypeface(null,Typeface.BOLD);
        wind.setText(aDay.getWind() + " m/s");
        rain.setText(aDay.getRain().toString() + " mm");
        pressure.setText(aDay.getPressure().toString() + " hPa");
        humidity.setText(aDay.getHumidity().toString() + " %");
        image.setImageDrawable(context.getResources().getDrawable(context.getResources().getIdentifier("drawable/a" + aDay.getImageSrc(), "drawable", context.getPackageName())));
//        image.setBackgroundDrawable(R.drawable.d);
        //image.setImageResource(R.drawable.d);
        return convertView;
	}

}
