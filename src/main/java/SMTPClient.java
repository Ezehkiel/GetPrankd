import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

/**
 * This class is responsible for the communication with the SMTP server.
 */
public class SMTPClient {

    private Socket connection;
    private BufferedReader input;
    private PrintWriter output;

    public SMTPClient(String host, int port, Mail mail) {

        try {
            this.connection = new Socket(host, port);
            this.input = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
            this.output = new PrintWriter(this.connection.getOutputStream());

            /* First, the server sends its information (code 220) */
            checkResponseCode("220");

            /* The client sends an EHLO message */
            sendEhlo("local");

            /* The server repond with acknowledgments (code 250) */
            checkResponseCode("250");

            /* The client continues with MAIL FROM: */
            sendMailFrom(mail.getFrom());

            /* The server repond with an acknowledgment (code 250) */
            checkResponseCode("250");

            /* The client continues with RCPT TO: */
            sendRcptTo(mail.getTo());

            /* The client continues with DATA */
            sendData();

            /* The server repond with an acknowledgment (code 354) */
            checkResponseCode("354");

            /* The client then sends the message */
            sendMessage(mail.getFrom(), mail.getTo(), mail.getMessage().getMessage());

            this.input.close();
            this.output.flush();
            this.output.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void checkResponseCode(String code) {
        String[] response;
        String line;
        try {
            do {
                line = this.input.readLine();
                if (line != null) {
                    System.out.println(line);
                    response = line.split(" ");
                } else {
                    throw new RuntimeException("The SMTP server respond with an error");
                }

            } while (!response[0].equals(code));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendEhlo(String ehloMessage) {
        this.output.println("EHLO " + ehloMessage);
        System.out.println("EHLO " + ehloMessage);
        this.output.flush();
    }

    private void sendMailFrom(Victim mailFrom) {
        this.output.println("MAIL FROM: " + mailFrom.getMailAddress());
        System.out.println("MAIL FROM: " + mailFrom.getMailAddress());
        this.output.flush();
    }

    private void sendRcptTo(List<Victim> rcptTo) {
        for(Victim rcpt : rcptTo) {
            this.output.println("RCPT TO: " + rcpt.getMailAddress());
            System.out.println("RCPT TO: " + rcpt.getMailAddress());
            this.output.flush();

            /* The server repond with an acknowledgment (code 250) */
            checkResponseCode("250");
        }
    }

    private void sendData() {
        this.output.println("DATA");
        System.out.println("DATA");
        this.output.flush();
    }

    private void sendMessage(Victim from, List<Victim> tos, String message) {
        this.output.println("From: " + from.getMailAddress());
        System.out.println("From: " + from.getMailAddress());
        this.output.flush();


        for(int j = 0; j < tos.size(); ++j) {
            if (j == 0) {
                this.output.print("To: " + tos.get(j).getMailAddress());
                System.out.print("To: " + tos.get(j).getMailAddress());
            } else {
                this.output.print(", " + tos.get(j).getMailAddress());
                System.out.print(", " + tos.get(j).getMailAddress());
            }
            this.output.flush();
        }
        this.output.println();
        this.output.flush();
        System.out.println();

        Scanner scanner = new Scanner(message);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            this.output.println(line);
            System.out.println(line);
            this.output.flush();
        }
        scanner.close();

        this.output.println(".");
        this.output.flush();
    }
}
