import java.util.*;
public class Scenarios {
    private String description;
    private int effect;
    private int answer;
    private String whatItEffects;

    private ArrayList <String> possibleWhatItEffects;

    public Scenarios(String d, int e, int a, String whatItEffects){
        possibleWhatItEffects = new ArrayList<>();
        possibleWhatItEffects.add("Money Supply");
        possibleWhatItEffects.add("Nominal Interest");
        possibleWhatItEffects.add("TSE");
        possibleWhatItEffects.add("DP");
        possibleWhatItEffects.add("CPI");
        possibleWhatItEffects.add("GDP");
        possibleWhatItEffects.add("Potential GDP");
        setDescription(d);
        setEffect(e);
        setAnswer(a);
        setWhatItEffects(whatItEffects);
    }
    public Scenarios(){
        this("The president accidentally states that that the economy is about to tank.",
                -10, 4, "GDP");
    }

    //getters and setters


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int a) {
        this.answer = a;
    }

    public String getWhatItEffects() {
        return whatItEffects;
    }

    public void setWhatItEffects(String whatItEffects) {
        if (possibleWhatItEffects.contains(whatItEffects)) {
            this.whatItEffects = whatItEffects;
        }
    }
}
