package gerenciadores;

public class EntradaESaida extends Thread {
	
	private int tempoEsperaRetorno = 10;
	
	private static boolean finaliza = false;
	
	private Processo proc = null;
	
	public void run() {
		while( !finaliza ){
			if( proc != null ){
				if( proc.getEstado() == Processo.ESTADO_EM_E_S ){
					if( tempoEsperaRetorno <= 0 ){
						proc.saida( this );
						proc = null;
					}
					tempoEsperaRetorno --;
					
				} else {
					proc.entrada( this );
				}
			}
			
		}
		super.interrupt();
	}
	
	public void interrupt() {
		finaliza = true;
	}
	
	public boolean isProcessando(){
		return proc != null; 
	}
	
	public void setProc(Processo proc) {
		this.proc = proc;
	}
	
	public void novoTempoEsperaRetorno(){
		tempoEsperaRetorno = 10;
	}
	
	private void espera(){
		try {
			Thread.sleep( 100 );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
