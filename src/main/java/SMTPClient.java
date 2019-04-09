import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class SMTPClient {

    private Socket connection;
    private BufferedReader input;
    private PrintWriter output;

    public SMTPClient(String host, int port, Mail mail) {

        /*
        this.connection = new Socket(host, port);
        this.input = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
        this.output = new PrintWriter(this.connection.getOutputStream());
*/
        //this.output.println("EHLO local");
        System.out.println("EHLO local");
        //this.output.flush();

        //this.output.println("MAIL FROM: " + mail.getFrom().getMailAddress());
        System.out.println("MAIL FROM: " + mail.getFrom().getMailAddress());
        //this.output.flush();

        for (Victim rcpt : mail.getTo()) {
            //this.output.println("RCPT TO: " + rcpt.getMailAddress());
            System.out.println("RCPT TO: " + rcpt.getMailAddress());
            //this.output.flush();

        }

        //this.output.println("DATA");
        System.out.println("DATA");
        //this.output.flush();

        //this.output.println("From: " + mail.getFrom().getMailAddress());
        System.out.println("From: " + mail.getFrom().getMailAddress());
        //this.output.flush();


        for (int j = 0; j < mail.getTo().size(); ++j) {
            if (j == 0) {
                //this.output.print("To: " + mail.getTo().get(j).getMailAddress());
                System.out.print("To: " + mail.getTo().get(j).getMailAddress());
            } else {
                //this.output.print(", " + mail.getTo().get(j).getMailAddress());
                System.out.print(", " + mail.getTo().get(j).getMailAddress());
            }
            //this.output.flush();
        }
        //this.output.println();
        //this.output.flush();
        System.out.println();

        Scanner scanner = new Scanner(mail.getMessage().getMessage());
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            //this.output.println(line);
            System.out.println(line);
            //this.output.flush();
        }
        scanner.close();


        //this.input.close();
        //this.output.flush();
        //this.output.close();
    }
}
