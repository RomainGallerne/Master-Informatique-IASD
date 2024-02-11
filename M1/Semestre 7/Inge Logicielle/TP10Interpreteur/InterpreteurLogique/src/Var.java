import java.util.HashMap;
import java.util.Stack;

public class Var extends Form{
    private String var;
    protected HashMap<String, Boolean> interpretation;

    public Var(String s, HashMap<String, Boolean> inte){
        this.var = s;
        this.interpretation = inte;
    }

    @Override
    public Form simplif() {
        return this;
    }

    @Override
    public Boolean eval() {
        return interpretation.get(var);
    }
}
