import java.util.*;
public class Environment {//Where the simulation occurs

    private int startYear; // starting year of simulation
    private int quarter; //current quarter of sim
    private int currentYear; //current year of sim
    private int endYear; // year sim ends
    private EconVariables nominalInterest; //nominal interest rate of sim
    private EconVariables tse; // term structure effect of sim
    private EconVariables dp; //default risk premimum
    private HashMap<Integer, EconVariables> cpi; //hashmaps of CPI of all years. Can be used to calculate inflation rate
    private EconVariables gdp; //GDP of country in billions
    private EconVariables potentialGDP;
    private GovVariables stability;//scale 0-100
    private PriorityQueue<Integer> answer = new PriorityQueue<Integer>();
    private HashMap<Integer, Scenarios> scenariosHashMap = new HashMap<>();

    //constructors
    public Environment(int startYear, int endYear, double i, double tse, double dp,
                       double beforeCPI, double afterCPI, double gdp, double potential, double s){
        setStartYear(startYear);
        quarter = 0;
        currentYear = startYear;
        setEndYear(endYear);
        setNominalInterest(i);
        setTSE(tse);
        setDP(dp);
        setCPI(beforeCPI, afterCPI);
        setGdp(gdp);
        setPotentialGDP(potential);
        setStability(s);
        fillScenarios();

    }
    public Environment (){
        this(2023, 2033,  3.0, 2.3, 1.2, 300.0, 300.0,
                23200.0, 23200.0, 50);
    }

    //Main methods

     public boolean nextQuarter(){//goes to the nextQuarter
         System.out.println("Stability: " + stability);
         quarter += 1;
        if (quarter > 4){
            quarter = 1;
            return nextYear();
        }
        else{
            System.out.println(currentYear + " Q: " + quarter);
            System.out.println("______________________________");
            scenario();
            choice();
            return update();
        }
    }

    private boolean nextYear(){
        currentYear +=1;
        if (currentYear > endYear){
            return false;
        }
        else if (currentYear == endYear){
            System.out.println("You have one year left!");
        }
        System.out.println("Happy [Fiscal] New Years!");
        System.out.println(currentYear + " Q: " + quarter);
        System.out.println("______________________________");
        scenario();
        choice();
        return update();
    }

    private void scenario(){
        int chance = (int)((Math.random() * 100) + stability.get() - 50);
        if (chance < 50){
            chance = 1 + (int)((Math.random()*19.9));
            Scenarios temp = scenariosHashMap.get(chance);
            answer.add(temp.getAnswer());
            System.out.println(temp.getDescription());
            stability.effect(temp.getEffect());
        }
        else{
            chance = 21 + (int)((Math.random()*19.9));
            Scenarios temp = scenariosHashMap.get(chance);
            answer.add(temp.getAnswer());
            System.out.println(temp.getDescription());
            stability.effect(4);
        }
        if (stability.get() > 100){
            stability.set(100);
        }
        System.out.println("Stability: " + stability);


    }

    private void choice(){
        int d;
        try {//choices listed out
            Scanner in = new Scanner(System.in);
            System.out.println("How do you respond?");
            System.out.println("______________________________");
            System.out.println("Open Market Operations: ");
            System.out.println("[1]: Open Market Purchase");
            System.out.println("[2]: Open Market Selling");
            System.out.println();
            System.out.println("Bank Regulation: ");
            System.out.println("[3]: Increase Federal Rate");
            System.out.println("[4]: Decrease Federal Rate");
            System.out.println("[5]: Increase Reserve Ratio Requirement");
            System.out.println("[6]: Reduce Reserve Ratio Requirement");
            System.out.println();
            System.out.println("Other: ");
            System.out.println("[7]: Quantitative Easing");
            System.out.println("[8]: Quantitative Tightening");
            System.out.println("[9]: Print More Money");
            System.out.println("[0]: Do Nothing");


            System.out.println("______________________________");
            System.out.print("Enter number: ");
            d = in.nextInt();
            if (d < 0 || d > 9){
                throw new RuntimeException();
            }
        }
        catch (Exception e){
            System.out.println("Enter proper input please!");
            System.out.println();
            choice();
            return;
        }

        if (answer.peek() != null && answer.poll() == d){
            System.out.println("Good work on choosing the right answer");
            stability.effect(3);
        }
        else {
            System.out.println("Oh no! What did you do!");
            stability.effect(-10);
        }
    }

