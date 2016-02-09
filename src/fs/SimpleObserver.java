package fs;

import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by zem on 05/02/16.
 */
public class SimpleObserver implements Observer {
    @Override
    public void fileCreated(Path path) {
        Logger.getLogger(SimpleObserver.class.getCanonicalName()).log(Level.INFO, "File " + path.toAbsolutePath() + " created");
    }

    @Override
    public void fileModified(Path path) {
        Logger.getLogger(SimpleObserver.class.getCanonicalName()).log(Level.INFO, "File " + path.toAbsolutePath() + " modified");
    }

    @Override
    public void fileDeleted(Path path) {
        Logger.getLogger(SimpleObserver.class.getCanonicalName()).log(Level.INFO, "File " + path.toAbsolutePath() + " deleted");
    }

    @Override
    public void directoryCreated(Path path) {
        Logger.getLogger(SimpleObserver.class.getCanonicalName()).log(Level.INFO, "Dir " + path.toAbsolutePath() + " created");

    }

    @Override
    public void directoryModified(Path path) {
        Logger.getLogger(SimpleObserver.class.getCanonicalName()).log(Level.INFO, "Dir " + path.toAbsolutePath() + " modified");

    }

    @Override
    public void directoryDeleted(Path path) {
        Logger.getLogger(SimpleObserver.class.getCanonicalName()).log(Level.INFO, "Dir " + path.toAbsolutePath() + " deleted");
    }
}
