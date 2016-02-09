package fs;

import java.nio.file.Path;

/**
 * Created by zem on 05/02/16.
 */
public interface Observer {
    void fileCreated(Path path);

    void fileModified(Path path);

    void fileDeleted(Path path);

    void directoryCreated(Path path);

    void directoryModified(Path path);

    void directoryDeleted(Path path);
}