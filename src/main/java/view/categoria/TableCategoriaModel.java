package view.categoria;

import java.util.ArrayList;
import java.util.List;

import models.Categoria;
import view.table.JTableModel;

public class TableCategoriaModel extends JTableModel<Categoria>{
	private static final long serialVersionUID = 2259690966041330349L;
	private List<Categoria> tabela;
	
	private final String colunaCategoria[] = {"Código", "Nome"};
	
	private final Integer tamanhoCampo[] = {};
	
	
	public TableCategoriaModel() {
		tabela = new ArrayList<Categoria>();
		this.coluna = colunaCategoria;
	}
	
	public TableCategoriaModel(List<Categoria> tabela) {
		super(tabela);
		this.tabela = tabela;
		this.coluna = colunaCategoria;
	}
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		Categoria categoria = tabela.get(linha);
		switch(coluna) {
			case 0:{
				return categoria.getId();
			}
			case 1:{
				return categoria.getNome();
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
	
	public Categoria getCategoria(int index) {
		return getTabela().get(index);
	}
	
	public void salvarCategoria(Categoria categoria) {
		getTabela().add(categoria);
		fireTableRowsInserted(getRowCount()-1, getColumnCount()-1);
	}
	
	public void alterarCategoria(Categoria categoria, int index) {
		getTabela().set(index, categoria);
		fireTableRowsUpdated(index, index);
	}
	
	public void removerCategoria(int index) {
		getTabela().remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		getTabela().clear();
		fireTableDataChanged();
	}

	public List<Categoria> getTabela() {
		return tabela;
	}

	public void setTabela(List<Categoria> tabela) {
		this.tabela = tabela;
	}

	public String[] getColuna() {
		return coluna;
	}

	public Integer[] getTamanhoCampo() {
		return tamanhoCampo;
	}
	
	
	
}
