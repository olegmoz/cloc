package name.mozzhechkov.cloc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClocOutput {

    private final Pattern SUM = Pattern.compile(
        "^\\s*SUM:" +
            "\\s+(?<files>\\d+)" +
            "\\s+(?<blank>\\d+)" +
            "\\s+(?<comment>\\d+)" +
            "\\s+(?<code>\\d+)" +
            "\\s*$",
        Pattern.MULTILINE);

    private final String raw;

    public ClocOutput(final String raw) {
        this.raw = raw;
    }

    public int codeSum() {
        final Matcher matcher = SUM.matcher(raw);
        if (!matcher.find()) {
            return 0;
            //todo: throw new IllegalStateException("Cannot find SUM line:\n" + this.raw);
        }
        final String string = matcher.group("code");
        if (matcher.find()) {
            throw new IllegalStateException("More then one SUM line:\n" + this.raw);
        }
        return Integer.parseInt(string);
    }
}
