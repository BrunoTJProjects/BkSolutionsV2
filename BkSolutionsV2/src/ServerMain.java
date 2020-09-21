import java.io.IOException;

import br.com.bksolutionsdomotica.conexao.MyServerBk;

public class ServerMain {

	public static void main(String[] args) throws InterruptedException, IOException {
		
		
		@SuppressWarnings("unused")
		MyServerBk myServerBk = new MyServerBk(58365);
		
//		ServidorBkSolutions servidor = new ServidorBkSolutions(58365);
//		
//		servidor.init();
		
//		Scanner scan = new Scanner(System.in);
//		String comando = "";
//		while(!comando.equals("fim")) {
//			comando = scan.nextLine();
//			//comunicacaoServidor.enviarComando(comando);
//		}

//		for(int i = 0; i < 10; i++) {
//			Thread.sleep(2000);
//			if(i == 9) {
//				System.out.println(i);
//				servidorBK.closeServer();
//			}
//		}
		
	}

}
