package js.withoppa;

import java.util.ArrayList;

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
	
	private void setData(){
	     arrData = new ArrayList<MyData>();
	     
	     arrData.add(new MyData(R.drawable.h1, "내친구동생여친", "어제 2시쯤", "길가다 효주봄ㅇㅇ"));
	     arrData.add(new MyData(R.drawable.h2, "지나가다안사람", "그제 5시", "길가다 효주봄ㅇㅇ"));
	     arrData.add(new MyData(R.drawable.h3, "님이라는글자", "그제 3시", "길가다 효주봄ㅇㅇ"));
	    }
}
