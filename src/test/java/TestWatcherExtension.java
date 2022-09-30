import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestWatcherExtension implements TestWatcher, BeforeEachCallback {

    private List<Path> listOfVideoFiles = new ArrayList<>();

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        try {
            listOfVideoFiles = Files.walk(Paths.get("videos/")).filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Failed to fetch videos. Error message was " + e.getMessage());
        }
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        deleteTraces(context);
        deleteVideos();
    }

    private void deleteTraces(ExtensionContext context) {
        Path tracePath = Paths.get("traces/" + context.getDisplayName().replace("()", "") + ".zip");
        try {
            Files.deleteIfExists(tracePath);
        } catch (IOException e) {
            System.out.println("Failed to delete trace file with name " + tracePath.getFileName());
        }
    }

    private void deleteVideos() {
        try {
            List<Path> newVideos = Files.walk(Paths.get("videos/")).filter(Files::isRegularFile)
                    .filter(el -> !listOfVideoFiles.contains(el)).collect(Collectors.toList());
            for(Path path : newVideos) {
                Files.deleteIfExists(path);
            }
        } catch (IOException e) {
            System.out.println("Failed to delete videos. Error message was " + e.getMessage());
        }
    }


}
