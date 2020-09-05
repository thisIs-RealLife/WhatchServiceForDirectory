package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        boolean flag= true;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(Thread.currentThread().getName());
	    Server server = new Server(".");
	    server.start();
        System.out.println("робит");
	    while (flag){
            try {
                String s= bufferedReader.readLine();
                if (s.equals("stop")){
                    server.stop();
                    flag = false;
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
