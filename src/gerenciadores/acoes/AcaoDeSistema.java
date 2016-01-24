package gerenciadores.acoes;

import java.awt.Color;
import java.util.Vector;

public interface AcaoDeSistema {
	
	public void tempo( long tempo );
	public void processos( int processo );
	public void memoria( int memoria );
	
	public void addMemoria( int pos, Color cor );
	public void removeMemoria( int pos );
	
	public void addProcesso( DadosProcesso dados );
	public void atualizaProcesso( DadosProcesso dados );
	public void removeProcesso( DadosProcesso dados );
	
	public void enviaFila( Vector<DadosProcesso> vcDados, int prioridade );
}
