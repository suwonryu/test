import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class MyTcpServer {
    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(9090);
        System.out.println("server start");

        Socket socket = serverSocket.accept();
        System.out.println(socket);

        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        while (true) {
            int len = dataInputStream.readInt();
            System.out.println("len: " + len);
            byte[] b = new byte[len];
            dataInputStream.readFully(b);
            System.out.println("b: " + new BigInteger(1, b).toString(2));

            byte[] msg = "Hello".getBytes(StandardCharsets.UTF_8);
            dataOutputStream.writeInt(msg.length);
            dataOutputStream.write(msg);
            dataOutputStream.flush();
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
