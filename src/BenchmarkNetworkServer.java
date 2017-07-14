import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BenchmarkNetworkServer implements Runnable {
	private ServerSocket serverSocket = null;

	public BenchmarkNetworkServer() throws InterruptedException{
		try {
			serverSocket = new ServerSocket(5001);
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
		System.out.println("TCP Server Started");
		System.out.println("Started to accept client connections...");
		Thread[] isThread = new Thread[2];
		for(int i=0; i<2; i++){
			isThread[i] = new Thread(this);
			isThread[i].start();
			Thread.sleep(1000);
		}
	}

	public void run(){
		Socket clientSocket = null;

		try{
//						while(!serverSocket.isClosed()){
			clientSocket = serverSocket.accept();
			int[] fileBytes = {1,1024,65507};
			for(int fileSizes=0 ; fileSizes<fileBytes.length ; fileSizes++){
				DataInputStream in = new DataInputStream(clientSocket.getInputStream());
				DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

				byte[] buf = new byte[fileBytes[fileSizes]];
				in.read(buf);					
				out.write(buf);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			new BenchmarkNetworkServer();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}