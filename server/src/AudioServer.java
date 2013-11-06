import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class AudioServer implements Runnable {

	DatagramSocket socket;
	DatagramPacket packet;// 从客户端接收到的UDP包
	DatagramPacket sendPkt;// 转发给另一个客户端的UDP包

	byte[] pktBuffer = new byte[1024];
	int bufferSize = 1024;
	boolean isRunning = false;
	int myport = 5656;

	// ///////////
	String clientIpStr = "192.168.1.103";
	InetAddress clientIp;
	int clientPort = 5757;

	public AudioServer() {
		try {
			clientIp = InetAddress.getByName(clientIpStr);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		try {
			socket = new DatagramSocket(myport);
			packet = new DatagramPacket(pktBuffer, bufferSize);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		System.out.println("服务器初始化完成 ");
	}

	public void startServer() {
		this.isRunning = true;
		new Thread(this).start();
	}

	public void run() {
		try {
			while (isRunning) {
				socket.receive(packet);
				System.out.println("收到一段  " + packet.getLength() + " "
						+ packet.getAddress());
				sendPkt = new DatagramPacket(packet.getData(),
						packet.getLength(), clientIp, clientPort);
				socket.send(sendPkt);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
		}
	}

	// main
	public static void main(String[] args) {
		new AudioServer().startServer();
	}
}
