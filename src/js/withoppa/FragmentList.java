package js.withoppa;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentList extends Fragment{
	
	View view;
	MyAdapter adapter;
	ArrayList<MyData> arrData;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_list, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//리스트에 보여줄 데이터를 세팅한다.
        setData();
        
        //어댑터 생성
        adapter = new MyAdapter(getActivity(), arrData);
        
        //리스트뷰에 어댑터 연결
        ListView list = (ListView)view.findViewById(R.id.list);
        list.setAdapter(adapter);
	}
	
	@SuppressLint("SimpleDateFormat")
	private void setData(){
		arrData = new ArrayList<MyData>();
		JSONArray contentsJsArray=null;
		GlobalVar.socket.emit("askContents",GlobalVar.socket.getHeader("midx"));
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			contentsJsArray=GlobalVar.userLoginTask.ioCallBackImpl.contentsJsArray;
			if(contentsJsArray!=null) break;
		}
		for(int i=0;i<contentsJsArray.length();i++){
			try {
				JSONObject tmpJsOb = contentsJsArray.getJSONObject(i);
				MyData tmpContent=new MyData();
				tmpContent.setName(tmpJsOb.getString("mgName"));
				String tmpDateString=tmpJsOb.getString("regdate");
				DateFormat tmpFormatB=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				DateFormat tmpFormatA=new SimpleDateFormat("yyyy'년'MM'월'dd'일' a hh'시'mm'분'");
				Date tmpDateDate=tmpFormatB.parse(tmpDateString);
				String tmpDate=tmpFormatA.format(tmpDateDate);
				tmpContent.setDate(tmpDate);
				tmpContent.setComment(tmpJsOb.getString("textData"));
				//String fileName=tmpJsOb.getString("filename");
				//int fileSize=Integer.parseInt(tmpJsOb.getString("filesize"));
				String fileData=tmpJsOb.getString("file");
				byte[] fileBytes=fileData.getBytes();
				Bitmap tmpImageBitmap=BitmapFactory.decodeByteArray(fileBytes,0,fileBytes.length);
				tmpContent.setImage(tmpImageBitmap);
				String mgFileData=tmpJsOb.getString("mgImage");
				byte[] mgFileBytes=mgFileData.getBytes();
				Bitmap tmpMgImageBitmap=BitmapFactory.decodeByteArray(mgFileBytes,0,mgFileBytes.length);
				tmpContent.setMgImage(tmpMgImageBitmap);
				tmpContent.setMgIdx(tmpJsOb.getString("mgIdx"));
				arrData.add(tmpContent);
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
}
