package name.mozzhechkov.cloc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class Runner {

    public static void main(String[] args) throws Exception {
        //todo: get repos from org using API
        final List<String> artipie = Arrays.asList(
            "artipie/rpm-adapter",
            "artipie/go-adapter",
            "artipie/npm-adapter",
            "artipie/asto",
            "artipie/artipie",
            "artipie/white-paper",
            "artipie/docker-adapter",
            "artipie/www.artipie.com",
            "artipie/http",
            "artipie/maven-adapter",
            "artipie/nuget-adapter",
            "artipie/pypi-adapter",
            "artipie/composer-adapter",
            "artipie/gem-adapter",
            "artipie/ppom",
            "artipie/vertx-server",
            "artipie/files-adapter",
            "artipie/vertx-rx",
            "artipie/npm-proxy-adapter",
            "artipie/helm-adapter",
            "artipie/benchmarks",
            "artipie/central",
            "artipie/http-client",
            "artipie/helm-charts",
            "artipie/blog.artipie.com",
            "artipie/upload-action",
            "artipie/management-api",
            "artipie/pypi-example"
        );
        new Runner(artipie).run();
    }

    private final Collection<String> repos;

    public Runner(String... repos) {
        this.repos = Arrays.asList(repos);
    }

    public Runner(Collection<String> repos) {
        this.repos = repos;
    }

    public void run() throws Exception {
        int total = 0;
        final Path tmp = Paths.get("D:\\cloc-tmp", UUID.randomUUID().toString());
        tmp.toFile().mkdirs();
        try {
            for (String repo : repos) {
                Path folder = tmp.resolve(repo.replace("/", "_"));
                folder.toFile().mkdir();
                ClocOutput output = new GitHubRepoCloc(folder, repo).run();
                total += output.codeSum();
            }
        } finally {
            //todo: MoreFiles.deleteDirectoryContents(root);
        }
        System.out.println("Total: " + total);
    }
}
