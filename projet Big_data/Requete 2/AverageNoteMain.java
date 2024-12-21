package bdma.bigdata.ay;

import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.conf.Configuration;

public class AverageNoteMain {
    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new Configuration(), new AverageNoteDriver(), args);
        System.exit(exitCode);
    }
}
