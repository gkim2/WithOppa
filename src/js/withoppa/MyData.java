package js.withoppa;

import android.graphics.Bitmap;

public class MyData {
	 private Bitmap image;
	 private String name;
	 private String date;
	 private String comment;
	 private Bitmap mgImage;
	 private String mgIdx;
	 
	 public MyData(){}
	 
	 public MyData(Bitmap image, String name, String date, String comment,Bitmap mgImage,String mgIdx){
	  this.image=image;
	  this.name=name;
	  this.date=date;
	  this.comment=comment;
	  this.mgImage=mgImage;
	  this.mgIdx=mgIdx;
	 }

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Bitmap getMgImage() {
		return mgImage;
	}

	public void setMgImage(Bitmap mgImage) {
		this.mgImage = mgImage;
	}

	public String getMgIdx() {
		return mgIdx;
	}

	public void setMgIdx(String mgIdx) {
		this.mgIdx = mgIdx;
	}
}
