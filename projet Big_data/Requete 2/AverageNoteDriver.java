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

public class AverageNoteDriver extends Configured implements Tool {
    @Override
    public int run(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.printf("Usage: %s [generic options] <input> <output> <semester/Annee>\n", getClass().getSimpleName());
            return -1;
        }

        Configuration conf = getConf();
        conf.set("targetSemesteretAnnee", args[2]);

        Job job = Job.getInstance(conf, "Success Rate");
        job.setJarByClass(getClass());
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(AverageNoteMapper.class);
        job.setCombinerClass(AverageNoteReducer.class);
        job.setReducerClass(AverageNoteReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

        return job.waitForCompletion(true) ? 0 : 1;
    }
}