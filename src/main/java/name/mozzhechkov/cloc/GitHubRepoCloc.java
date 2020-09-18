package name.mozzhechkov.cloc;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

public class GitHubRepoCloc implements Cloc {

    private final Path tmp;

    private final String repo;

    public GitHubRepoCloc(final Path tmp, final String repo) {
        this.tmp = tmp;
        this.repo = repo;
    }

    @Override
    public ClocOutput run() throws IOException {
        final URL url = new URL(String.format("https://github.com/%s/archive/master.zip", repo));
        return new ZipUrlCloc(tmp, url).run();
    }
}
