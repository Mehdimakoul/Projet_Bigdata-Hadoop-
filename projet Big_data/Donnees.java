package bdma.bigdata.wordcount;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Donnees {

    // Fonction pour générer une date de naissance aléatoire
    private static String dateNaissanceAleatoire() {
        Random random = new Random();
        int annee = random.nextInt(26) + 1980; // Entre 1980 et 2005
        int mois = random.nextInt(12) + 1;   // Entre 1 et 12
        int jour = random.nextInt(28) + 1;     // Entre 1 et 28 (simplification)
        return String.format("%02d/%02d/%04d", jour, mois, annee);
    }

    // Générer des données pour les étudiants
    private static void genererDonneesEtudiants(int nombreEtudiants) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Etudiants.csv"))) {
            writer.write("Numéro d'Étudiant,promotion,nom,prenom,date_naissance,email,telephone,adresse\n");
            Random random = new Random();
            for (int i = 1; i <= nombreEtudiants; i++) {
                String numeroEtudiant = String.format("2012%06d", i);
                String promotion = random.nextBoolean() ? "L" + random.nextInt(3) : "M" + random.nextInt(2);
                String nom = "Nom" + i;
                String prenom = "Prenom" + i;
                String dateNaissance = dateNaissanceAleatoire();
                String email = prenom.toLowerCase() + "." + nom.toLowerCase() + "@example.com";
                String telephone = String.valueOf(1000000000 + random.nextInt(900000000));
                String adresse = "Adresse" + i;
                String ligne = String.format("%s,%s,%s,%s,%s,%s,%s,%s\n",
                        numeroEtudiant, promotion, nom, prenom, dateNaissance, email, telephone, adresse);
                writer.write(ligne);
            }
            System.out.println("Données des étudiants générées avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Générer des données pour les unités d'enseignement
    private static void genererDonneesUE(int nombreUE) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("UE.csv"))) {
            writer.write("code_ue,nom_ue,enseignants\n");
            Random random = new Random();
            for (int i = 1; i <= nombreUE; i++) {
                String codeUE = String.format("S07A%03d", i);
                String nomUE = "UE" + i;
                String enseignants = "Prof." + random.nextInt(100);
                String ligne = String.format("%s,%s,%s\n", codeUE, nomUE, enseignants);
                writer.write(ligne);
            }
            System.out.println("Données des UE générées avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Générer des données pour les notes des étudiants
    private static void genererDonneesNotes(int nombreEtudiants, int nombreUE) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Notes.csv"))) {
            writer.write("Numéro d'Étudiant,code_ue,note\n");
            Random random = new Random();
            for (int i = 1; i <= nombreEtudiants; i++) {
                String numeroEtudiant = String.format("2012%06d", i);
                for (int j = 1; j <= nombreUE; j++) {
                    String codeUE = String.format("S07A%03d", j);
                    int note = random.nextInt(21); // Note entre 0 et 20
                    String ligne = String.format("%s,%s,%d\n", numeroEtudiant, codeUE, note);
                    writer.write(ligne);
                }
            }
            System.out.println("Données des notes générées avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int nombreEtudiants = 1000;
        int nombreUE = 100;
        genererDonneesEtudiants(nombreEtudiants);
        genererDonneesUE(nombreUE);
        genererDonneesNotes(nombreEtudiants, nombreUE);
    }}