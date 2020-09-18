package name.mozzhechkov.cloc;

import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathCloc implements Cloc {

    private static final Logger LOG = LoggerFactory.getLogger(PathCloc.class);

    private final Path tmp;

    private final Path target;

    public PathCloc(final Path tmp, final Path target) {
        this.tmp = tmp;
        this.target = target;
    }

    @Override
    public ClocOutput run() throws IOException {
        final String run = this.run(this.tmp, this.target.toString());
        return new ClocOutput(run);
    }

    private String run(Path path, String... args) throws IOException {
        final Path stdout = path.resolve(
            String.format("%s-stdout.txt", UUID.randomUUID().toString())
        );
        final List<String> command = ImmutableList.<String>builder()
            .add("cloc-1.84.exe")
            .add(args)
            .build();
        LOG.info("Command:\n" + String.join(" ", command));
        final Process process = new ProcessBuilder()
            .directory(path.toFile())
            .command(command)
            .redirectOutput(stdout.toFile())
            .redirectErrorStream(true)
            .start();
        final int code;
        try {
            code = process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to wait ", e);
        }
        final String log = new String(Files.readAllBytes(stdout));
        LOG.info("Full stdout/stderr:\n" + log);
        if (code != 0) {
            throw new IllegalStateException(String.format("Not OK exit code: %d", code));
        }
        return log;
    }
}
