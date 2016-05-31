package googlemap;

import java.util.List;

import com.example.googlemaptest.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * @author Wu
 * @date  2016-05-27
 */
public class WGoogleMap {
	
	public GoogleMap googleMap;
	public CameraPosition cameraPosition;
	public static final int MAP_TYPE_NORMAL    = 1;//普通街道地图
	public static final int MAP_TYPE_SATELLITE = 2;//卫星视图
	public static final int MAP_TYPE_TERRAIN   = 3;//地势地图
	public static final int MAP_TYPE_HYBRID    = 4;//卫星和路网的混合视图
	
	public WGoogleMap(GoogleMap map){
		super();
		this.googleMap = map;
		googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	}
	
	/**
	 * @param enable 是否显示我的位置按钮
	 */
	public void setMyLoaction(boolean enable){
		googleMap.setMyLocationEnabled(enable);
	}

	/**
	 * @param enable 是否在视图上面显示缩放比例控件
	 */
	public void setZoomControlsEnabled(boolean enable){
		googleMap.getUiSettings().setZoomControlsEnabled(enable);
	}
	
	/**
	 * @param enable  当用户查看室内地图时候，层级选择器会出现在右侧边缘
	 */
	public void setIndoorLevelPickerEnabled(boolean enable){
		googleMap.getUiSettings().setIndoorLevelPickerEnabled(enable);
	}
	
	/**
	 * @param enable  地图工具栏（会跳转到Google导航软件或者地图软件）
	 */
	public void setMapToolbarEnabled(boolean enable){
		googleMap.getUiSettings().setMapToolbarEnabled(enable);
	}
	
	/**
	 * @param enable 倾斜手势 (默认开启)
	 */
	public void setTiltGesturesEnabled(boolean enable){
		googleMap.getUiSettings().setTiltGesturesEnabled(enable);
	}
	
	/**
	 * @param enable 旋转手势 (默认开启)
	 */
	public void setRotateGesturesEnabled(boolean enable){
		googleMap.getUiSettings().setRotateGesturesEnabled(enable);
	}
	
	/**
	 * @param enable 缩放手势 (默认开启)
	 */
	public void setZoomGesturesEnabled(boolean enable){
		googleMap.getUiSettings().setZoomGesturesEnabled(enable);
	}
	
	/**
	 * @param enable 平移手势 (默认开启)
	 */
	public void setScrollGesturesEnabled(boolean enable){
		googleMap.getUiSettings().setScrollGesturesEnabled(true);
	}
	
	/**
	 * @param enable 在有些城市放大会显示3D视图
	 */
	public void setBuildingsEnabled(boolean enable){
		googleMap.setBuildingsEnabled(enable);
	}
	
	
	/**
	 * @param mapType 1:普通街道视图 2:卫星视图 3:地势地图 4:卫星和街道混合视图（默认是normal）
	 */
	public void setMapType(int mapType){
		switch (mapType) {
		case 1:
			googleMap.setMapType(MAP_TYPE_NORMAL);
			break;
		case 2:
			googleMap.setMapType(MAP_TYPE_SATELLITE);
			break;
		case 3:
			googleMap.setMapType(MAP_TYPE_TERRAIN);
			break;
		case 4:
			googleMap.setMapType(MAP_TYPE_HYBRID);
			break;
		default:
			googleMap.setMapType(MAP_TYPE_NORMAL);
			break;
		}
	}
	
	
	/**
	 * @param target 设置要移动镜头的目标（移动这个点会在地图中心）
	 * @param zoom 设置缩放级别
	 * @param bearing 设置相机的方向
	 * @param tilt 镜头的倾斜度
	 */
	public void animateCamera(LatLng target,int zoom,int bearing,int tilt){
		CameraPosition cameraPosition = new CameraPosition.Builder()
	    .target(target)      // Sets the center of the map to Mountain View
	    .zoom(13)            // Sets the zoom
	    .bearing(bearing)    // Sets the orientation of the camera to east
	    .tilt(tilt)          // Sets the tilt of the camera to 30 degrees
	    .build();            // Creates a CameraPosition from the builder
		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}
	
	
	/**
	 * @param latlng 标签的位置
	 * @param markName 标签显示的信息
	 * @return Marker
	 */
	public Marker addMarker(LatLng latlng,String markName){
		return googleMap.addMarker(new MarkerOptions().position(latlng).title(markName));
	}
	
	/**
	 * @param latlng 把镜头移动到的点
	 */
	public void moveCamera(LatLng latlng){
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
	}
	
	
	
	/**
	 * @param latlng
	 * @param resId
	 * @return MarkerOptions
	 */
	public MarkerOptions setMarkOptions(LatLng latlng,int resId){
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.person);
		MarkerOptions markOption = new MarkerOptions().anchor(0.1f, 0.1f)
				.position(latlng).icon(bitmap);
		return markOption;
	}
	
	
	/**
	 * @param markOptions
	 * @return
	 */
	public Marker addMarker(MarkerOptions markOptions){
		return googleMap.addMarker(markOptions);
	}
	
	
	/**
	 * @param seconds 位置更新时间 (单位为：/s)
	 * @return LocationRequest
	 */
	public LocationRequest setLocationRequest(int seconds){
		LocationRequest REQUEST = LocationRequest.create()
	            .setInterval(1000 * seconds)         // 5 seconds
	            .setFastestInterval(16)    // 16ms = 60fps
	            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		return REQUEST;
	}
	
	/**
	 * @param points 经纬度点集合
	 * @param color  线颜色
	 * @param width  线宽度
	 */
	public void drawLine(List<LatLng> points,int color,float width){
		PolylineOptions options = new PolylineOptions(); 
		options.color(color);
		options.width(width);
		options.geodesic(true);
		options.addAll(points);
		googleMap.addPolyline(options);
	}
	
	/**
	 * @param start 开始经纬度点
	 * @param end   结束经纬度点
	 * @param color 线颜色 
	 * @param width 线宽度
	 */
	public void drawLine(LatLng start, LatLng end,int color,float width){
		googleMap.addPolyline((new PolylineOptions())
	            .add(start,end)
	            .width(width)
	            .color(color)
	            .geodesic(true));
	}
	
	/**
	 * 清除地图上面的东西
	 */
	public void clear(){
		googleMap.clear();
	}
	
	
	/**
	 * @param marker 移除marker
	 */
	public void removeMarker(Marker marker){
		marker.remove();
	}
	
	
	/**
	 * @param client GoogleApiClient
	 * @param seconds  定位时间间隔
	 * @param listener 定位监听
	 */
	public void setMyLocationListener(GoogleApiClient client,int seconds,LocationListener listener){
		LocationServices.FusedLocationApi.requestLocationUpdates(
				client,
				setLocationRequest(seconds),
				listener);  // LocationListener  
	}
	

}
