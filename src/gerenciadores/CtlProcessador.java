package gerenciadores;

import gerenciadores.acoes.AcaoDeSistema;
import gerenciadores.acoes.DadosProcesso;

import java.util.HashMap;
import java.util.Vector;

public class CtlProcessador {
	
	private CPU cpu = null;
	
	private static boolean finaliza = false;
	
	private Vector<Processo> filaDeExecucao;
	private int prioridadeRodando = 0;
	
	private HashMap<Integer,Vector<Processo>> filaDePrioridades;
	
	private Vector<Integer> pids = new Vector<Integer>();

	private AcaoDeSistema interfaceSistema;
	
	public CtlProcessador( AcaoDeSistema interfaceSistema ){
		this.interfaceSistema = interfaceSistema;
		cpu = new CPU();
		cpu.start();
		filaDePrioridades = new HashMap<Integer, Vector<Processo>>();
		EnviaFila e = new EnviaFila();
		e.start();
	}
	
	public void envia( Processo processo ) {
		executarFilaExecucao( processo );
		pids.add( processo.getPid() );
	}

	public void alteraPrioridade( Processo processo, int prioridade ){
		
	}
	
	private void executarFilaExecucao( Processo proc ){
		
		Vector<Processo> fila = null;
		if( filaDePrioridades.containsKey( proc.getPrioridade() )){
			fila = filaDePrioridades.get( proc.getPrioridade() );
		} else {
			fila = new Vector<Processo>();
			filaDePrioridades.put( proc.getPrioridade(), fila );
		}

		fila.add( proc );	
	}
	
	private class EnviaFila extends Thread {
		
		public void run() {
			while( !finaliza ){
				relogio();
				if( !cpu.isProcessando() ){
					buscaPrioridade();
					if( filaDeExecucao != null && filaDeExecucao.size() > 0 ){
						Processo processo = filaDeExecucao.get( 0 );
						if( processo.getEstado() == Processo.ESTADO_NOVO || processo.getEstado() == Processo.ESTADO_FIM_E_S || processo.getEstado() == Processo.ESTADO_ESPERANDO ){
							if( processo != null ){
								cpu.setProc( processo );
							}
						}
						filaDeExecucao.remove( 0 );
						if( processo.getEstado() != Processo.ESTADO_ENCERRADO ){
							filaDeExecucao.add( processo );
						}
					}
				}
				enviaFila();
			}
		}
		
	}
	
	private void relogio(){
		try {
			Thread.sleep( 100 );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buscaPrioridade() {
		
		for (int i = 0; i < 12; i++) {
			
			if( filaDePrioridades.containsKey( i ) ){
				Vector<Processo> fila = filaDePrioridades.get( i );
				if( fila != null && fila.size() > 0 ){
					filaDeExecucao = fila;
					prioridadeRodando = i;
				}
			}
		}
	}

	public void enviaFila() {
		Vector<DadosProcesso> vc = new Vector<DadosProcesso>();
		if( filaDeExecucao != null ){
			for (int i = 0; i < filaDeExecucao.size(); i++) {
				vc.add( filaDeExecucao.get( i ) );
			}
			interfaceSistema.enviaFila( vc, this.prioridadeRodando );
		}
	}

	public void interrupt(){
		finaliza = true;
		cpu.interrupt();
	}
}
