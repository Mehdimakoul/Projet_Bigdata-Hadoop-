package bdma.bigdata.ay;

import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.conf.Configuration;

public class Main {
    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new Configuration(), new ProfessorSuccessRateDriver(), args);
        System.exit(exitCode);
    }
}
