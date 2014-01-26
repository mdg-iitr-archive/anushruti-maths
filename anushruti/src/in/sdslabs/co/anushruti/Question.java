package in.sdslabs.co.anushruti;

public class Question {

	int ques_no;
	int status_ques;//  0-unattempted , 1-- correct, 2--incorrect

	public  Question() {
		
	}
	
	public Question(int lques_no,int lstatus_ques){
		this.ques_no=lques_no;
		this.status_ques=lstatus_ques;
	}
	public Question(int lstatus_ques)
	{
		this.status_ques=lstatus_ques;
	}
	public int getQuesNo()
	{
		return this.ques_no;
	}
	public void setQuesNo(int lques_no)
	{
		this.ques_no= lques_no;
	}
	public int getStatus()
	{
		return this.status_ques;
	}
	public void setStatus(int lstatus_ques)
	{
		this.status_ques= lstatus_ques;
	}
	
}
