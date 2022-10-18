package view.estado;

import java.util.ArrayList;
import java.util.List;

import models.Estado;
import view.table.JTableModel;

public class TableEstadoModel extends JTableModel<Estado>{
	private static final long serialVersionUID = 2259690966041330349L;
	private List<Estado> tabela;
	
	private final String colunaEstado[] = {"Código", "Nome e sigla do estado"};
	
	private final Integer tamanhoCampo[] = {};
	
	
	public TableEstadoModel() {
		tabela = new ArrayList<Estado>();
		this.coluna = colunaEstado;
	}
	
	public TableEstadoModel(List<Estado> tabela) {
		super(tabela);
		this.tabela = tabela;
		this.coluna = colunaEstado;
	}
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		Estado estado = tabela.get(linha);
		switch(coluna) {
			case 0:{
				return estado.getId();
			}
			case 1:{
				return estado.getNome();
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
	
	public Estado getEstado(int index) {
		return getTabela().get(index);
	}
	
	public void salvarEstado(Estado estado) {
		getTabela().add(estado);
		fireTableRowsInserted(getRowCount()-1, getColumnCount()-1);
	}
	
	public void alterarEstado(Estado estado, int index) {
		getTabela().set(index, estado);
		fireTableRowsUpdated(index, index);
	}
	
	public void removerEstado(int index) {
		getTabela().remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		getTabela().clear();
		fireTableDataChanged();
	}

	public List<Estado> getTabela() {
		return tabela;
	}

	public void setTabela(List<Estado> tabela) {
		this.tabela = tabela;
	}

	public String[] getColuna() {
		return coluna;
	}

	public Integer[] getTamanhoCampo() {
		return tamanhoCampo;
	}
	
	
	
}
