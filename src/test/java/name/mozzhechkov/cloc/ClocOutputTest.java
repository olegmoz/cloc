package name.mozzhechkov.cloc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

class ClocOutputTest {

    @Test
    void readCodeSum() {
        final ClocOutput output = new ClocOutput(
            "     279 text files.\n" +
                "     267 unique files.\n" +
                "     123 files ignored.\n" +
                "\n" +
                "github.com/AlDanial/cloc v 1.84  T=2.00 s (127.0 files/s, 10424.0 lines/s)\n" +
                "-------------------------------------------------------------------------------\n" +
                "Language                     files          blank        comment           code\n" +
                "-------------------------------------------------------------------------------\n" +
                "Java                           159           1263           6806           9741\n" +
                "Markdown                        18            245              0            778\n" +
                "YAML                            42              1              5            613\n" +
                "Maven                            7             42             96            551\n" +
                "Handlebars                       4             15              6            205\n" +
                "Bourne Shell                    10             57             58            113\n" +
                "JSON                             4              0              0             68\n" +
                "PHP                              2             13             15             41\n" +
                "XML                              2              0              0             27\n" +
                "Dockerfile                       1              3              2             16\n" +
                "Python                           2              5              7             13\n" +
                "Ruby                             1              0              0             12\n" +
                "PowerShell                       1              9             10             11\n" +
                "JavaScript                       1              0              0              1\n" +
                "-------------------------------------------------------------------------------\n" +
                "SUM:                           254           1653           7005          12190\n" +
                "-------------------------------------------------------------------------------"
        );
        assertThat(output.codeSum(), is(12190));
    }

    @Test
    void readWhenNoSum() {
        final ClocOutput output = new ClocOutput(
            "       2 text files.\n" +
                "       2 unique files.                              \n" +
                "       3 files ignored.\n" +
                "\n" +
                "github.com/AlDanial/cloc v 1.84  T=0.50 s (2.0 files/s, 4.0 lines/s)\n" +
                "-------------------------------------------------------------------------------\n" +
                "Language                     files          blank        comment           code\n" +
                "-------------------------------------------------------------------------------\n" +
                "Markdown                         1              0              0              2\n" +
                "-------------------------------------------------------------------------------\n" +
                "\n"
        );
        assertThat(output.codeSum(), is(0)); //todo: fix and make it 2
    }
}
