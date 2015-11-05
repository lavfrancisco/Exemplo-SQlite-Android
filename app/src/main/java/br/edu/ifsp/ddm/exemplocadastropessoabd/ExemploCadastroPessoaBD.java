package br.edu.ifsp.ddm.exemplocadastropessoabd;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import br.edu.ifsp.ddm.exemplocadastropessoabd.dao.PessoaDAO;
import br.edu.ifsp.ddm.exemplocadastropessoabd.modelo.Pessoa;

public class ExemploCadastroPessoaBD extends Activity {

	private Pessoa p;
	private List<Pessoa> pessoas;
	private PessoaDAO dao;
	private EditText edID;
	private EditText edNome;
	private EditText edIdade;
	private Spinner spSexo;
	private ListView lvPessoas;
	private String operacao;
	private String[] sexo = { "Masculino", "Feminino" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		edID = (EditText) findViewById(R.id.edID);
		edNome = (EditText) findViewById(R.id.edNome);
		edIdade = (EditText) findViewById(R.id.edIdade);
		spSexo = (Spinner) findViewById(R.id.spSexo);
		lvPessoas = (ListView) findViewById(R.id.lvPessoas);
		lvPessoas.setOnItemClickListener(selecionarPessoa);
		lvPessoas.setOnItemLongClickListener(excluirPessoa);
		pessoas = new ArrayList<Pessoa>();
		operacao = new String("Novo");
		dao = new PessoaDAO(getApplicationContext());
		preencherSexo();
		atualizarLista();
	}

	

	private void excluirPessoa(final int idPessoa) {
		
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Excluir pessoa?")
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setMessage("Deseja excluir essa pessoa?")
				.setCancelable(false)
				.setPositiveButton(getString(R.string.sim),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								if (dao.deletar(idPessoa)) {
									atualizarLista();
									exibirMensagem(getString(R.string.msgExclusao));
								} else {
									exibirMensagem(getString(R.string.msgFalhaExclusao));
								}

							}
						})
				.setNegativeButton(getString(R.string.nao),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		builder.create();
		builder.show();

	}

	private void preencherSexo() {
		try {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, sexo);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spSexo.setAdapter(adapter);
		} catch (Exception ex) {
			exibirMensagem("Erro: " + ex.getMessage());
		}

	}

	public void salvar(View v) {
		
		if (operacao.equalsIgnoreCase("Novo")) {
			p = new Pessoa();
		}

		p.setNome(edNome.getText().toString());
		p.setSexo(sexo[spSexo.getSelectedItemPosition()]
				.equalsIgnoreCase("Masculino") ? "M" : "F");
		p.setIdade(Integer.valueOf(edIdade.getText().toString()));

		if (operacao.equalsIgnoreCase("Novo")) {
			dao.salvar(p);
			exibirMensagem("Pessoa cadastrada com sucesso!");
		} else {
			dao.atualizar(p);
			exibirMensagem("Pessoa atualizada com sucesso!");
		}

		atualizarLista();
		limparDados();

	}

	public void novo(View v) {
		operacao=new String("Novo");
		limparDados();

	}

	private void limparDados() {
		edID.setText("");
		edNome.setText("");
		edNome.requestFocus();
		edIdade.setText("");
		spSexo.setSelection(0);

	}

	private void atualizarLista() {
		
		pessoas = dao.listAll();
		if (pessoas != null) {
			if (pessoas.size() > 0) {
				PessoaListAdapter pla = new PessoaListAdapter(
						getApplicationContext(), pessoas);
				lvPessoas.setAdapter(pla);
			}

		}

	}

	private OnItemClickListener selecionarPessoa = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
			operacao = new String("Atualizar");
			p = pessoas.get(pos);
			preecherDados(p);

		}

	};
	
	private OnItemLongClickListener excluirPessoa = new OnItemLongClickListener() {

		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int pos, long arg3) {
			excluirPessoa(pessoas.get(pos).getId());
			return true;
		}

		

	};

	private void preecherDados(Pessoa pessoa) {
		edID.setText(String.valueOf(pessoa.getId()));
		edNome.setText(pessoa.getNome());
		edIdade.setText(String.valueOf(pessoa.getIdade()));
		spSexo.setSelection(pessoa.getSexo().equalsIgnoreCase("M") ? 0 : 1);

	}

	private void exibirMensagem(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}
}
