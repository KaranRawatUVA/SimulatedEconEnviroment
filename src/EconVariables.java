public class EconVariables implements Variables{

    private double value;
    boolean user; //can the user change this value
    String name;
    public EconVariables (){
        name = "Money Supply";
        set(100.00);
        user = true;
    }
    public EconVariables (String n, double v, Boolean u){
        name = n;
        set(v);
        user = u;
    }
    public boolean isUser() {
        return user;
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
    public void effect(int c){//changes value based on situation/user,
        //abs value of c < 5 means not large effect, greater or equal is larger effect
        //negative means negative effect, positive mean
        if (Math.abs(c) < 5){
            value +=  (int)(value *Math.random()/4 * (c/Math.abs(c)));
        }
        else {
            value += c +  (value * Math.random()* (c/Math.abs(c)));
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
