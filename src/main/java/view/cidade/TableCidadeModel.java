package view.cidade;

import java.util.ArrayList;
import java.util.List;

import models.Cidade;
import view.table.JTableModel;

public class TableCidadeModel extends JTableModel<Cidade>{
	private static final long serialVersionUID = 2259690966041330349L;
	private List<Cidade> tabela;
	
	private final String colunaCidade[] = {"Código", "Nome", "Estado"};
	
	private final Integer tamanhoCampo[] = {};
	
	
	public TableCidadeModel() {
		tabela = new ArrayList<Cidade>();
		this.coluna = colunaCidade;
	}
	
	public TableCidadeModel(List<Cidade> tabela) {
		super(tabela);
		this.tabela = tabela;
		this.coluna = colunaCidade;
	}
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		Cidade cidade = tabela.get(linha);
		switch(coluna) {
			case 0:{
				return cidade.getId();
			}
			case 1:{
				return cidade.getNome();
			}
			case 2:{
				return cidade.getEstado().getNome();
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
			
			default:
				return null;
		}
	}
	
	public Cidade getCidade(int index) {
		return getTabela().get(index);
	}
	
	public void salvarCidade(Cidade cidade) {
		getTabela().add(cidade);
		fireTableRowsInserted(getRowCount()-1, getColumnCount()-1);
	}
	
	public void alterarCidade(Cidade cidade, int index) {
		getTabela().set(index, cidade);
		fireTableRowsUpdated(index, index);
	}
	
	public void removerCidade(int index) {
		getTabela().remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		getTabela().clear();
		fireTableDataChanged();
	}

	public List<Cidade> getTabela() {
		return tabela;
	}

	public void setTabela(List<Cidade> tabela) {
		this.tabela = tabela;
	}

	public String[] getColuna() {
		return coluna;
	}

	public Integer[] getTamanhoCampo() {
		return tamanhoCampo;
	}
	
	
	
}
