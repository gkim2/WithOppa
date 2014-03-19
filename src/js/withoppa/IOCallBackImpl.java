package js.withoppa;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIOException;

public class IOCallBackImpl implements IOCallback {
	
	boolean logedIn=false;
	boolean tried=false;
	
	boolean signUp=false;

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
					GlobalVar.socket.addHeader("midx",jsOb.getString("MIDX"));
				}
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
