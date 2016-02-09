import fs.FsWatcher;
import fs.SimpleObserver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        try {
            Path path = Paths.get("/Users/zem/Desktop/ttt/");
            FsWatcher fsWatcher = new FsWatcher(path);
            SimpleObserver simpleObserver = new SimpleObserver();
            fsWatcher.addObserver(simpleObserver);
            fsWatcher.watch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
