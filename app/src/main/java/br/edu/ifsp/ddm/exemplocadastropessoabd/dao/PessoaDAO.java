package br.edu.ifsp.ddm.exemplocadastropessoabd.dao;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.ddm.exemplocadastropessoabd.modelo.Pessoa;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class PessoaDAO extends DAO<Pessoa> {
	
	private SQLiteDatabase database;
	
	

	public PessoaDAO(Context context) {
		super(context);
		campos = new String[]{"id","idade","nome","sexo"};
		tableName = "pessoa";
		database = getWritableDatabase();
		
	}
	
	
	public Pessoa getByNome(String nome) {
		Pessoa pessoa = null;
		
		Cursor cursor = executeSelect("nome = ?", new String[]{nome}, null);
		
		if(cursor!=null && cursor.moveToFirst())
		{
			pessoa = serializeByCursor(cursor);
		}
		if(!cursor.isClosed())
		{
			cursor.close();
		}
		
		
		return pessoa;
	}

	public Pessoa getByID(Integer id) {
		Pessoa pessoa = null;
		
		Cursor cursor = executeSelect("id = ?", new String[]{String.valueOf(id)}, null);
		
		if(cursor!=null && cursor.moveToFirst())
		{
			pessoa = serializeByCursor(cursor);			
		}
		if(!cursor.isClosed())
		{
			cursor.close();
		}
		
		
		return pessoa;
	}

	public List<Pessoa> listAll() {
		List<Pessoa> list = new ArrayList<Pessoa>();
		Cursor cursor = executeSelect(null, null, "1");
		

		if(cursor!=null && cursor.moveToFirst())
		{
			do{
				list.add(serializeByCursor(cursor));
			}while(cursor.moveToNext());
			
			
		}
		
		if(!cursor.isClosed())
		{
			cursor.close();
		}
		
		return list;
		
		
	}

	public boolean salvar(Pessoa pessoa) {
		ContentValues values = serializeContentValues(pessoa);
		if(database.insert(tableName, null, values)>0)
			return true;			
		else
			return false;
	}

	public boolean deletar(Integer id) {
		if(database.delete(tableName, "id = ?", new String[]{String.valueOf(id)})>0)
			return true;
		else
			return false;
	}

	public boolean atualizar(Pessoa pessoa) {
		ContentValues values = serializeContentValues(pessoa);
		if(database.update(tableName, values, "id = ?", new String[]{String.valueOf(pessoa.getId())})>0)
			return true;
		else
			return false;
	}		

	
	private Pessoa serializeByCursor(Cursor cursor)
	{
		Pessoa pessoa = new Pessoa();
		pessoa.setId(cursor.getInt(0));
		pessoa.setIdade(cursor.getInt(1));
		pessoa.setNome(cursor.getString(2));
		pessoa.setSexo(cursor.getString(3));		
		
		return pessoa;
		
	}
	
	private ContentValues serializeContentValues(Pessoa pessoa)
	{
		ContentValues values = new ContentValues();
		values.put("id", pessoa.getId());
		values.put("idade", pessoa.getIdade());
		values.put("nome", pessoa.getNome());
		values.put("sexo", pessoa.getSexo());
		
		return values;
	}	
	
	private Cursor executeSelect(String selection, String[] selectionArgs, String orderBy)
	{
		
		return database.query(tableName,campos, selection, selectionArgs, null, null, orderBy);		

	}
	
	


}
