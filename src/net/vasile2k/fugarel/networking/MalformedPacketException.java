package net.vasile2k.fugarel.networking;

public class MalformedPacketException extends Exception {

    public MalformedPacketException(){
        super();
    }

    public MalformedPacketException(String message){
        super(message);
    }

}
