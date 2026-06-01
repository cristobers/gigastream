package gigastream.auth;

import java.nio.file.Path;

/*
Before the stream starts, make sure that:
    * The old streams are removed
    * There's a way to view the stream that doesn't require the stream key.
*/
public class PreStream {

    private Path pathToStream;

    public void setPath(Path p) {
        pathToStream = p;
    }

    // Runs after a key has been verified, but before the stream starts.
    public void setUp(String streamKey) {
        Cleanup.removeOldStreams(pathToStream);
        Cleanup.symlinkStream(pathToStream, streamKey);
    }
}
