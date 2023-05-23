public class GovVariables implements Variables{
    private double value;
    String name;
    public GovVariables (){
        name = "Stability";
        set(100.00);
    }
    public GovVariables (String n, double v){
        name = n;
        set(v);
    }
    public void set(double v) {//set variable
        try {
            if (v >=0){
                value = v;
            }
        }
        catch (Exception e){
            //Wrong value
        }
    }
    public double get() {//get variable
        return value;
    }

    public String getName() {//get variable
        return name;
    }

    public void add(double v){//add to variable
        value += v;
    }
    public void subtract(double v) {//subtract from variable
        value -= v;
    }
    public void effect(int c){//changes value based on situation/user
        //abs value of < 5 means not large effect,
        // greater or equal is larger effect for like a budget increase like tax
        //negative means negative effect, positive means positive effect
        if (Math.abs(c) < 5){
            value += Math.ceil(10 *(Math.random()) * (c/Math.abs(c)));
        }
        else {
            value += Math.ceil(c + 10 * (Math.random()) * (c/Math.abs(c)));
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }


}
