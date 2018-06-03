package data;

public class Train {
	public int train_id;
	public int date;
	public int t1, t2;
	public double early, student;
	
	public Train(int train_id, int date, int t1, int t2, double early, double student) {
		this.train_id = train_id;
		this.date = date;
		this.t1 = t1;
		this.t2 = t2;
		this.early = early;
		this.student = student;
	}
}
