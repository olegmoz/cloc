package name.mozzhechkov.stars;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            "artipie/upload-action"
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
        for (String repo : repos) {
            final int value = stars(repo);
            total += value;
        }
        System.out.println("Total: " + total);
    }

    private int stars(final String repo) throws IOException {
        final HttpURLConnection con = (HttpURLConnection) new URL(
            String.format("https://github.com/%s", repo)
        ).openConnection();
        con.setRequestMethod("GET");
        String result = CharStreams.toString(
            new InputStreamReader(con.getInputStream(), Charsets.UTF_8)
        );
        final Pattern pattern = Pattern.compile(
            "<a class=\"social-count js-social-count\"[^>]*>(?<value>[^<]*)</a>"
        );
        final Matcher matcher = pattern.matcher(result);
        final boolean found = matcher.find();
        if (!found) {
            throw new IllegalArgumentException("Not found");
        }
        final String str = matcher.group("value").trim();
        System.out.println(repo + ": " + str);
        return Integer.parseInt(str);
    }
}
