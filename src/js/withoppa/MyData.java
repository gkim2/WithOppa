package js.withoppa;

import android.graphics.Bitmap;

public class MyData {
	 private Bitmap image;
	 private String name;
	 private String date;
	 private String comment;
	 
	 public MyData(){}
	 
	 public MyData(Bitmap image, String name, String date, String comment){
	  this.setImage(image);
	  this.setName(name);
	  this.setDate(date);
	  this.setComment(comment);
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
}
