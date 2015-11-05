package br.edu.ifsp.ddm.exemplocadastropessoabd.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DAO <T extends Object> extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "sgcp.sqlite3";
	private static final int DATABASE_VERSION = 1;
	public static final String TABLE_PESSOA = "pessoa";
	
	protected Context context;
	protected String[] campos;
	protected String tableName;
	
	
	private static final String CREATE_TABLE_PESSOA = "CREATE TABLE pessoa ( "
			+ " id integer primary key autoincrement NOT NULL,"
			+ " idade integer,"
			+ " nome varchar(75),"
			+ " sexo VARCHAR( 1 ));";

	public DAO(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_TABLE_PESSOA);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(" DROP TABLE IF EXISTS " + TABLE_PESSOA);
		
		onCreate(db);
	}
	
	
	
	
	
	
	protected void closeDatabase(SQLiteDatabase db)
	{		
		if(db.isOpen())
			db.close();		
	}

}	

