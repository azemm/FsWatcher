package fs;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zem on 04/02/16.
 */
public class FsWatcher {

    private Path dir;
    private WatchService watcher;
    private List<Observer> observers;
    private ConcurrentHashMap<WatchKey, Path> keys;

    public FsWatcher(Path path) throws IOException {
        this.dir = path;
        observers = new ArrayList<Observer>();
        keys = new ConcurrentHashMap<WatchKey, Path>();
    }

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void clearObservers(){
        observers.clear();
    }

    private void notifyObservers(WatchEvent<Path> event, Path path){
        for (Observer observer: observers) {
            if(event.kind() == StandardWatchEventKinds.ENTRY_CREATE){
                if(Files.isDirectory(path)){
                    observer.directoryCreated(path);
                } else {
                    observer.fileCreated(path);
                }
            }  else if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                if(Files.isRegularFile(path)) {
                    observer.fileModified(path);
                }
            } else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                if(Files.isDirectory(path)){
                    observer.directoryDeleted(path);
                } else {
                    observer.fileDeleted(path);
                }
            }
        }
    }

    public void watch() throws IOException {
        watcher = FileSystems.getDefault().newWatchService();
        register(dir);
        registerAll(dir);
        WatchKey key = null;

        while(key == null || key.reset()){

            try {
                key = watcher.take();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(WatchEvent<?> event : key.pollEvents()){
                WatchEvent<Path> ev = (WatchEvent<Path>) event;

                Path parent = keys.get(key);
                Path path = parent.resolve(ev.context());

                if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
                    continue;
                }

                if(event.kind() == StandardWatchEventKinds.ENTRY_CREATE
                        && Files.isDirectory(path)){
                    register(path);
                }

                notifyObservers(ev, path);
            }
        }
    }

    private void register(Path path) throws IOException {
        WatchKey key = path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
        keys.put(key, path);
    }

    private void registerAll(final Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>(){

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException{
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}