    private boolean update(){//checks if stability too high
        if (stability.get() > 0){
            return true;
        }
        else {
            return false;
        }
    }

    private void fillScenarios(){//fills scenario hash map
        //bad
        scenariosHashMap.put(1, new Scenarios());
        scenariosHashMap.put(2, new Scenarios("Congress decides to decrease unemployment benefits right before the GDP decreases.",
                -7, 4, "GDP"));
        scenariosHashMap.put(3, new Scenarios("Another war breaks out in Europe, affecting grain imports.", -3, 4,
                "Money Supply"));
        scenariosHashMap.put(4, new Scenarios("War breaks out in the Middle East. The US doesn't get involved, but oil prices increase massively."
                , -5, 4, "CPI"));
        scenariosHashMap.put(5, new Scenarios("Congress gives out stimulus checks to people despite the economy increasing at its normal rate.",
                -3, 4, "CPI"));
        scenariosHashMap.put(6, new Scenarios("An important boat gets stuck in a major canal leading to increases in shipping costs for the next year.",
                -2, 4, "CPI"));
        scenariosHashMap.put(7, new Scenarios("A rumor spreads that next quarter's inflation is supposed to be really bad.",
                -4, 3, "CPI"));
        scenariosHashMap.put(8, new Scenarios("The US enters in another war in the Middle East and needs to ramp up production.",
                -4, 4, "Nominal Interest"));
        scenariosHashMap.put(9, new Scenarios("The US stops fighting in other countries, leaving many factories needing to transition.",
                -4, 4, "Nominal Interest"));
        scenariosHashMap.put(10, new Scenarios("Banks are having trouble lending out cash to firms.",
                -3, 1, "Nominal Interest"));
        scenariosHashMap.put(11, new Scenarios("Banks increase rates too much because of a lack of cash on hand.",
                -3, 1, "Nominal Interest"));
        scenariosHashMap.put(12, new Scenarios("Nominal interest rates rise too much due to banks having not enough " +
                "money. Feds already tried open market operations and messing with the federal rates.",
                -2, 6, "Nominal Interest"));
        scenariosHashMap.put(13, new Scenarios("A bunch of banks's bond investments are not doing well and now they can't afford to loan cash to firms.",
                -6, 1, "Nominal Interest"));
        scenariosHashMap.put(14, new Scenarios("Banks are having cash problems due to many bad investments.",
                -7, 1, "Nominal Interest"));
        scenariosHashMap.put(15, new Scenarios("Banks are having cash problem, but the Feds already tried open market operations and changing the federal rates.",
                -7, 6, "Nominal Interest"));
        scenariosHashMap.put(16, new Scenarios("Short term interest rates are normal but long term interest rates are way above the target.",
                -3, 7, "Nominal Interest"));
        scenariosHashMap.put(17, new Scenarios("The GDP has decreased at a staggering rate.",
                -4, 4, "GDP"));
        scenariosHashMap.put(18, new Scenarios("The economy is starting to fail due to liquidity problem in banks. However, the Feds already tried lowering rates and open market operations.",
                -7, 4, "Money Supply"));
        scenariosHashMap.put(19, new Scenarios("Firms and households can not afford to get loans due to banks assets failing, staggering the economy.",
                -4, 4, "Nominal Interest"));
        scenariosHashMap.put(20, new Scenarios("Banks don't trust their consumers to pay them back in the long term.",
                -3, 6, "DP"));


        //good
        scenariosHashMap.put(21, new Scenarios("Everything is working as planned.", 2, 0,
                "GDP"));
        scenariosHashMap.put(22, new Scenarios("A new oil reserve in Texas has been found!", 2, 3,
                "CPI"));
        scenariosHashMap.put(23, new Scenarios("A new medical device is made that makes people live longer is invented!",
                2, 3, "CPI"));
        scenariosHashMap.put(24, new Scenarios("New technology leads to innovation in shipping!", 4, 3,
                "CPI"));
        scenariosHashMap.put(25, new Scenarios("GDP is increasing at a higher then expected rate.", 2, 3,
                "CPI"));
        scenariosHashMap.put(26, new Scenarios("Aramco decides to lower oil prices.", 2, 3,
                "CPI"));
        scenariosHashMap.put(27, new Scenarios("The economy is doing extraordinarily well. However it's not sustainable.", 6, 3,
                "CPI"));
        scenariosHashMap.put(28, new Scenarios("Banks are giving out too many loans to firms with the recent GDP surges.", 4, 3,
                "CPI"));
        scenariosHashMap.put(29, new Scenarios("Banks have too much money due to the recent GDP surges.", 2, 2,
                "Money Supply"));
        scenariosHashMap.put(30, new Scenarios("Rates are too low due to banks having excess reserves.", 2, 2,
                "Money Supply"));
        scenariosHashMap.put(31, new Scenarios("Reserves are higher than expected due to the recent GDP increases.", 4, 2,
                "Money Supply"));
        scenariosHashMap.put(32, new Scenarios("Banks are holding more money then needed due to workers having greater wealth.", 4, 2,
                "Money Supply"));
        scenariosHashMap.put(33, new Scenarios("Despite the GDP increasing, loaners are considering not lending money due to the rates being too low due to excess reserves.", 7, 2,
                "Money Supply"));
        scenariosHashMap.put(34, new Scenarios("Bank reserves are at a record high due to the recent increase in deposits.", 2, 2,
                "Money Supply"));
        scenariosHashMap.put(35, new Scenarios("The recent economic success has led to banks receiving too many deposits.", 2, 2,
                "Money Supply"));
        scenariosHashMap.put(36, new Scenarios("Long term loans rates are super low as the GDP increases.", 3, 8,
                "TSE"));
        scenariosHashMap.put(37, new Scenarios("Despite the recent GDP increases people are considering not lending with " +
                "long term loans due to its low rate", 6, 8,
                "TSE"));
        scenariosHashMap.put(38, new Scenarios("Bank reserves are at a record high due to the recent increase in deposits" +
                ", but the Feds have already tried market operations and raising the rates.", 5, 5,
                "Money Supply"));
        scenariosHashMap.put(39, new Scenarios("Even with Feds trying their usual tools, Bank reserves are too high.", 2, 5,
                "Money Supply"));
        scenariosHashMap.put(40, new Scenarios("Reserves are still record high after Feds try open market operations " +
                "and increasing the rate.", 2, 5,
                "Money Supply"));
    }
    //getters and setters
    private int getStartYear() {
        return startYear;
    }

