package p09_javanet;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatCliente implements Runnable {

    @Override
    public void run() {
        try {
            Scanner consoleIn = new Scanner(System.in);

            //o usuário deve informar, pelo teclado, o endereço IP
            //que será usado na conexão
            System.out.println("[C] Endereço IP: ");
            String address = consoleIn.nextLine();

            //o usuário deve informar, pelo teclado, a porta para fazer a conexão
            System.out.println("[C] Porta: ");
            int port = Integer.parseInt(consoleIn.nextLine());

            System.out.println("[C] Conectando...");

            //um objeto da classe Socket é criado
            //ele serve para criar o "socket" (tomada) entre cliente e servidor
            //imediatamente ele tenta realizar a conexão
            Socket socket = new Socket(address, port);
            System.out.println("[C] Conectado na porta " + port + " do PC " + address);

            //conexão estabelecida!
            //significa que há:
            //um "input stream" (fluxo de entrada), onde os dados de outro PC chegarão
            //um "output stream" (fluxo de saída), onde os dados serão enviados para outro PC
            //para o Scanner e o PrintWriter, não interessa onde a mensagem está
            //ela será entregue para a parte correspondente
            //objeto da classe Scanner para pegar dado pelo teclado através do socket
            Scanner socketIn = new Scanner(socket.getInputStream());

            //objeto da classe PrintWriter para imprimir dado na tela do PC através do socket
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("[C] Entrada e saída de dados estabilizada...");

            //loop para ler mensagem pelo teclado, enviá-la pra o servidor, 
            //ler a resposta do servidor e imprimi-la na tela do PC
            //o loop só para quando a mensagem for vazia (número de caracteres igual a zero)
            boolean terminado = false;
            do {
                //o cliente cria uma mensagem
                System.out.print("Cliente diz: ");
                String message = consoleIn.nextLine();

                //se a mensagem for vazia, o programa finaliza a conversa
                if (message.length() == 0) {
                    System.out.println("[C] Finalizando conversa...");
                    socketOut.println("");
                    terminado = true;
                }

                if (!terminado) {
                    //envie a mensagem do cliente para o servidor
                    socketOut.println(message);

	    //a execução do programa é suspensa, 
                    //fica esperando até que a mensagem do servidor chegue
                    String response = socketIn.nextLine();

	   //se a mensagem não for vazia, 
                    //o programa imprime a mensagem que chegou do servidor
                    //senão, a conversa finaliza
                    if (response.length() != 0) {
                        System.out.println("[C] Mensagem do servidor: " + response);
                    } else {
                        System.out.println("[C] Conversa finalizada pelo servidor...");
                        terminado = true;
                    }
                }
            } while (!terminado);

            System.out.println("[C] Fechando conexão...");

            //fecha o socket
            socket.close();
        } catch (Exception e) {
            System.err.println("Erro! " + e.getMessage());
            System.exit(0);
        }

    }

}
