package br.edu.ifsp.ddm.exemplocadastropessoabd;

import java.util.List;

import br.edu.ifsp.ddm.exemplocadastropessoabd.R;
import br.edu.ifsp.ddm.exemplocadastropessoabd.modelo.Pessoa;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PessoaListAdapter extends BaseAdapter {

	private Context context;
	private List<Pessoa> lista;

	public PessoaListAdapter(Context context, List<Pessoa> lista) {
		this.context = context;
		this.lista = lista;
	}

	public int getCount() {
		return lista.size();
	}

	public Object getItem(int position) {
		return lista.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Pessoa p = lista.get(position);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.pessoas, null);
		
		TextView id = (TextView) view.findViewById(R.id.txtIdPessoa);
		id.setText("ID: "+String.valueOf(p.getId()));
		
		
		TextView nome = (TextView) view.findViewById(R.id.txtNomePessoa);
		nome.setText("Nome: "+p.getNome());
		
		TextView idade = (TextView) view.findViewById(R.id.txtIdadePessoa);
		idade.setText("Idade: "+String.valueOf(p.getIdade()));
		
		TextView sexo = (TextView) view.findViewById(R.id.txtSexo);
		sexo.setText("Sexo: "+p.getSexo());
		
		return view;
	}
}