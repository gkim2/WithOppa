package js.withoppa;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener{
	 
	//좌우메뉴 맴버필드
	boolean toggleLimit;
	float firstX;
	float firstY;
	float lastX;
	float lastY;

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
	
	//프레그먼트 맴버필드
	int fragmentIndex;
	public final static int FRAGMENT_LIST = 0;
	public final static int FRAGMENT_TEST = 1;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		panelWidth = (int) ((metrics.widthPixels) * 0.75);
		
		View ic_leftslidemenu = (View) findViewById(R.id.ic_leftslidemenu);
		//슬라이드뷰 초기설정
		slidingPanel = (LinearLayout) findViewById(R.id.slidingPanel);
		slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel
				.getLayoutParams();
		slidingPanelParameters.width = metrics.widthPixels;
		slidingPanel.setLayoutParams(slidingPanelParameters);

		// left 메뉴 초기설정
		leftMenuPanel = (LinearLayout) findViewById(R.id.leftMenuPanel);
		leftMenuPanelParameters = (FrameLayout.LayoutParams) leftMenuPanel
				.getLayoutParams();
		leftMenuPanelParameters.width = panelWidth;
		leftMenuPanel.setLayoutParams(leftMenuPanelParameters);

		// right 메뉴 초기설정
		rightMenuPanel = (RelativeLayout) findViewById(R.id.rightMenuPanel);
		rightMenuPanelParameters = (FrameLayout.LayoutParams) rightMenuPanel
				.getLayoutParams();
		rightMenuPanelParameters.width = panelWidth;
		rightMenuPanel.setLayoutParams(rightMenuPanelParameters);

        //프레그먼트 초기설정
        fragmentIndex = FRAGMENT_LIST;
        fragmentReplace(fragmentIndex);
        //버튼 리스너 등록
        TextView test1Btn = (TextView) findViewById(R.id.test1_btn);
        test1Btn.setOnClickListener(this);
		TextView test2Btn = (TextView) findViewById(R.id.test2_btn);
		test2Btn.setOnClickListener(this);
        
	}

	/**
	 * 좌측 메뉴 토글시 처리
	 */
	void menuLeftSlideAnimationToggle() {

		if (!isLeftExpanded) {

			isLeftExpanded = true;
			rightMenuPanel.setVisibility(View.GONE);
			leftMenuPanel.setVisibility(View.VISIBLE);

			// Expand
			new ExpandAnimation(slidingPanel, panelWidth, "left",
					Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, 0.75f, 0, 0.0f, 0, 0.0f);

			// disable all of main view
			FrameLayout viewGroup = (FrameLayout)findViewById(R.id.ll_fragment)
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

	//좌우메뉴
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(ev.getAction()==MotionEvent.ACTION_DOWN){
			firstX = ev.getX(); 
			firstY = ev.getY(); 
		}else if(ev.getAction()==MotionEvent.ACTION_UP){
			lastX = ev.getX();
			lastY = ev.getY();
			//스크롤 방지
			if(isRightExpanded!=true && isLeftExpanded!=true && firstY-lastY<40 && firstY-lastY>-40){
				toggleLimit = true;
			}else{
				toggleLimit = false;
			}
			
			if((firstX - lastX)>40 && toggleLimit){
				menuRightSlideAnimationToggle();
				Log.e("Right", "Right");
			}else if((firstX - lastX)<-40 && toggleLimit){
				menuLeftSlideAnimationToggle();
				Log.e("Left", "Left");
			}
		}
		
		return super.dispatchTouchEvent(ev);
	}
	
	//프레그먼트 설정 
	public void fragmentReplace(int replaceIndex) {

		Fragment newFragment = getFragment(replaceIndex);

		final FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		transaction.replace(R.id.ll_fragment, newFragment);

		transaction.commit();
		
	}
	//프레그먼트 생성
	public Fragment getFragment(int index){
		
		Fragment newFragment = null;
		
		switch (index) {
		case 0:
			newFragment = new FragmentList();
			if(isLeftExpanded){
				menuLeftSlideAnimationToggle();
			}
			break;

		case 1:
			newFragment = new FragmentTest();
			if(isLeftExpanded){
				menuLeftSlideAnimationToggle();
			}
			break;
		}
		return newFragment;
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {

		case R.id.test1_btn:
			fragmentIndex = FRAGMENT_LIST;
			fragmentReplace(fragmentIndex);
			break;
		case R.id.test2_btn:
			fragmentIndex = FRAGMENT_TEST;
			fragmentReplace(fragmentIndex);
			break;
		}
		
	}
	
}
