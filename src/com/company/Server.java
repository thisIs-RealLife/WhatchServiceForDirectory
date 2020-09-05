package com.company;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

public class Server implements Runnable{

    private boolean canWatch;
    private Thread self;
    private ArrayList<Client> clients ;
    private String directory;

    public Server(String directory){
        self = new Thread(this, "ServerThread");
        this.directory = directory;
        clients = new ArrayList<>();
    }

    public void start(){
        canWatch = true;
        self.start();
    }

    public void stop() throws InterruptedException{
        canWatch = false;
        self.interrupt();
    }

    @Override
    public void run() {

        try(WatchService watchService = FileSystems.getDefault().newWatchService()) {
            Paths.get(directory).register(watchService,StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,StandardWatchEventKinds.ENTRY_DELETE);

            while (canWatch){
                WatchKey watchKey =  watchService.take();
                for (WatchEvent event : watchKey.pollEvents()){
                    System.out.println("Event kind: " + event.kind()
                    + ". File affected: "+ event.context());
                }
                watchKey.reset();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
