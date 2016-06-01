//package googlemap;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response.ErrorListener;
//import com.android.volley.Response.Listener;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.google.android.gms.maps.model.LatLng;
//
//import android.content.Context;
//import android.util.Log;
//
//public class WGeocoder {
//	
//	public static RequestQueue mQueue;
//	
//	public WGeocoder(Context context){
//		init(context);
//	}
//	
//	/**
//	 * @param context
//	 */
//	public static void init(Context context) {
//		if (mQueue == null) {
//			mQueue = Volley.newRequestQueue(context.getApplicationContext());
//		}
//	}
//	
//	/**
//	 * @param latlng
//	 * @param onSuccess
//	 * @param onError
//	 */
//	public void request(LatLng latlng, Listener<String> onSuccess,ErrorListener onError) {
//		if(mQueue==null){
//			Log.i("WGeocoder", "RequestQueue还木有初始化，请先调用init方法");
//			return ;
//		}
//		@SuppressWarnings("rawtypes")
//		Request request = new StringRequest(getUrl(latlng), onSuccess, onError);
//		request.setShouldCache(false);
//		mQueue.add(request);
//	}
//	
//	/**
//	 * @param latlng
//	 * @return
//	 */
//	public String getUrl(LatLng latlng){
//		StringBuilder url = new StringBuilder();
//		url.append("http://maps.googleapis.com/maps/api/geocode/json?latlng=");
//		url.append(latlng.latitude).append(",").append(latlng.longitude);
//		url.append("&sensor=false");
//		return url.toString();
//	}
//	
////	httpGet.addHeader("Accept-Language", "en-us");//中文 ： zh-CN
//	
//	/**
//	 * @param response
//	 * @return
//	 */
//	public String getAddress(String response){
//		String address = "";
//		try {
//			JSONObject jsonObject = new JSONObject(response);
//			JSONArray resultArray = jsonObject.getJSONArray("results");
//			if (resultArray.length() > 0) {
//				JSONObject subObject;
//			subObject = resultArray.getJSONObject(0);
//		    address = subObject.getString("formatted_address");
//			}
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return address;
//	}	
//}
