import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SMTPClient {

    private Socket connection;
    private BufferedReader input;
    private PrintWriter output;

    public SMTPClient(String host, int port, Mail mail) {

        try {
            this.connection = new Socket(host, port);
            this.input = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
            this.output = new PrintWriter(this.connection.getOutputStream());

            // impl

            this.input.close();
            this.output.flush();
            this.output.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
