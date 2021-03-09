package main.java.assignment2;
import java.text.DecimalFormat;

public class TestFile {
    private String filename;
    private double spamProbability;
    private String actualClass;

    public TestFile(String filename, double spamProbability, String actualClass) {
        this.filename = filename;
        this.spamProbability = spamProbability;
        this.actualClass = actualClass;
    }

    public String getFilename() {
        return filename;
    }

    public double getSpamProbability() {
        return spamProbability;
    }

    public String getActualClass() {
        return actualClass;
    }

    public String getSpamProbRounded(){
        DecimalFormat df = new DecimalFormat("0.00000");
        return df.format(this.spamProbability);
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setActualClass(String actualClass) {
        this.actualClass = actualClass;
    }

    public void setSpamProbability(double spamProbability) {
        this.spamProbability = spamProbability;
    }

    public String toString(){
        return "{" + filename + ", " + spamProbability + ", " + actualClass + "}";
    }
}
