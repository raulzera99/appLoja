package view.telefone;

import java.util.ArrayList;
import java.util.List;

import models.Telefone;
import view.table.JTableModel;

public class TableTelefoneModel extends JTableModel<Telefone>{
	private static final long serialVersionUID = 2259690966041330349L;
	private List<Telefone> tabela;
	
	private final String colunaTelefone[] = {"Código", "Nome e sigla do telefone"};
	
	private final Integer tamanhoCampo[] = {};
	
	
	public TableTelefoneModel() {
		tabela = new ArrayList<Telefone>();
		this.coluna = colunaTelefone;
	}
	
	public TableTelefoneModel(List<Telefone> tabela) {
		super(tabela);
		this.tabela = tabela;
		this.coluna = colunaTelefone;
	}
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		Telefone telefone = tabela.get(linha);
		switch(coluna) {
			case 0:{
				return telefone.getId();
			}
			case 1:{
				return telefone.getNome();
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
			default:
				return null;
		}
	}
	
	public Telefone getTelefone(int index) {
		return getTabela().get(index);
	}
	
	public void salvarTelefone(Telefone telefone) {
		getTabela().add(telefone);
		fireTableRowsInserted(getRowCount()-1, getColumnCount()-1);
	}
	
	public void alterarTelefone(Telefone telefone, int index) {
		getTabela().set(index, telefone);
		fireTableRowsUpdated(index, index);
	}
	
	public void removerTelefone(int index) {
		getTabela().remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		getTabela().clear();
		fireTableDataChanged();
	}

	public List<Telefone> getTabela() {
		return tabela;
	}

	public void setTabela(List<Telefone> tabela) {
		this.tabela = tabela;
	}

	public String[] getColuna() {
		return coluna;
	}

	public Integer[] getTamanhoCampo() {
		return tamanhoCampo;
	}
	
	
	
}
