package p09_javanet;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServidor implements Runnable {

    @Override
    public void run() {
        try {
            Scanner consoleIn = new Scanner(System.in);

            //o usuário deve informar, pelo teclado, a porta que será usada na conexão
            System.out.print("[S] Porta para escutar: ");
            int port = Integer.parseInt(consoleIn.nextLine());

            //um objeto da classe ServerSocket é criado
            //fica aguardando pela conexão do cliente
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("[S] Esperando pela conexão...");
           
            //o método accept() é chamado para suspender a execução do programa
            //até que a conexão com o cliente seja estabelecida
            Socket socket = serverSocket.accept();
            
            //depois que o cliente entra em contato com o servidor, a conexão é feita
            System.out.println("[S] Conexão aceita!");

            //objeto da classe Scanner para pegar dado pelo teclado através do socket
            Scanner socketIn = new Scanner(socket.getInputStream());

            //objeto da classe PrintWriter para imprimir dado na tela do PC através do socket
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);

            boolean terminado = false;
            do {
                //pega a mensagem enviada pelo cliente
                String message = socketIn.nextLine();

	//se a mensagem for vazia, então a conexão é finalizada
                //senão, o programa mostra a mensagem na tela do servidor
                if (message.length() == 0) {
                    System.out.println("[S] Finalizando conversa...");
                    terminado = true;
                } else {
                    System.out.println("[S] Mensagem do cliente: " + message);
                }

                if (!terminado) {
                    //o servidor cria uma mensagem
                    System.out.print("Servidor diz: ");
                    String response = consoleIn.nextLine();

					//se a mensagem não for vazia, então ela é enviada para o cliente
                    //senão, o programa finaliza a conversa
                    if (response.length() != 0) {
                        socketOut.println(response);
                    } else {
                        System.out.println("[S] Conversa finalizada pelo cliente...");
                        socketOut.println("");
                        terminado = true;
                    }
                }
            } while (!terminado);

            System.out.println("[S] Fechando conexão");

            //fecha o socket
            socket.close();
        } catch (Exception e) {
            System.out.println("Erro! " + e.getMessage());
            System.exit(0);
        }

    }

}
