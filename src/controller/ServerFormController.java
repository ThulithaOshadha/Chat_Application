package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.ClientHand;
import model.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Timer;
import Thread.Flusher;

public class ServerFormController {
    public JFXTextField txtUserName;

    Stage stage;
    Server server;
    Thread mainThread;
    Socket socket;

    public static HashMap<Integer, DataOutputStream> clients = new HashMap<>();

    public void initialize() throws IOException {

        server = new Server(8080, 5);

        mainThread = new Thread(() -> {
            // always waits for a client's request
            while (true) {
                try {
                    socket = server.accept();
                    Timer timer = new Timer();
                    timer.schedule(new Flusher(new DataInputStream(socket.getInputStream()),timer),0,2000);
                    clients.put(socket.getPort(), new DataOutputStream(socket.getOutputStream()));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        mainThread.start();

        Platform.runLater(() -> {
            stage.setOnCloseRequest(e -> {
                mainThread.interrupt();

                System.exit(0);
            });
        });
    }


    public void loginOnAction(ActionEvent actionEvent) throws IOException {

        Stage clientStage = new Stage(StageStyle.DECORATED);
        FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource("view/ClientForm.fxml"));
        Scene client = new Scene(loader.load());

        clientStage.setScene(client);
        clientStage.setTitle("User : " + txtUserName.getText());
        clientStage.setResizable(false);
        clientStage.sizeToScene();

        // passing data via the controller
        try {
            ClientFormController controller = loader.getController();
            controller.initData(txtUserName.getText(),clientStage);
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }

        clientStage.show();
        txtUserName.clear();
    }

    public void initData (Stage stage) {
        this.stage = stage;
    }

    public void loginFromEnterOnAction(KeyEvent keyEvent) throws IOException {

        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            Stage clientStage = new Stage(StageStyle.DECORATED);
            FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource("view/ClientForm.fxml"));
            Scene client = new Scene(loader.load());

            clientStage.setScene(client);
            clientStage.setTitle("User : " + txtUserName.getText());
            clientStage.setResizable(false);
            clientStage.sizeToScene();

            // passing data via the controller
            try {
                ClientFormController controller = loader.getController();
                controller.initData(txtUserName.getText(),clientStage);
            }catch (NullPointerException e){
                System.out.println(e.getMessage());
            }

            clientStage.show();
            txtUserName.clear();
            }
    }

}


/*public void startServer(){
        try {
            while (!serverSocket.isClosed()){
                socket = serverSocket.accept();
                System.out.println("A client has joined");
                ClientHand clientHandler = new ClientHand(socket);

                Thread thread = new Thread((Runnable) clientHandler);
                thread.start();
            }
        }catch (IOException e){

        }
    }

    public void closeServerSocket(){
        try {
            if (serverSocket != null){
                serverSocket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }*/
