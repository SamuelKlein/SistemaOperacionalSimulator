package gerenciadores;

public class CPU extends Thread {
	
	
	
	private static boolean finaliza = false;
	
	private Processo proc = null;
	
	public void run() {
		while( !finaliza ){
			if( proc != null ){
				try{
					if( proc.getEstado() == Processo.ESTADO_NOVO ){
						CtlMemoria.envia( proc );
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				proc.rodando( this );
				
				if( proc.getEstado() == Processo.ESTADO_ENCERRADO || proc.getEstado() == Processo.ESTADO_ESPERANDO || proc.getEstado() == Processo.ESTADO_ESPERANDO_E_S ) { 
					proc = null;
				}
			}
			espera();
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
	
	private void espera(){
		try {
			Thread.sleep( 100 );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
