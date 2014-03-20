package js.withoppa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIOException;

public class IOCallBackImpl implements IOCallback {
	
	boolean logedIn=false;
	boolean tried=false;
	JSONArray contentsJsArray=null;

	@Override
	public void on(String event, IOAcknowledge ack, Object... args) {
		if("logedIn".equals(event)){
			Log.e("logedIn 콜백받음", "logedIn 콜백받음");
			JSONObject jsOb = null;
			tried=true;
			try{
				jsOb=(JSONObject) args[0];
				Log.e("모왔냐", jsOb.toString());
				logedIn=jsOb.getBoolean("logedIn");
				if(logedIn){
					GlobalVar.mgIdx=jsOb.getString("midx");
					GlobalVar.mgName=jsOb.getString("mname");
					//효석이가 가입처리끝내면 마져 ㄱㄱ
					String fileData=jsOb.getString("먼가이미지명");
					byte[] fileBytes=fileData.getBytes();
					Bitmap tmpImageBitmap=BitmapFactory.decodeByteArray(fileBytes,0,fileBytes.length);
					GlobalVar.mgImage=tmpImageBitmap;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else if("getContents".equals(event)){
			Log.e("getContents 콜백받음", "getContents 콜백받음");
			JSONObject jsOb=null;
			try{
				jsOb=(JSONObject) args[0];
				Log.e("모왔냐", jsOb.toString());
				//컨텐츠가 제이슨어레이일 가정으로
				contentsJsArray=jsOb.getJSONArray("contents");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void onConnect() {
	}
	@Override
	public void onDisconnect() {
	}
	@Override
	public void onError(SocketIOException arg0) {
		Log.e(arg0.toString(),arg0.toString());
	}
	@Override
	public void onMessage(String arg0, IOAcknowledge arg1) {
	}
	@Override
	public void onMessage(JSONObject arg0, IOAcknowledge arg1) {
	}

}
