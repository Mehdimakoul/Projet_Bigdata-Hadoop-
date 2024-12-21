package bdma.bigdata.ay;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class UESuccessRateDriver extends Configured implements Tool {
    @Override
    public int run(String[] args) throws Exception {
        if (args.length != 4) {
            System.err.printf("Usage: %s [generic options] <input> <input> <output> <UECode>\n", getClass().getSimpleName());
            return -1;
        }

        Configuration conf = getConf();
        conf.set("targetUECode", args[3]);

        Job job = Job.getInstance(conf, "UE Success Rate");
        job.setJarByClass(getClass());
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileInputFormat.addInputPath(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));

        job.setMapperClass(UESuccessRateMapper.class);
        job.setReducerClass(UESuccessRateReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

        return job.waitForCompletion(true) ? 0 : 1;
    }
}
