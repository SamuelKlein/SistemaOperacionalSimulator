package gerenciadores.acoes;

import java.awt.Color;

public interface DadosProcesso {
	
	public Color getCor();
	public void setPrioridade( int prioridade );
	public int getPrioridade();
	public int getFrame();
	public int getEstado();
	public int getPid();
	public int getTempoUCP();
	public String getDescricaoEstado();
	
	public void suspender();
	public void prosseguir();
	public void finalizar();
}
