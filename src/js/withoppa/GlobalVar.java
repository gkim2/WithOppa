package js.withoppa;

import android.graphics.Bitmap;
import song.writeActivity;
import io.socket.SocketIO;

public class GlobalVar {
	public static SocketIO socket=null;
	public static LoginActivity.UserLoginTask userLoginTask=null;
	public static Bitmap mgImage=null;
	public static String mgIdx=null;
	public static String mgName=null;
	public static writeActivity.UpDataTask upDataTask = null;
}
