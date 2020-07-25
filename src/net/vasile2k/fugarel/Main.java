package net.vasile2k.fugarel;

import java.util.Arrays;
import java.util.List;

public class Main {

    private static Runnable game = null;

    public static Runnable getCurrentGame(){
        return game;
    }

    public static void main(String[] args){

        List<String> argsList = Arrays.asList(args);

        // show help
        if(argsList.contains("-h") || argsList.contains("--help")) {
            System.out.println("Command line options:");
            System.out.println("\t-h, --help - Show this message");
            System.out.println("\t-s, --server - Start game server");
            System.out.println("\t-p, --port - Tell the server what port to use, defaults to 669");
            System.out.println("\tno arguments - Start client");
        }

        // start game
        if(argsList.contains("-s") || argsList.contains("--server")){
            int index = argsList.indexOf("-p");
            if(index == -1){
                index = argsList.indexOf("--port");
            }
            if(index != -1){
                if(index + 1 < argsList.size()){
                    String portString = argsList.get(index + 1);
                    try{
                        int port = Integer.parseInt(portString);
                        game = new GameServer(port);
                    }catch (NumberFormatException ex){
                        System.err.println("Please provide a valid port number");
                        System.exit(-1);
                    }
                }else{
                    System.err.println("Please specify the port!");
                    System.exit(-1);
                }
            }else{
                game = new GameServer();
            }
        }else{
            game = new GameClient();
        }

        game.run();
    }

}
