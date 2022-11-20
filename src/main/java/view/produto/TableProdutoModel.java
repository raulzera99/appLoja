package view.produto;

import java.util.ArrayList;
import java.util.List;

import models.Produto;
import view.table.JTableModel;

public class TableProdutoModel extends JTableModel<Produto>{
	private static final long serialVersionUID = 2259690966041330349L;
	private List<Produto> tabela;
	
	private final String colunaProduto[] = {"Código", "Nome", "Preço", "Categoria"};
	
	private final Integer tamanhoCampo[] = {};
	
	
	public TableProdutoModel() {
		tabela = new ArrayList<Produto>();
		this.coluna = colunaProduto;
	}
	
	public TableProdutoModel(List<Produto> tabela) {
		super(tabela);
		this.tabela = tabela;
		this.coluna = colunaProduto;
	}
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		Produto produto = tabela.get(linha);
		switch(coluna) {
			case 0:{
				return produto.getId();
			}
			case 1:{
				return produto.getNome();
			}
			case 2:{
				return produto.getPreco();
			}
			case 3:{
				return produto.getCategorias().toString();
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
	
	public Produto getProduto(int index) {
		return getTabela().get(index);
	}
	
	public void salvarProduto(Produto produto) {
		getTabela().add(produto);
		fireTableRowsInserted(getRowCount()-1, getColumnCount()-1);
	}
	
	public void alterarProduto(Produto produto, int index) {
		getTabela().set(index, produto);
		fireTableRowsUpdated(index, index);
	}
	
	public void removerProduto(int index) {
		getTabela().remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		getTabela().clear();
		fireTableDataChanged();
	}

	public List<Produto> getTabela() {
		return tabela;
	}

	public void setTabela(List<Produto> tabela) {
		this.tabela = tabela;
	}

	public String[] getColuna() {
		return coluna;
	}

	public Integer[] getTamanhoCampo() {
		return tamanhoCampo;
	}
	
	
	
}
