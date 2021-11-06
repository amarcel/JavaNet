
package p09_javanet;

import java.util.Scanner;

public class P09_JavaNet {

    public static void main(String[] args) {
        System.out.println("Digite S para iniciar o servidor ou C para iniciar o cliente: ");
        Scanner sc = new Scanner(System.in);
        String escolha = sc.nextLine();
        switch(escolha) {
            case "C":
                //THREAD DO CLIENTE!
                ChatCliente cliente = new ChatCliente();
                Thread t1 = new Thread(cliente);
                t1.start();
            break;
            case "S":
                //THREAD DO SERVIDOR!
                ChatServidor servidor = new ChatServidor();
                Thread t2 = new Thread(servidor);
                t2.start();
            break;
            default:
                System.out.println("Opção inválida.");
        }
        
        
        
    }
    
}
