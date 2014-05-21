package org.das.coolweather;

import java.util.Locale;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.survivingwithandroid.weatherapp.JSONWeatherParser;
//import com.survivingwithandroid.weatherapp.WeatherHttpClient;


import org.das.coolweather.Location;
import org.das.coolweather.Weather;
import org.json.JSONException;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_search).toUpperCase(l);
			case 1:
				return getString(R.string.title_map).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";
		private GoogleMap theMap;
		private EditText edtSearchTerm;
		private Button btnSearchTerm;
		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = null;
			
			
			if(getArguments().getInt(
					ARG_SECTION_NUMBER) == 1) {
				
				rootView = inflater.inflate(R.layout.activity_search, container,
					false);
				edtSearchTerm= (EditText) rootView.findViewById(R.id.edtSearchTerm);
				btnSearchTerm = (Button) rootView.findViewById(R.id.btnSearch);
				
				btnSearchTerm.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						Intent intentDetails = new Intent(getActivity(), DetailsActivity.class);
						intentDetails.putExtra("City", edtSearchTerm.getText().toString());
						startActivity(intentDetails);
						
					}
				});
				
			} else {
				rootView = inflater.inflate(R.layout.map, container,
						false);
				
				theMap = ((SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.elmapa)).getMap();
				theMap.setMyLocationEnabled(true);
				
				theMap.setOnMapClickListener(new OnMapClickListener() {
					
					@Override
					public void onMapClick(LatLng point) {
						theMap.addMarker(new MarkerOptions()
						.position(point)
						.draggable(true)
						.title("Descripción del marcador"));
						
					}
				});
				
//				theMap.setOnMapLongClickListener(new OnMapLongClickListener() {
//					
//					@Override
//					public void onMapLongClick(LatLng point) {
//						theMap.addMarker(new MarkerOptions()
//						.position(point)
//						.title("Descripción del marcador"));
//						
//					}
//				});
				theMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
					
					@Override
					public void onInfoWindowClick(Marker marker) {
						Intent i = new Intent(getActivity().getApplicationContext(), 
								DetailsActivity.class);
						
					}
				});
				theMap.setOnMarkerClickListener(new OnMarkerClickListener() {
					
					@Override
					public boolean onMarkerClick(Marker marker) {
						marker.setTitle("hola soy el " + marker.getId());
						marker.showInfoWindow();
						return false;
					}
				});
				
				
				theMap.setOnMarkerDragListener(new OnMarkerDragListener() {
					
					@Override
					public void onMarkerDragStart(Marker marker) {
						marker.remove();	
					}

					@Override
					public void onMarkerDrag(Marker marker) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onMarkerDragEnd(Marker marker) {
						// TODO Auto-generated method stub
					}
					
				});
			}
			
			return rootView;
		}
	}
	
	

}
