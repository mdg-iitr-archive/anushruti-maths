package in.sdslabs.co.anushruti;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper{


	private  static final int DB_VERSION =1;
	private  static final String DB_NAME="LEVEL";
	private  static final String TABLE_CONTENTS= "QUESTION";
	//table columns name
	private  static final String QUES_NO="QuesNo";
	private  static final String STATUS_QUES= "Status_Ques";
		
	public DBHandler(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_LEVEL_TABLE="CREATE TABLE "+TABLE_CONTENTS+
				"("+QUES_NO+" INTEGER PRIMARY KEY,"+STATUS_QUES+" INTEGER PRIMARY KEY"+")";
		db.execSQL(CREATE_LEVEL_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		//drop the table if already exists
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_CONTENTS);
		//create new table
		onCreate(db);
	}
	
	public void addQues(Question question)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(QUES_NO, question.getQuesNo()); // Contact Name
	    values.put(STATUS_QUES, question.getStatus()); // Contact Phone Number
	 
	    // Inserting Row
	    db.insert(TABLE_CONTENTS, null, values);
	    db.close(); // Closing database connection
	}
	
	
    // Getting single ques 
public Question getQuestion(int ques_no) {
    SQLiteDatabase db = this.getReadableDatabase();
 
    Cursor cursor = db.query(TABLE_CONTENTS, new String[] { QUES_NO,
            STATUS_QUES}, QUES_NO + "=?",
            new String[] { String.valueOf(ques_no) }, null, null, null, null);
    if (cursor != null)
        cursor.moveToFirst();
 
    Question question = new Question(Integer.parseInt(cursor.getString(0)),
    		Integer.parseInt(cursor.getString(1)));
    // return contact
    return question;
}

// Getting All Contacts
public List<Question> getAllContacts() {
List<Question> questionList = new ArrayList<Question>();
// Select All Query
String selectQuery = "SELECT  * FROM " + TABLE_CONTENTS;

SQLiteDatabase db = this.getWritableDatabase();
Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
if (cursor.moveToFirst()) {
    do {
        Question question= new Question();
        question.setQuesNo(Integer.parseInt(cursor.getString(0)));
        question.setStatus(Integer.parseInt(cursor.getString(1)));
        // Adding contact to list
        questionList.add(question);
    } while (cursor.moveToNext());
}

// return question list
return questionList;
}


//Getting contacts Count
 public int getQuestionsCount() {
     String countQuery = "SELECT  * FROM " + TABLE_CONTENTS;
     SQLiteDatabase db = this.getReadableDatabase();
     Cursor cursor = db.rawQuery(countQuery, null);
     cursor.close();

     // return count
     return cursor.getCount();
 }
 
 // Updating single question
public int updateQuestion(Question question) {
 SQLiteDatabase db = this.getWritableDatabase();

 ContentValues values = new ContentValues();
 values.put(STATUS_QUES, question.getStatus());

 // updating row
 return db.update(TABLE_CONTENTS, values, QUES_NO + " = ?",
         new String[] { String.valueOf(question.getQuesNo()) });
}

// Deleting single question
public void deleteContact(Question question) {
SQLiteDatabase db = this.getWritableDatabase();
db.delete(TABLE_CONTENTS, QUES_NO + " = ?",
        new String[] { String.valueOf(question.getQuesNo()) });
db.close();
}
}
