package name.mozzhechkov.cloc;

import java.io.IOException;

public interface Cloc {
    ClocOutput run() throws IOException;
}
