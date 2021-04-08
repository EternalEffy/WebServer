import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public class EFFY_WebServer implements Runnable {

    private Socket client;
    private String html;
    private ArrayList<String> fileNamesList = new ArrayList<>(Arrays.asList("index.html","first.html","second.html","third.html","fourth.html"));

    public EFFY_WebServer(Socket client) {
        this.client = client;
    }

    private void loadServer() {
        try{
            DataInputStream in = new DataInputStream(client.getInputStream());
            PrintWriter out = new PrintWriter(client.getOutputStream());
            byte a[]= new byte[in.available()];
            in.read(a);
            parse(out,new String(a));

            out.close();
            in.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void sendResponse(PrintWriter out,String fileName){

            BufferedInputStream reader = null;
            try {
                reader = new BufferedInputStream(new FileInputStream(fileName));
                byte b[] = new byte[reader.available()];
                reader.read(b);
                html = new String(b);
                reader.close();

                out.println("HTTP/1.1 200 OK");
                out.println("Content-type: text/html");
                out.println("\r\n");
                out.println(html);
                out.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void parse(PrintWriter out,String response){
            if (response.startsWith("GET /first.html HTTP/1.1")) {
               sendResponse(out, fileNamesList.get(1));
           }
            else if (response.startsWith("GET /second.html HTTP/1.1")) {
               sendResponse(out, fileNamesList.get(2));
           }
            else if (response.startsWith("GET /third.html HTTP/1.1")) {
               sendResponse(out, fileNamesList.get(3));
           }
            else if (response.startsWith("GET /fourth.html HTTP/1.1")) {
               sendResponse(out, fileNamesList.get(4));
           }
            else {
            sendResponse(out, fileNamesList.get(0));
        }
    }

    @Override
    public void run() {
        loadServer();
    }
}
