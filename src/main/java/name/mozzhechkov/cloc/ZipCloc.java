package name.mozzhechkov.cloc;

import java.io.IOException;
import java.nio.file.Path;
import net.lingala.zip4j.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipCloc implements Cloc {

    private static final Logger LOG = LoggerFactory.getLogger(ZipCloc.class);

    private final Path tmp;

    private final Path zip;

    private final Path exploded;

    public ZipCloc(final Path tmp, final Path zip, final Path exploded) {
        this.tmp = tmp;
        this.zip = zip;
        this.exploded = exploded;
    }

    @Override
    public ClocOutput run() throws IOException {
        LOG.info("Unzipping: {} to {}", zip, exploded);
        ZipFile zipFile = new ZipFile(zip.toFile());
        zipFile.extractAll(exploded.toString());
        return new PathCloc(tmp, exploded).run();
    }
}
