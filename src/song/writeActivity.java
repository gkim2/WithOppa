package song;


import js.withoppa.MainActivity;
import js.withoppa.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class writeActivity extends Activity{
	ActionBar actionBar;
	TextView resId;
	EditText mText;
		
		@SuppressLint("NewApi")
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_write);
			
			/*-------------------액션바를 생성한다---------------*/
			actionBar = getActionBar();
			actionBar.setTitle("  게시물 작성");
			actionBar.setHomeButtonEnabled(true);
		//	actionBar.setIcon(R.drawable.back_icon);
		//	actionBar.setLogo(R.drawable.back_icon);
			
			actionBar.setDisplayOptions(
		//			actionBar.DISPLAY_HOME_AS_UP |
					actionBar.DISPLAY_SHOW_TITLE |
					actionBar.DISPLAY_SHOW_HOME |
					actionBar.DISPLAY_USE_LOGO
			
					);
			
			
//			actionBar.show();		//defaut show
			/*---------------------------------------------*/
			
			resId = (TextView)findViewById(R.id.resId);
			mText = (EditText)findViewById(R.id.mText);
			
		}
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			String tmp;
			switch(item.getItemId()){
			
			case android.R.id.home	:
				
				Intent intent = new Intent(writeActivity.this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				break;
				
			case	R.id.register	: 
				tmp = mText.getText().toString();
				/*------------Debug----------*/
				Log.e("song",tmp);
				Toast.makeText(this, tmp, 0).show();
				/*--------------------------*/
				/*------------ Data 를 가지고 가면 됨 --------*/
				
				
				/*------------------------------------*/
				
				break;
				
			}
			return super.onOptionsItemSelected(item);
		}
		
	
		
		
}
