package js.withoppa;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnClickListener {

	/* slide menu */
	private DisplayMetrics metrics;
	private LinearLayout slidingPanel;
	private LinearLayout leftMenuPanel;
	private RelativeLayout rightMenuPanel;
	private FrameLayout.LayoutParams slidingPanelParameters;
	private FrameLayout.LayoutParams leftMenuPanelParameters;
	private FrameLayout.LayoutParams rightMenuPanelParameters;
	private int panelWidth;
	private static boolean isLeftExpanded;
	public static boolean isRightExpanded;
	private Button bt_left, bt_right;

	ListView list;
	MyAdapter adapter;
	ArrayList<MyData> arrData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		panelWidth = (int) ((metrics.widthPixels) * 0.75);

		bt_left = (Button) findViewById(R.id.bt_left);
		bt_right = (Button) findViewById(R.id.bt_right);
		bt_left.setOnClickListener(this);
		bt_right.setOnClickListener(this);
		View ic_leftslidemenu = (View) findViewById(R.id.ic_leftslidemenu);
		// sliding view Initialize
		slidingPanel = (LinearLayout) findViewById(R.id.slidingPanel);
		slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel
				.getLayoutParams();
		slidingPanelParameters.width = metrics.widthPixels;
		slidingPanel.setLayoutParams(slidingPanelParameters);

		// left slide menu initialize
		leftMenuPanel = (LinearLayout) findViewById(R.id.leftMenuPanel);
		leftMenuPanelParameters = (FrameLayout.LayoutParams) leftMenuPanel
				.getLayoutParams();
		leftMenuPanelParameters.width = panelWidth;
		leftMenuPanel.setLayoutParams(leftMenuPanelParameters);

		// right slide menu initialize
		rightMenuPanel = (RelativeLayout) findViewById(R.id.rightMenuPanel);
		rightMenuPanelParameters = (FrameLayout.LayoutParams) rightMenuPanel
				.getLayoutParams();
		rightMenuPanelParameters.width = panelWidth;
		rightMenuPanel.setLayoutParams(rightMenuPanelParameters);

		//리스트에 보여줄 데이터를 세팅한다.
        setData();
        
        //어댑터 생성
        adapter = new MyAdapter(this, arrData);
        
        //리스트뷰에 어댑터 연결
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
		
	}

	 private void setData(){
	     arrData = new ArrayList<MyData>();
	     arrData.add(new MyData(R.drawable.h1, "내친구동생여친", "어제 2시쯤", "길가다 효주봄ㅇㅇ"));
	     arrData.add(new MyData(R.drawable.h2, "지나가다안사람", "그제 5시", "길가다 효주봄ㅇㅇ"));
	     arrData.add(new MyData(R.drawable.h3, "님이라는글자", "그제 3시", "길가다 효주봄ㅇㅇ"));
	    }
	
	/**
	 * 좌측 메뉴 토글시 처리
	 */
	void menuLeftSlideAnimationToggle() {

		if (!isLeftExpanded) {

			// networkRequestTimeLineGetNewCnt();

			isLeftExpanded = true;
			rightMenuPanel.setVisibility(View.GONE);
			leftMenuPanel.setVisibility(View.VISIBLE);

			// Expand
			new ExpandAnimation(slidingPanel, panelWidth, "left",
					Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, 0.75f, 0, 0.0f, 0, 0.0f);

			// disable all of main view
			// LinearLayout viewGroup = (LinearLayout) findViewById(
			FrameLayout viewGroup = (FrameLayout) findViewById(R.id.ll_fragment)
					.getParent();
			enableDisableViewGroup(viewGroup, false);

			// enable empty view
			((LinearLayout) findViewById(R.id.ll_empty))
					.setVisibility(View.VISIBLE);

			findViewById(R.id.ll_empty).setEnabled(true);
			/*findViewById(R.id.ll_empty).setOnTouchListener(
					new OnTouchListener() {

						@Override
						public boolean onTouch(View arg0, MotionEvent arg1) {
							menuLeftSlideAnimationToggle();
							return true;
						}
					});*/

		} else {
			isLeftExpanded = false;

			// Collapse
			new CloseAnimation(slidingPanel, panelWidth,
					TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
					TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0, 0.0f);

			// enable all of main view
			// LinearLayout viewGroup = (LinearLayout) findViewById(
			FrameLayout viewGroup = (FrameLayout) findViewById(R.id.ll_fragment)
					.getParent();
			enableDisableViewGroup(viewGroup, true);

			// disable empty view
			((LinearLayout) findViewById(R.id.ll_empty))
					.setVisibility(View.GONE);
			findViewById(R.id.ll_empty).setEnabled(false);

		}
	}

	/**
	 * 오른쪽 메뉴 토글에 대한 처리
	 */
	public void menuRightSlideAnimationToggle() {
		if (!isRightExpanded) {
			isRightExpanded = true;
			rightMenuPanel.setVisibility(View.VISIBLE);
			leftMenuPanel.setVisibility(View.GONE);

			// Expand
			new ExpandAnimation(slidingPanel, panelWidth, "right",
					Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, -0.75f, 0, 0.0f, 0, 0.0f);

			FrameLayout viewGroup = (FrameLayout) findViewById(R.id.ll_fragment)
					.getParent();
			enableDisableViewGroup(viewGroup, false);

			// enable empty view
			((LinearLayout) findViewById(R.id.ll_empty))
					.setVisibility(View.VISIBLE);

			findViewById(R.id.ll_empty).setEnabled(true);
			findViewById(R.id.ll_empty).setOnTouchListener(
					new OnTouchListener() {

						@Override
						public boolean onTouch(View arg0, MotionEvent arg1) {
							menuRightSlideAnimationToggle();
							return true;
						}
					});

		} else {
			isRightExpanded = false;

			// Collapse
			new CloseAnimation(slidingPanel, panelWidth,
					TranslateAnimation.RELATIVE_TO_SELF, -0.75f,
					TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0, 0.0f);

			// enable all of main view
			FrameLayout viewGroup = (FrameLayout) findViewById(R.id.ll_fragment)
					.getParent();
			enableDisableViewGroup(viewGroup, true);

			// disable empty view
			((LinearLayout) findViewById(R.id.ll_empty))
					.setVisibility(View.GONE);
			findViewById(R.id.ll_empty).setEnabled(false);
		}
	}

	/**
	 * 뷰의 동작을 제어한다. 하위 모든 뷰들이 enable 값으로 설정된다.
	 * 
	 * @param viewGroup
	 * @param enabled
	 */
	public static void enableDisableViewGroup(ViewGroup viewGroup,
			boolean enabled) {
		int childCount = viewGroup.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View view = viewGroup.getChildAt(i);

			if (view.getId() != R.id.bt_left) {
				view.setEnabled(enabled);
				if (view instanceof ViewGroup) {
					enableDisableViewGroup((ViewGroup) view, enabled);
				}
			}
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.bt_left:
			menuLeftSlideAnimationToggle();
			break;
		case R.id.bt_right:
			menuRightSlideAnimationToggle();
			break;

		}

	}
}