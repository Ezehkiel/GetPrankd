import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

/**
 * This class is responsible for the communication with the SMTP server.
 */
public class SMTPClient {

    private Socket connection;
    private BufferedReader input;
    private PrintWriter output;
    private boolean requireAuthentication = false;

    public SMTPClient(String host, int port, Mail mail, String username, String password) {

        try {
            this.connection = new Socket(host, port);
            this.input = new BufferedReader(new InputStreamReader(this.connection.getInputStream(), StandardCharsets.UTF_8));
            this.output = new PrintWriter(new OutputStreamWriter(this.connection.getOutputStream(), StandardCharsets.UTF_8));

            /* First, the server sends its information (code 220) */
            checkResponseCode("220");

            /* The client sends an EHLO message */
            sendSimpleMessage("EHLO local");

            /* The server responds with acknowledgments (code 250).
             * The method updates requireAuthentication */
            checkResponseCode("250");

            /* If the server requires authentication, the clients sends the login information */
            if (this.requireAuthentication) {
                sendSimpleMessage("AUTH LOGIN");
                checkResponseCode("334");
                sendSimpleMessage(username);
                checkResponseCode("334");
                sendSimpleMessage(password);
                checkResponseCode("235");
            }

            /* The client continues with MAIL FROM: */
            sendMailFrom(mail.getFrom());

            /* The server responds with an acknowledgment (code 250) */
            checkResponseCode("250");

            /* The client continues with RCPT TO:
            *  This function checks response codes. */
            sendRcptTo(mail.getTo());

            /* The client continues with DATA */
            sendSimpleMessage("DATA");

            /* The server responds with an acknowledgment (code 354) */
            checkResponseCode("354");

            /* The client then sends the message */
            sendMessage(mail.getFrom(), mail.getTo(), mail.getMessage().getMessage());

            /* The client then sends a QUIT */
            sendSimpleMessage("QUIT");

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

                    if (response[0].equals("250-AUTH")) {
                        this.requireAuthentication = true;
                    }

                } else {
                    throw new RuntimeException("The SMTP server respond with an error");
                }

            } while (!response[0].equals(code));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMailFrom(Victim mailFrom) {
        this.output.println("MAIL FROM: <" + mailFrom.getMailAddress() + ">");
        System.out.println("MAIL FROM: <" + mailFrom.getMailAddress() + ">");
        this.output.flush();
    }

    private void sendRcptTo(List<Victim> rcptTo) {
        for (Victim rcpt : rcptTo) {
            this.output.println("RCPT TO: <" + rcpt.getMailAddress() + ">");
            System.out.println("RCPT TO: <" + rcpt.getMailAddress() + ">");
            this.output.flush();

            /* The server repond with an acknowledgment (code 250) */
            checkResponseCode("250");
        }
    }

    private void sendSimpleMessage(String message) {
        this.output.println(message);
        System.out.println(message);
        this.output.flush();
    }

    private void sendMessage(Victim from, List<Victim> to, String message) {
        this.output.println("From: " + from.getMailAddress());
        System.out.println("From: " + from.getMailAddress());
        this.output.flush();


        for (int j = 0; j < to.size(); ++j) {
            if (j == 0) {
                this.output.print("To: " + to.get(j).getMailAddress());
                System.out.print("To: " + to.get(j).getMailAddress());
            } else {
                this.output.print(", " + to.get(j).getMailAddress());
                System.out.print(", " + to.get(j).getMailAddress());
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
