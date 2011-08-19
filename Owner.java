import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Owner {
	public static void main(String args[]) {
		int Port = 3333;
		try {
			while(true) {
				int port = Port;
				DatagramSocket dsocket = new DatagramSocket(port);
				byte[] buffer = new byte[8024];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				while (true) {
					dsocket.receive(packet);
					String msg = new String(buffer, 0, packet.getLength());
					System.out.println(msg);
					packet.setLength(buffer.length);
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}