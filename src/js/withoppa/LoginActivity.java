package js.withoppa;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.SocketIO;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	/*private UserLoginTask mAuthTask = null;*/

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	
	public static Activity staticLoginAct;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.e("쓰레드 남았냐",String.valueOf(GlobalVar.userLoginTask!=null));
		Log.e("소켓 남았냐",String.valueOf(GlobalVar.socket!=null));
		if(GlobalVar.userLoginTask!=null) GlobalVar.userLoginTask=null;
		if(GlobalVar.socket!=null){
			GlobalVar.socket.disconnect();
			GlobalVar.socket=null;
		}

		setContentView(R.layout.activity_login);
		
		staticLoginAct=LoginActivity.this;
		
		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
		findViewById(R.id.sign_up_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
						startActivity(intent);
					}
				});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		Log.e("attemptLogin()실행", "attemptLogin()실행");
		/*if (mAuthTask != null) {
			return;
		}*/
		if (GlobalVar.userLoginTask != null) {
			return;
		}
		/*if (GlobalVar.userLoginTask == null) {
			GlobalVar.userLoginTask=new UserLoginTask();
		}*/

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			/*mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);*/
			Log.e("쓰레드생성", "쓰레드생성");
			GlobalVar.userLoginTask=new UserLoginTask();
			GlobalVar.userLoginTask.execute((Void)null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		IOCallBackImpl ioCallBackImpl;
		public UserLoginTask() {
			if(GlobalVar.socket==null){
				Log.e("쓰레드 생성자", "쓰레드 생성자");
				try {
					String host = "http://192.168.0.175";
					GlobalVar.socket = new SocketIO(host);
					ioCallBackImpl=new IOCallBackImpl();
					GlobalVar.socket.connect(ioCallBackImpl);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
			Log.e("doInBackground()실행", "doInBackground()실행");
			JSONObject data=new JSONObject();
			try {
				data.put("id",mEmail);
				data.put("pw",mPassword);
				GlobalVar.socket.emit("login",data);
				while(true){
					Thread.sleep(1000);
					Log.e("ioCallBackImpl.tried", String.valueOf(ioCallBackImpl.tried));
					if(ioCallBackImpl.tried){
						break;
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// TODO: register the new account here.
			return ioCallBackImpl.logedIn;
		}

		@Override
		protected void onPostExecute(Boolean success) {
			/*mAuthTask = null;*/
			/*GlobalVar.userLoginTask=null;
			GlobalVar.socket.disconnect();
			GlobalVar.socket=null;*/
			showProgress(false);
			Log.e("onPostExecute()실행", "onPostExecute()실행");
			if (success) {
				Intent intent=new Intent(LoginActivity.this,MainActivity.class);
				ioCallBackImpl.tried=false;
				startActivity(intent);
			} else {
				GlobalVar.userLoginTask=null;
				GlobalVar.socket.disconnect();
				GlobalVar.socket=null;
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				ioCallBackImpl.tried=false;
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			/*mAuthTask = null;*/
			GlobalVar.userLoginTask=null;
			GlobalVar.socket.disconnect();
			GlobalVar.socket=null;
			Log.e("onCancelled()실행", "onCancelled()실행");
			showProgress(false);
		}
	}
}