    private void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    private int getQuarter() {
        return quarter;
    }

    private void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    private void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public int getEndYear() {
        return endYear;
    }

    private void setEndYear(int endYear) {
        this.endYear = endYear;
    }


    private EconVariables getNominalInterest() {
        return nominalInterest;
    }

    private void setNominalInterest(double v) {
        this.nominalInterest = new EconVariables("Nominal Interest" ,v,true);
    }

    private EconVariables getTSE() {
        return tse;
    }

    private void setTSE(double v) {
        tse = new EconVariables("Term Structure Effect" ,v,false);
    }

    private EconVariables getDP() {
        return dp;
    }

    private void setDP(double v) {
        this.dp = new EconVariables("Default Risk Premium" ,v,false);
    }

    private HashMap<Integer, EconVariables> getCPI() {
        return cpi;
    }

    private void setCPI(double beforeStartCPI, double afterStartCPI) {
        this.cpi = new HashMap<>();
        cpi.put(startYear-1, new EconVariables(startYear-1 + " CPI" ,beforeStartCPI,false));
        cpi.put(startYear-1, new EconVariables(startYear + " CPI" ,afterStartCPI,false));
    }

    private EconVariables getGdp() {
        return gdp;
    }

    private void setGdp(double v) {
        this.gdp = new EconVariables("GDP" ,v,false);;
    }

    private EconVariables getPotentialGDP() {
        return potentialGDP;
    }

    private void setPotentialGDP(double v) {
        this.potentialGDP = new EconVariables("Potential GDP" ,v,false);;
    }

    public GovVariables getStability() {
        return stability;
    }

    private void setStability(double v) {
        this.stability = new GovVariables("Stability", v);
    }
}
