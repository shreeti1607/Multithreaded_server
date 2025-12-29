
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server{
       public Consumer<Socket> getConsumer(){
        // return new Consumer<Socket>(){
        //     @Override
        //     public void accept(Socket clientSocket){
        //        try {
        //         PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
        //         toClient.println("Hello, from server!");
        //         toClient.close();
        //         clientSocket.close();
        //         } catch (Exception e) {
        //         }
        //     }
        //  };
             return (clientSocket)->{
                try {
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                toClient.println("Hello, from server!");
                toClient.close();
                clientSocket.close();
                } catch (Exception e) {
                }
             };
       }


        public static void main(String[] args) {
        int port = 8080; // Default port
        Server server = new Server();
        try {
           ServerSocket serverSocket = new ServerSocket(port);
           serverSocket.setSoTimeout(100000);
           System.out.println("Server started on port " + port);
           while (true) {
              Socket acceptedSocket = serverSocket.accept();
              Thread thread = new Thread(()->server.getConsumer().accept(acceptedSocket));
              thread.start();
           }    

        } catch (Exception e) {
           e.printStackTrace();    
         }
    }
}