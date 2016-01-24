package gerenciadores;

import gerenciadores.acoes.DadosProcesso;

import java.awt.Color;

public class Processo implements DadosProcesso {
	
	public final static int ESTADO_NOVO			 	= 0;
	public final static int ESTADO_PRONTO 			= 1;
	public final static int ESTADO_ESPERANDO 		= 2;
	public final static int ESTADO_EXECUTANDO 		= 3;
	public final static int ESTADO_ENCERRADO 		= 4;
	public final static int ESTADO_ESPERANDO_E_S    = 5;
	public final static int ESTADO_EM_E_S 			= 6;
	public final static int ESTADO_FIM_E_S 			= 7;
	
	
	public final static int PROCESSO_CPU_BOUND	 			= 0;
	public final static int PROCESSO_IO_BOUND_DISCO			= 1;
	public final static int PROCESSO_IO_BOUND_FILA  		= 2;
	public final static int PROCESSO_IO_BOUND_TEMINAL		= 3;
	public final static int PROCESSO_CPU_IO_BOUND_DISCO		= 4;
	public final static int PROCESSO_CPU_IO_BOUND_FILA  	= 5;
	public final static int PROCESSO_CPU_IO_BOUND_TEMINAL	= 6;

	private int tempoUCP = 200;	
	private int pid;
	private int prioridade;
	private int tipoProcesso;
	private int frame;
	private Color cor;
	
	private int estado = 0;
	
	public Processo( int prioridade, int tipoProcesso, int frame, Color cor ){
		this.prioridade = prioridade;
		this.tipoProcesso = tipoProcesso;
		this.frame = frame;
		this.cor = cor;
		tempoUCP = (int) ((int) 20 * Math.random());
	}
	
	public void setPid(int pid) {
		this.pid = pid;
	}
	
	public void setCor(Color cor) {
		this.cor = cor;
	}
	
	public Color getCor() {
		return cor;
	}
	
	public void setPrioridade( int prioridade ) {
		this.prioridade = prioridade;
	}
	
	public int getPrioridade() {
		return prioridade;
	}
	
	public int getFrame() {
		return frame;
	}
	
	public int getEstado() {
		return estado;
	}
	
	public String getDescricaoEstado(){
		switch( estado ){
			case ESTADO_NOVO 		: return "Novo";
			case ESTADO_PRONTO 		: return "Pronto";
			case ESTADO_ESPERANDO 	: return "Esperando";
			case ESTADO_EXECUTANDO 	: return "Executando";
			case ESTADO_ENCERRADO 	: return "Encerrado";
			case ESTADO_EM_E_S 		: return "Em IO";
			case ESTADO_FIM_E_S 	: return "Fim IO";
			case ESTADO_ESPERANDO_E_S : return "Esperando OI";
			
		}
		return null;
	}
	
	public int getPid() {
		return pid;
	}
	
	public int getTipoProcesso() {
		return tipoProcesso;
	}
	
	public int getTempoUCP() {
		return tempoUCP;
	}
	
	public void setFrame(int frame) {
		this.frame = frame;
	}
	
	public void setEstado(int estado) {
		this.estado = estado;
	}

	public void finalizar() {
		
	}

	public void prosseguir() {
		
	}

	public void suspender() {
		
	}

	public void rodando( CPU cpu ) {
		estado = ESTADO_EXECUTANDO;
		espera( 1 );
		tempoUCP--;
		estado = ESTADO_ESPERANDO;

		if( tipoProcesso >= PROCESSO_IO_BOUND_DISCO  ){
			estado = ESTADO_ESPERANDO_E_S;
		}
		
		if( tempoUCP <= 0 ){
			estado = ESTADO_ENCERRADO;	
		}
	}
	
	public void entrada( EntradaESaida es ) {
		if( estado == ESTADO_ESPERANDO_E_S ){
			espera( 1 );	
		}
		es.novoTempoEsperaRetorno();
		estado = ESTADO_EM_E_S;
	}
	
	public void saida( EntradaESaida es ) {
		if( estado == ESTADO_EM_E_S ){
			estado = ESTADO_FIM_E_S;
		}
	}
	
	private void espera( int segundo ){
		try{
			Thread.sleep( segundo * 1000 );
		} catch (Exception e) {}
	}
	
}
