package view.codigo;

import java.util.ArrayList;
import java.util.List;

import models.Codigo;
import view.table.JTableModel;

public class TableCodigoModel extends JTableModel<Codigo>{
	private static final long serialVersionUID = 2259690966041330349L;
	private List<Codigo> tabela;
	
	private final String colunaCodigo[] = {"Código", "Número"};
	
	private final Integer tamanhoCampo[] = {};
	
	
	public TableCodigoModel() {
		tabela = new ArrayList<Codigo>();
		this.coluna = colunaCodigo;
	}
	
	public TableCodigoModel(List<Codigo> tabela) {
		super(tabela);
		this.tabela = tabela;
		this.coluna = colunaCodigo;
	}
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		Codigo codigo = tabela.get(linha);
		switch(coluna) {
			case 0:{
				return codigo.getId();
			}
			case 1:{
				return codigo.getNumero();
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
				return Integer.class;
			}
			
			
			default:
				return null;
		}
	}
	
	public Codigo getCodigo(int index) {
		return getTabela().get(index);
	}
	
	public void salvarCodigo(Codigo codigo) {
		getTabela().add(codigo);
		fireTableRowsInserted(getRowCount()-1, getColumnCount()-1);
	}
	
	public void alterarCodigo(Codigo codigo, int index) {
		getTabela().set(index, codigo);
		fireTableRowsUpdated(index, index);
	}
	
	public void removerCodigo(int index) {
		getTabela().remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		getTabela().clear();
		fireTableDataChanged();
	}

	public List<Codigo> getTabela() {
		return tabela;
	}

	public void setTabela(List<Codigo> tabela) {
		this.tabela = tabela;
	}

	public String[] getColuna() {
		return coluna;
	}

	public Integer[] getTamanhoCampo() {
		return tamanhoCampo;
	}
	
	
	
}
