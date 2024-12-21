package bdma.bigdata.ay;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class ProfessorSuccessRateMapper extends Mapper<Object, Text, Text, DoubleWritable> {
    private String targetProfessor;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        this.targetProfessor = context.getConfiguration().get("targetProfessor");
    }

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] parts = value.toString().split("\\|");

        if (parts.length >= 3) {
            String codeUE = parts[0].trim();
            String[] professors = parts[2].split(",");
            Double note = Double.parseDouble(parts[2].trim());

            for (String professor : professors) {
                if (professor.trim().equals(targetProfessor)) {
                    context.write(new Text(codeUE), new DoubleWritable(note));
                    break; // Sortir du boucle une fois trouvé
                }
            }
        }
    }
}
