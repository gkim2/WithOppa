package js.withoppa;


import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

import java.net.MalformedURLException;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SignUpActivity extends Activity implements OnTouchListener{
	
	static final int PICK_FROM_ALBUM=1;
	ImageView thumImage;
	EditText signUpName;
	EditText signUpEmail;
	EditText signUpPw1;
	EditText signUpPw2;
	TextView signUpBtn;
	MotionEvent event = null;
	
	private String sName;
	private String sEmail;
	private String sPassword;
	
	private UserSignupTask signUpTask = null;
	
	private SocketIO socket;
	private boolean signUp=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		
		thumImage = (ImageView)findViewById(R.id.thumImg);
		signUpName = (EditText)findViewById(R.id.UpName_input);
		signUpEmail = (EditText)findViewById(R.id.UpEmail_input);
		signUpPw1 = (EditText)findViewById(R.id.UpPw1_input);
		signUpPw2 = (EditText)findViewById(R.id.UpPw2_input);
		
		signUpBtn = (TextView)findViewById(R.id.signUp_btn);
		signUpBtn.setOnTouchListener(this);
	}
	
	
	//가입버튼 이미지변경
	@Override
	public boolean onTouch(View signUpBtn, MotionEvent ev) {
		if(ev.getAction()==MotionEvent.ACTION_DOWN){
			signUpBtn.setBackgroundResource(R.drawable.color_2);
		}else if(ev.getAction()==MotionEvent.ACTION_UP){
			signUpBtn.setBackgroundResource(R.drawable.color_1);
		}
		return false;
	}
	//썸네일 넣기
	public void thumPick(View view){
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
		startActivityForResult(intent, PICK_FROM_ALBUM) ;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK){
			Uri mImageUri = data.getData();
			thumImage.setImageURI(mImageUri);
		}
	}
	//가입처리
	public void signUp(View view){
		sName = signUpName.getText().toString();
		sEmail = signUpEmail.getText().toString();
		sPassword = signUpPw1.getText().toString();
		signUpTask = new UserSignupTask();
		signUpTask.execute((Void) null);
	}
	
	public class UserSignupTask extends AsyncTask<Void,Void ,Boolean>{
		
		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				String host = "http://192.168.0.175";
				socket = new SocketIO(host);
				socket.connect(new IOCallback() {
					@Override
					public void on(String event, IOAcknowledge ack, Object... args) {
						if("signUp".equals(event)){
							Log.e("signUp 콜백받음", "signUp 콜백받음");
							signUp=true;
				        }
					}
					@Override
					public void onConnect() {
						Log.e("Connect","Connect");
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
				});
				JSONObject data=new JSONObject();
				try {
					Log.e("emit","emit");
					data.put("name",sName);
					data.put("id",sEmail);
					data.put("pw",sPassword);
					socket.emit("join",data);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
			return signUp;
		}
		
	}
}
