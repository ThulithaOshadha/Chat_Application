package controller;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/*public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlerArrayList = new ArrayList<>();
    public Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientName;

    public ClientHandler(Socket socket,String clientName){
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientName = bufferedReader.readLine();
            clientHandlerArrayList.add(this);
            broadCastmessage("Server "+ clientName + "has entered t the chat");
        }catch (IOException e){
            closeEverything(socket,bufferedWriter,bufferedReader);
        }
    }

    public ClientHandler(Socket socket){
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientName = bufferedReader.readLine();
            clientHandlerArrayList.add(this);
            broadCastmessage("Server "+ clientName + "has entered t the chat");
        }catch (IOException e){
            closeEverything(socket,bufferedWriter,bufferedReader);
        }
    }



    publicvoid run() {
        String messageFormClient;

        while (socket.isConnected()){
            try {
                messageFormClient = bufferedReader.readLine();
                broadCastmessage(messageFormClient);
            }catch (IOException e){
                closeEverything(socket,bufferedWriter,bufferedReader);
                break;
            }
        }

    }

    public void broadCastmessage(String sendMessage) {
        for (ClientHandler clientHandler : clientHandlerArrayList) {
            try {
                if (!clientHandler.clientName.equals(clientName)){
                    clientHandler.bufferedWriter.write(sendMessage);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            }catch (IOException e){
                closeEverything(socket,bufferedWriter,bufferedReader);
            }
        }
    }

    public void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        removeClient();
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

    }

    public void removeClient(){
        clientHandlerArrayList.remove(this);
        broadCastmessage("Server " + clientName + "has left the chat");
    }
}*/
