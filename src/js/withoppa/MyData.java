package js.withoppa;

public class MyData {
	 private int image;
	 private String name;
	 private String date;
	 private String comment;
	 
	 public MyData(int image, String name, String date, String comment){
	  this.image = image;
	  this.name = name;
	  this.date=date;
	  this.comment = comment;
	 }
	 public int getImage() {
	  return image;
	 }
	 public String getName() {
	  return name;
	 }
	public String getDate() {
		return date;
	}
	public String getComment() {
		return comment;
	}
}
