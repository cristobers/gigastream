package gigastream.auth;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Cleanup {

    public static void removeOldStreams(Path streamPath) {
        try (Stream<Path> paths = Files.walk(Paths.get(streamPath.toString()))) {
            paths
                .filter(Files::isRegularFile)
                .filter(
                    f -> (f.getFileName().toString().contains(".m3u8"))
                    &&   (!f.getFileName().toString().equals("stream.m3u8"))
                )
                .forEach(Cleanup::deleteFile);
        } catch (IOException e) {
            System.out.println("Failed to access starting file.");
        }
    }

    private static void deleteFile(Path pathToOldStream) {
        System.out.println("[CLEANUP]: Removing " + pathToOldStream.getFileName().toString());
        pathToOldStream.toFile().delete();
    }

    public static void symlinkStream(Path p, String streamKey) {
        // .m3u8 should exist at <streamKey>.m3u8
        Path stream = Paths.get(p.toString() + "/" + streamKey + ".m3u8");
        Path symlinkPath = Paths.get(p.toString() + "/" + "stream.m3u8");
        
        if (symlinkPath.toFile().exists()) {
            return;
        }

        try {
            Files.createSymbolicLink(symlinkPath, stream);
        } catch (FileAlreadyExistsException e) {
            return;
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
