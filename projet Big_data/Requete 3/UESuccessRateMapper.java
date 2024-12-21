package bdma.bigdata.ay;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class UESuccessRateMapper extends Mapper<Object, Text, Text, DoubleWritable> {
    private String targetUECode;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        this.targetUECode = context.getConfiguration().get("targetUECode");
    }

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] parts = value.toString().split("\\|");

        if (parts.length >= 2) {
            String codeUE = parts[0].trim();
            String ueName = parts[1].trim();
            Double note = Double.parseDouble(parts[2].trim());

            if (codeUE.equals(targetUECode)) {
                context.write(new Text(ueName), new DoubleWritable(note));
            }
        }
    }
}
