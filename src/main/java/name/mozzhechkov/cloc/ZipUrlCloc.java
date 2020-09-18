package name.mozzhechkov.cloc;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipUrlCloc implements Cloc {

    private static final Logger LOG = LoggerFactory.getLogger(ZipUrlCloc.class);

    private final Path tmp;

    private final URL url;

    public ZipUrlCloc(final Path tmp, final URL url) {
        this.tmp = tmp;
        this.url = url;
    }

    @Override
    public ClocOutput run() throws IOException {
        final Path zip = tmp.resolve("target.zip");
        LOG.info("Downloading from: {} to {}", url, zip);
        try (InputStream in = url.openStream()) {
            Files.copy(in, zip);
        }
        final Path exploded = tmp.resolve("exploded");
        return new ZipCloc(tmp, zip, exploded).run();
    }
}
