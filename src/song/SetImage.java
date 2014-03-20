package song;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class SetImage {
	
	// 이미지 리사이징
		Drawable resize( String path )
		{
			/// 비트맵 저장할 변수를 하나 생성한다.
		    Bitmap bmp ;
		    /// 이미지 불러올때 사용할 옵션을 만든다.
		    BitmapFactory.Options options2 =  new BitmapFactory.Options( );
		    /// 이미지 가져올때 샘플링할 옵션
		    /// 값이 클수록 작게 가져온다.
		    options2.inSampleSize = 4 ;
		    
		    /// 해당 옵션을 사용하여 이미지를 가져온다.
		    /// 아래 줄에서 실제 이미지를 메모리에 전부 로드하지는 않는다.
		    bmp = BitmapFactory.decodeFile( path, options2 ) ;
		    
		    /// 가져온 이미지가 Bitmap(비트맵)이라서 Drawable로 바꿔줘야한다.
		    /// Drawable형태의 Bitmap로 바꾼다.
		    /// 실제 메모리에 이미지를 로드한다.
		    BitmapDrawable dbmp = new BitmapDrawable( bmp );
		    /// Drawable 형태로 형변환 해서 반환한다.
		    return (Drawable)dbmp ;
		}
		
		

}
