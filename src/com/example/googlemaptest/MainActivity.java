package com.example.googlemaptest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import googlemap.WGoogleMap;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;


/**
 * @author Wu
 *
 * Google Map Test
 */
public class MainActivity extends FragmentActivity implements OnMapReadyCallback,
			ConnectionCallbacks, OnConnectionFailedListener{

	Context mContext;
	Location mLastLocation;
	GoogleMap googleMap;
	GoogleApiClient mGoogleApiClient = null;
	WGoogleMap wgoogleMap;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        
        MapFragment mapFragment = (MapFragment) getFragmentManager()
        	    .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);  
        googleMap = mapFragment.getMap();
        wgoogleMap = new WGoogleMap(googleMap);
        
      
        
    	// Create an instance of GoogleAPIClient.
    	if (mGoogleApiClient == null) {
    	    mGoogleApiClient = new GoogleApiClient.Builder(this)
    	        .addConnectionCallbacks(this)
    	        .addOnConnectionFailedListener(this)
    	        .addApi(LocationServices.API)
    	        .build();
    	} 
    }
    
    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	mGoogleApiClient.connect();
    	Log.d("LOACTION_GOOGLE", "���ӡ�������");
    }
    
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	mGoogleApiClient.disconnect();
    	Log.d("LOACTION_GOOGLE", "�Ͽ�������");
    }
    
    

	@Override
	public void onMapReady(GoogleMap map) {
		// TODO Auto-generated method stub
		map.getUiSettings().setZoomControlsEnabled(true);//����ͼ������ʾ���ű����ؼ�
		map.getUiSettings().setMapToolbarEnabled(true);//��ͼ������
		map.setMyLocationEnabled(false);//�ҵ�λ�ð�ť
		map.setMapType(WGoogleMap.MAP_TYPE_NORMAL);
		map.setBuildingsEnabled(true);
		map.setOnMapClickListener(onMapClickListener);//��Google��ͼ����¼�
	}

	
	/**
	 * Google��ͼ����¼�
	 */
	private OnMapClickListener onMapClickListener =  new OnMapClickListener() {
		
		@Override
		public void onMapClick(LatLng arg0) {
			// TODO Auto-generated method stub
			Toast.makeText(mContext, "arg0:" + arg0.latitude, Toast.LENGTH_LONG).show();
			LatLng latlng = new LatLng(arg0.latitude, arg0.longitude);
			googleMap.addMarker(new MarkerOptions().position(latlng).title("�ƺ�֮ˮ���������ƺ�֮ˮ�������ƺ�֮ˮ������"));//��ӱ�־
			
		}
	};
	
	
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		Log.d("LOACTION_GOOGLE", "����ʧ��!!!");
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		Log.d("LOACTION_GOOGLE", "���ӳɹ�������");
		LatLng start = new LatLng(Double.valueOf(22.58), Double.valueOf(113.92));
		LatLng end   = new LatLng(Double.valueOf(22.57), Double.valueOf(113.97));
		wgoogleMap.drawLine(getPoints(), Color.RED, 5);
		wgoogleMap.drawLine(start, end, Color.BLUE, 8); 
		wgoogleMap.setMyLocationListener(mGoogleApiClient, 20, listener);
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
	Marker phoneMark;
	private void drawPhoneLocation(double latitude, double longitude){
		// ����е�ǰλ�ã�����ɾ��
		if (phoneMark != null) {
			phoneMark.remove();
		}
		LatLng phoneLatLng = new LatLng(latitude, longitude);
		MarkerOptions options = wgoogleMap.setMarkOptions(phoneLatLng, R.drawable.person);
		phoneMark = (Marker) (wgoogleMap.addMarker(options));
		wgoogleMap.animateCamera(phoneLatLng, 15, 30, 30);
	}

	/**
	 * @return ��ȡ����
	 */
	private List<LatLng> getPoints(){
		String p = getAsset("points.txt");
		List<LatLng> points = new ArrayList<LatLng>();
		points.clear();
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(p);
			for(int i =0;i<jsonArray.length();i++){
				JSONObject obj = new JSONObject(jsonArray.get(i).toString());
				LatLng latlng = new LatLng(Double.valueOf(obj.getString("lat")),Double.valueOf(obj.getString("lon")));
				points.add(latlng);
			}	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return points;	
	}
	

	/**
	 * @param fileName
	 * @return
	 */
	private String getAsset(String fileName) {
        AssetManager am = getResources().getAssets();
        InputStream is = null;
        try {
            is = am.open(fileName, AssetManager.ACCESS_BUFFER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Scanner(is).useDelimiter("\\Z").next();
    }
	
	LocationListener listener = new LocationListener() {
		
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			if (location == null || googleMap == null){
				return;// map view ���ٺ��ڴ����½��յ�λ��
			}
				
			drawPhoneLocation(location.getLatitude(), location.getLongitude());
			
			Log.d("LOACTION_GOOGLE","�ҵ�λ��" +"γ��:" + location.getLatitude() 
					+ "   " + "����:" + location.getLongitude());
		}
	};
	
	
}
