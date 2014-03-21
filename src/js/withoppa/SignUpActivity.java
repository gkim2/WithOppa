package js.withoppa;


import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;


import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends Activity implements OnTouchListener,OnFocusChangeListener{
	
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
	private String sPassword_ch;

	String byteStr;
	
	private boolean nameCheck;
	private boolean emailCheck;
	private boolean pw1Check;
	private boolean pw2Check;
	private Boolean signUpEnd=false;
	private UserSignupTask signUpTask=null;
	
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
		signUpName.setOnFocusChangeListener(this);
		signUpEmail.setOnFocusChangeListener(this);
		signUpPw1.setOnFocusChangeListener(this);
		signUpPw2.setOnFocusChangeListener(this);
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
		startActivityForResult(intent, PICK_FROM_ALBUM);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK){
			Uri mImageUri = data.getData();
			thumImage.setImageURI(mImageUri);
			
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
	        Cursor cursor = getContentResolver().query(mImageUri,
	                filePathColumn, null, null, null);
	        cursor.moveToFirst();
	        
	        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	        String picturePath = cursor.getString(columnIndex);
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        Bitmap testImge = BitmapFactory.decodeFile(picturePath, options);
	        
	        options.inJustDecodeBounds = true;
	        options.inSampleSize = 4;
	        
	        ByteArrayOutputStream  byteArray = new ByteArrayOutputStream();
	        testImge.compress(CompressFormat.JPEG, 100, byteArray);
	        byte[] bArray = byteArray.toByteArray();
	        byte[] encodeBitByte = Base64.encodeBase64(bArray);
	        byteStr = new String(encodeBitByte);
		}
	}
	//가입처리
	public void signUp(View view){
		if(!signUpEnd){
			sName = signUpName.getText().toString();
			sEmail = signUpEmail.getText().toString();
			sPassword = signUpPw1.getText().toString();
			sPassword_ch = signUpPw2.getText().toString();
			pw2Check = sPassword.equals(sPassword_ch);
			if(!pw2Check){
				Toast toast = Toast.makeText(SignUpActivity.this,"비밀번호가 다릅니다.", 0);
				toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
				toast.show();
			}else if(nameCheck&emailCheck&pw1Check&pw2Check){
				signUpEnd = true;
				signUpTask = new UserSignupTask();
				signUpTask.execute((Void) null);
			}else{
				Toast toast = Toast.makeText(SignUpActivity.this, "가입실패", 0);
				toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
				toast.show();
			}
		}
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
					data.put("image",byteStr);
					socket.emit("join",data);
					while(true){
						try {
							Thread.sleep(2000);
							if(signUp){
								break;
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
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
		
		@Override
		protected void onPostExecute(Boolean result) {
			if(result){
				Log.e("종료하자","321");
				socket.disconnect();
				socket = null;
				SignUpActivity.this.finish();
			}
			super.onPostExecute(result);
		}
		
	}
	
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if(v.getId()==R.id.UpName_input){
			if(!hasFocus){
				sName = signUpName.getText().toString();
				nameCheck = SignUpCheck.nameCheck(sName);
				if(!nameCheck){
					Toast toast = Toast.makeText(SignUpActivity.this, "이름이 올바르지 않습니다.", 0);
					toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
					toast.show();
				}
			}
		}
		
		if(v.getId()==R.id.UpEmail_input){
			if(!hasFocus){
				if(!hasFocus){
					
					sEmail = signUpEmail.getText().toString();
					emailCheck = SignUpCheck.emailCheck(sEmail);
					if(!emailCheck){
						Toast toast = Toast.makeText(SignUpActivity.this, "메일이 올바르지 않습니다.", 0);
						toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
						toast.show();
					}
				}				
			}
		}
		
		if(v.getId()==R.id.UpPw1_input){
			if(!hasFocus){
				if(!hasFocus){
					sPassword = signUpPw1.getText().toString();
					pw1Check = SignUpCheck.passwordCheck(sPassword);
					if(!pw1Check){
						Toast toast = Toast.makeText(SignUpActivity.this, "비밀번호가 올바르지 않습니다.", 0);
						toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
						toast.show();
					}				
			}
		}
	}
		if(v.getId()==R.id.UpPw2_input){
			if(!hasFocus){
				if(!hasFocus){
					sPassword = signUpPw1.getText().toString();
					sPassword_ch = signUpPw2.getText().toString();
					pw2Check = sPassword.equals(sPassword_ch);
					if(!pw2Check){
						Toast toast = Toast.makeText(SignUpActivity.this,"비밀번호가 다릅니다.", 0);
						toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
						toast.show();
					}
				}				
			}
		}
	}
	
	
}
