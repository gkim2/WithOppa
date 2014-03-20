package song;


import io.socket.SocketIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import js.withoppa.GlobalVar;
import js.withoppa.IOCallBackImpl;
import js.withoppa.MainActivity;
import js.withoppa.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class writeActivity extends Activity{
	ActionBar actionBar;
	TextView resId;
	EditText mText;
	ImageView mPicture;
	Uri uri;
		
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
			mPicture = (ImageView)findViewById(R.id.takePicture);
			
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
				Toast.makeText(this, mPicture.getDrawable().toString(), 0).show();
				/*--------------------------*/
				/*------------ Data 를 가지고 가면 됨 --------*/
				
				
				/*------------------------------------*/
				
				break;
				
			}
			return super.onOptionsItemSelected(item);
		}
		
		private static final  int TAKE_CAMERA = 1;
		private static final  int TAKE_PICTURE= 0;
		private static final  int CROP_FROM_CAMERA = 2;
		//카메라 이미지 버튼을 누른다.
		public void picture(View v){
		
			Intent intent = new Intent( Intent.ACTION_PICK ) ;	
			intent.setType( android.provider.MediaStore.Images.Media.CONTENT_TYPE ) ;
			intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult( intent, TAKE_PICTURE ) ;
		}
		
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
	
			switch(requestCode){
			case CROP_FROM_CAMERA :
		      {
		        // 크롭이 된 이후의 이미지를 넘겨 받습니다.
		        // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
		        // 임시 파일을 삭제합니다.
		        final Bundle extras = data.getExtras();
		  
		        if(extras != null)
		        {
		          Bitmap photo = extras.getParcelable("data");
		          mPicture.setImageBitmap(photo);
		        }
		  
		        // 임시 파일 삭제
		        File f = new File(uri.getPath());
		        if(f.exists())
		        {
		          f.delete();
		        }
		  
		        break;
		      }
			case TAKE_PICTURE	:
				
				uri = data.getData();
				String path =  uri.getPath();
				
				BitmapFactory.Options option = new BitmapFactory.Options();
                option.inSampleSize = 2;
                Bitmap bm = null;
				try {
					bm = Images.Media.getBitmap(getContentResolver(), uri);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
             //   Bitmap bm = BitmapFactory.decodeFile(path);
				mPicture.setImageBitmap(bm);
				
				/*Intent intent = new Intent("com.android.camera.action.CROP");
		        intent.setDataAndType(uri, "image/*");
				mPicture.setImageURI(uri);
				intent.putExtra("outputX", 180);
		        intent.putExtra("outputY", 180);
		        intent.putExtra("aspectX", 1);
		        intent.putExtra("aspectY", 1);
		        intent.putExtra("scale", true);
		        intent.putExtra("return-data", true);
		        startActivityForResult(intent, CROP_FROM_CAMERA);*/
		  
		        break;
				
				/*mPicture.setImageBitmap((Bitmap)data.getParcelableExtra("data"	));
				mPicture.setScaleType(ImageView.ScaleType.FIT_XY);*/
				/*String path =  uri.getPath();
				Drawable dw = setImage.resize(path);
				
				mPicture.setBackground(dw);*/
				
				
				
			case TAKE_CAMERA	:
				break;
			}
			
		}
		
		
		/*public class upDataTask extends AsyncTask<String, Integer, Boolean>{
			IOCallBackImpl ioCallBackImpl;
			public upDataTask() {			
				// TODO Auto-generated constructor stub
				if(GlobalVar.socket==null){
					Log.e("song" , "업로드 태스크 생성");
					try {
						String host = "http://192.168.0.90";
						GlobalVar.socket = new SocketIO(host);
						ioCallBackImpl=new IOCallBackImpl();
						GlobalVar.socket.connect(ioCallBackImpl);
					
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
			}
			
			
			@Override
			protected Boolean doInBackground(String... params) {
				JSONObject data = new JSONObject();
				try {
					
				} 
				return null;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
			}
			
		}*/
		
	
		
		
}
