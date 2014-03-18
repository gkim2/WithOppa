package js.withoppa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

public class SignUpActivity extends Activity implements OnTouchListener{
	
	static final int PICK_FROM_ALBUM=1;
	ImageView thumImage;
	
	TextView signUpBtn;
	MotionEvent event = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		signUpBtn = (TextView)findViewById(R.id.signUp_btn);
	}
	
	public void signUp(View v){
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
	//t
	public void test(View view){
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
		startActivityForResult(intent, PICK_FROM_ALBUM) ;
	}
}
