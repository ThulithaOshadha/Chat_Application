package controller;

import Thread.ListenerThread;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Client;
import org.apache.commons.lang.ArrayUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Timer;

import static org.apache.commons.lang.ArrayUtils.addAll;
import static org.apache.commons.lang.ArrayUtils.*;


public class ClientFormController {
    public TextField txtMsgField;
    public JFXButton btnMsgSend;
    public JFXTextArea msgArea;
    public ScrollPane txtScrollPane;
    public VBox vBox;

    Client client;
    String clientName;
    ListenerThread listener;
    Stage stage;

    // file handling
    FileChooser fileChooser;

    // data handling
    byte[] payload;
    byte[] header;

    //int mouseCounter = 0;

    public void initialize() throws IOException {

        Platform.runLater(() -> {
            // add a listener for scrollbar to be at the end
            vBox.heightProperty().addListener(observable -> txtScrollPane.setVvalue(1D));
            stage.setOnCloseRequest(e -> {
                listener.stop();
            });
        });

        Connect();

    }

    private void Connect() throws IOException {
        client = new Client(clientName,"localhost",8080);

        Timer timer = new Timer();
        fileChooser = new FileChooser();


        try {
            listener = new ListenerThread(new DataInputStream(client.getInputStream()),vBox,timer);
            timer.schedule(listener,0,1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean sendMsg(String clientName) throws IOException, InterruptedException {


        if (!txtMsgField.getText().equals("")){
            String msg = clientName + "  :  " + txtMsgField.getText();
            //System.out.println(msg);
            payload = msg.getBytes(StandardCharsets.UTF_16);

            int len = payload.length;

            header = ByteBuffer.allocate(4).putInt(len).array();
            byte[] frame = addAll(header,payload);

            client.getDataOutputStream().write(0);
            client.getDataOutputStream().write(frame);
            client.getDataOutputStream().flush();

            return true;
        } else return false;


    }

    public void initData(String name, Stage stage) {
        this.stage = stage;
        this.clientName = name;
    }

    public void sendOnAction(ActionEvent actionEvent) throws IOException, InterruptedException {
        if(sendMsg(clientName)){
            txtMsgField.clear();
        }
    }

    public void sendimageOnAction(MouseEvent mouseEvent) throws IOException {

    }

    public void sendFromEnter(KeyEvent keyEvent) throws IOException, InterruptedException {
        if(keyEvent.getCode().equals(KeyCode.ENTER)) {
            if (sendMsg(clientName)) {
                txtMsgField.clear();
            }
        }
    }









    /*Socket socket;
    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;
    String userName;

    public void initialize() throws IOException {
        userName = "thuli";
        socket = new Socket("localhost",1234);
        ClientHand clientHand = new ClientHand(socket,userName);
        listenChat();
        sendOnAction(userName);

    }


    public void sendOnAction(String userName) {
        try {
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            while (socket.isConnected()){
                String msg = txtMsgField.getText().trim();
                bufferedWriter.write(userName + " : " + msg);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenChat(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                while (socket.isConnected()){
                    try {
                        msgFromGroupChat = bufferedReader.readLine();
                        msgArea.appendText(msgFromGroupChat);
                    }catch (IOException e){
                        closeEverything(socket,bufferedWriter,bufferedReader);
                    }
                }

            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        try {
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (socket != null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }*/
}
