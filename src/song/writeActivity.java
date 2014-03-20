package song;


import io.socket.SocketIO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import js.withoppa.GlobalVar;
import js.withoppa.IOCallBackImpl;
import js.withoppa.MainActivity;
import js.withoppa.MyData;
import js.withoppa.R;
import js.withoppa.LoginActivity.UserLoginTask;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
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
	Bitmap resizedImage;
	boolean setPicture = false;;
		
		@SuppressLint("NewApi")
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_write);
			Log.e("song"," 글쓰기 액티비티 생성");
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
			
			if(setPicture){
				
				mText.setHint(" 이 사진에 대하여 할 말이 있습니까?");
			}else{
				mText.setHint(" 하고 싶은 말이 있으신가요");
			}
			/*---------------------인텐트 데이터 꺼내옴 -----------------*/
			Intent intent = getIntent();
			
			boolean intentToken = intent.getExtras().getBoolean("approach");
			if(intentToken){
				
			Toast.makeText(this, "아아아", 0).show();
			} else{
				picture(mPicture);
			}
			
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
				JSONObject data = new JSONObject();
				/*비트맵 데이터 배열로 변환 */
				byte [] bitmapArray = bitmapToByteArray(resizedImage);
				try {
					data.put("name", GlobalVar.mgName);					
				//	data.put("comment", value)
					data.put("data", tmp);
					data.put("image", bitmapArray);
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					Toast.makeText(this, data.get("image").toString(),0).show();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*GlobalVar.upDataTask=new UpDataTask();
				GlobalVar.upDataTask.execute(data);*/
				
				/*------------------------------------*/
				
				break;
				
			}
			return super.onOptionsItemSelected(item);
		}
		
		public static final  int TAKE_CAMERA = 1;
		private static final  int TAKE_PICTURE= 0;
		private static final  int CROP_FROM_CAMERA = 2;
		public final static int DIRECT_PICTURE = 3;
		
		
		//카메라 이미지 버튼을 누른다.
		public void picture(View v){
			String temp = v.toString();
			Toast.makeText(this, temp, 0).show();
			Intent intent = new Intent( Intent.ACTION_PICK ) ;	
			intent.setType( android.provider.MediaStore.Images.Media.CONTENT_TYPE ) ;
			intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult( intent, TAKE_PICTURE ) ;
			
		}
		

		//받는 사람 아이콘 누를 시
		public void toUsers(View v){
			Toast.makeText(this, "현재는 더미아이콘임", 0).show();
		}
		
		public byte[] bitmapToByteArray(Bitmap bitmap){
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 100, baos);
			byte [] bArray = baos.toByteArray();
			return bArray;
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
				if(resultCode == Activity.RESULT_OK){					uri = data.getData();
						String path =  uri.getPath();
						
						BitmapFactory.Options option = new BitmapFactory.Options();
		                option.inSampleSize = 4;
		                Bitmap bm = null;
						try {
							bm = Images.Media.getBitmap(getContentResolver(), uri);
							resizedImage = Bitmap.createScaledBitmap(bm, 180, 180, true);
							Log.e("song","비트맵 이미지를 세팅합니다.");
							setPicture =true;
							mPicture.setImageBitmap(resizedImage);
							
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				
				}
           
		  
		        break;
				
			case 3	:
				Toast.makeText(this, "외부에서 접근", 0).show();
				Intent intent = new Intent(this , writeActivity.class);
				
				break;
				
			case TAKE_CAMERA	:
				break;
			}
			
		}
		
		
		public class UpDataTask extends AsyncTask<JSONObject, Integer, Boolean>{
			IOCallBackImpl ioCallBackImpl;
			public UpDataTask() {			
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
			protected Boolean doInBackground(JSONObject... params) {
				Log.e("song","백그라운드 쓰레드 동작");
				
				
				return null;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
			}
			
		}
		
	
		
		
}
