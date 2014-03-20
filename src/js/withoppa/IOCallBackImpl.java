package js.withoppa;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

public class IOCallBackImpl implements IOCallback {
	
	boolean logedIn=false;
	SocketIO socket=LoginActivity.socket;

	@Override
	public void on(String event, IOAcknowledge ack, Object... args) {
		if("logedIn".equals(event)){
			JSONObject jsOb=(JSONObject) args[0];
			try {
				logedIn=jsOb.getBoolean("logedIn");
				if(logedIn){
					socket.addHeader("midx",args[1].toString());
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
