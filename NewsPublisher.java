package com.company.newsPortal;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;


public class NewsPublisher implements Runnable {


    List<Subscriber> subscribers = new ArrayList<>();

    private static String NEWS_PATH = "src\\com\\company\\newsPortal\\news";

    //subscribe
    public void subscribe(Subscriber subscriber) {
        System.out.println("Added subscriber " + subscriber.getName());
        //call the database and run a sql query to insert this observer
        subscribers.add(subscriber);
    }

    //unsubscribe
    public void unsubscribe(Subscriber subscriber) {

        //call the database and run a sql query to insert this observer
        subscribers.remove(subscriber);
    }

    @Override
    public void run() {
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(NEWS_PATH);
            path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);

            while (true) {
                WatchKey watchKey = watcher.poll();
                if (watchKey != null) {
                    List<WatchEvent<?>> events = watchKey.pollEvents();
                    System.out.println("number of events: " + events.size());
                    WatchEvent event = events.get(0);

                    String fileName= event.context().toString();
                    System.out.println(fileName);
                    // Wait sometime until the file is fully saved into the file system
                    Thread.sleep(10);
                    FileReader fileReader=new FileReader(NEWS_PATH+"\\"+fileName);
                    int i;
                    String content = "";
                    while((i=fileReader.read())!=-1)
                        content+=(char)i;
                    System.out.println(content);
                    notifySubscribers(content);
                    fileReader.close();
                    watchKey.reset();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public void notifySubscribers(String news) {
        System.out.println("Number of susbs:" +subscribers.size());
        for (Subscriber subscriber : subscribers) {
            subscriber.notifySubbscriber(news);
            System.out.println(subscriber.getName());
        }
    }
}



