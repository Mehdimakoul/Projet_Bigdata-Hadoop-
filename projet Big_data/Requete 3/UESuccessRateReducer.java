package bdma.bigdata.ay;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class UESuccessRateReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
    private int totalStudents = 0;
    private int passedStudents = 0;

    @Override
    public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
        double sum = 0;
        int count = 0;
        for (DoubleWritable val : values) {
            sum += val.get();
            count++;
        }
        if (count > 0) { //   calculer les notes s'il ya 
            double average = sum / count;
            totalStudents++; // Incrémenter le total des étudiants 
            if (average >= 10) {
                passedStudents++; // Incrémenter le nombre d'étudiants ayant valider le eu ou bien il ont 10 ou plus
            }
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        double successRate = 0.0;
        if (totalStudents > 0) { // Éviter la division par zéro
            successRate = (double) passedStudents / totalStudents ; // Calculer le taux de réussite
        }
        context.write(new Text("Success Rate"), new DoubleWritable(successRate));
    }
}

