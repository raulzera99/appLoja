package view.cliente;

import java.util.ArrayList;
import java.util.List;

import models.Cliente;
import view.table.JTableModel;

public class TableClienteModel extends JTableModel<Cliente>{
	private static final long serialVersionUID = 2259690966041330349L;
	private List<Cliente> tabela;
	
	private final String colunaCliente[] = {"Código", "Nome", "Email", "Tipo", "CPF/CNPJ"};
	
	private final Integer tamanhoCampo[] = {};
	
	
	public TableClienteModel() {
		tabela = new ArrayList<Cliente>();
		this.coluna = colunaCliente;
	}
	
	public TableClienteModel(List<Cliente> tabela) {
		super(tabela);
		this.tabela = tabela;
		this.coluna = colunaCliente;
	}
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		Cliente cliente = tabela.get(linha);
		switch(coluna) {
			case 0:{
				return cliente.getId();
			}
			case 1:{
				return cliente.getNome();
			}
			case 2:{
				return cliente.getEmail();
			}
			case 3:{
				return cliente.getTipo().toString();
			}
			case 4:{
				return cliente.getCpfOuCnpj();
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
			case 4:{
				return String.class;
			}
			
			default:
				return null;
		}
	}
	
	public Cliente getCliente(int index) {
		return getTabela().get(index);
	}
	
	public void salvarCliente(Cliente cliente) {
		getTabela().add(cliente);
		fireTableRowsInserted(getRowCount()-1, getColumnCount()-1);
	}
	
	public void alterarCliente(Cliente cliente, int index) {
		getTabela().set(index, cliente);
		fireTableRowsUpdated(index, index);
	}
	
	public void removerCliente(int index) {
		getTabela().remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		getTabela().clear();
		fireTableDataChanged();
	}

	public List<Cliente> getTabela() {
		return tabela;
	}

	public void setTabela(List<Cliente> tabela) {
		this.tabela = tabela;
	}

	public String[] getColuna() {
		return coluna;
	}

	public Integer[] getTamanhoCampo() {
		return tamanhoCampo;
	}
	
	
	
}
