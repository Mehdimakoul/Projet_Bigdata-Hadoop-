package bdma.bigdata.ay;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class ProfessorSuccessRateReducer extends Reducer<Text, DoubleWritable, Text, Text> {
    @Override
    public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
        int totalStudents = 0;
        int passedStudents = 0;

        for (DoubleWritable val : values) {
            totalStudents++;
            if (val.get() >= 10) {
                passedStudents++;
            }
        }

        double successRate = totalStudents > 0 ? (double) passedStudents / totalStudents : 0;
        String classification = getClassification(successRate);

        context.write(key, new Text(classification + " - " + String.format("%.2f", successRate)));
    }

    private String getClassification(double successRate) {
        if (successRate < 0.12) {
            return "Cours Très Difficile";
        } else if (successRate < 0.25) {
            return "Cours Difficile";
        } else if (successRate < 0.5) {
            return "Cours Moyen";
        } else if (successRate < 0.75) {
            return "Cours Facile";
        } else {
            return "Cours Très Facile";
        }
    }
}
