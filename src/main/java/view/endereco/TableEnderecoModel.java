package view.endereco;

import java.util.ArrayList;
import java.util.List;

import models.Endereco;
import view.table.JTableModel;

public class TableEnderecoModel extends JTableModel<Endereco>{
	private static final long serialVersionUID = 2259690966041330349L;
	private List<Endereco> tabela;
	
	private final String colunaEndereco[] = {"Código", "Numero", "Bairro", "CEP"};
	
	private final Integer tamanhoCampo[] = {};
	
	
	public TableEnderecoModel() {
		tabela = new ArrayList<Endereco>();
		this.coluna = colunaEndereco;
	}
	
	public TableEnderecoModel(List<Endereco> tabela) {
		super(tabela);
		this.tabela = tabela;
		this.coluna = colunaEndereco;
	}
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		Endereco endereco = tabela.get(linha);
		switch(coluna) {
			case 0:{
				return endereco.getId();
			}
			case 1:{
				return endereco.getNumero();
			}
			case 2:{
				return endereco.getBairro();
			}
			case 3:{
				return endereco.getCep();
			}
			default:
				return null;
		}
		
	}
	
	@Override
	public Class<?> getColumnClass(int coluna){
		switch (coluna) {
			case 0:{
				return Long.class;
			}
			case 1:{
				return String.class;
			}
			case 2:{
				return String.class;
			}
			case 3:{
				return String.class;
			}
			default:
				return null;
		}
	}
	
	public Endereco getEndereco(int index) {
		return getTabela().get(index);
	}
	
	public void salvarEndereco(Endereco endereco) {
		getTabela().add(endereco);
		fireTableRowsInserted(getRowCount()-1, getColumnCount()-1);
	}
	
	public void alterarEndereco(Endereco endereco, int index) {
		getTabela().set(index, endereco);
		fireTableRowsUpdated(index, index);
	}
	
	public void removerEndereco(int index) {
		getTabela().remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		getTabela().clear();
		fireTableDataChanged();
	}

	public List<Endereco> getTabela() {
		return tabela;
	}

	public void setTabela(List<Endereco> tabela) {
		this.tabela = tabela;
	}

	public String[] getColuna() {
		return coluna;
	}

	public Integer[] getTamanhoCampo() {
		return tamanhoCampo;
	}
	
	
	
}
