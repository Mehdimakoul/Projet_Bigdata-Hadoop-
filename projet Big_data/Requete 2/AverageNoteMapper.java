package bdma.bigdata.ay;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class AverageNoteMapper extends Mapper<Object, Text, Text, DoubleWritable> {
    private String targetSemester;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        this.targetSemester = context.getConfiguration().get("targetSemesteretAnnee");
    }

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] parts = value.toString().split("\\|"); // Échapper le caractère '|' car il est spécial en expression régulière
        String[] part = this.targetSemester.split("/");

        // Vérifier si la ligne contient suffisamment d'éléments
        if (parts.length >= 3) { // Nous devons vérifier si nous avons au moins 3 parties pour extraire le numéro d'étudiant, le code de l'UE et la note
            String codeUE = parts[1].trim(); // Code_UE (potentiellement le semestre)
            String numEtudiant = parts[0].trim();
            String semester = codeUE.substring(0, 3);
            String year = numEtudiant.substring(0, 4);

            // Vérifier si le semestre et l'année correspondent à ceux spécifiés dans les arguments
            if (part.length == 2 && part[0].equals(semester) && part[1].equals(year)) {
                Text studentId = new Text(numEtudiant); // Numéro d'étudiant
                DoubleWritable grade = new DoubleWritable(Double.parseDouble(parts[2].trim())); // Note

                context.write(studentId, grade);
            }
        }
    }
}