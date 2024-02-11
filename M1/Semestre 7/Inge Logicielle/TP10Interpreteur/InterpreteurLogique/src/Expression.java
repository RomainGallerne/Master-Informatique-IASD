import java.util.HashMap;
import java.util.Stack;

interface Expression {
    public abstract Form simplif();
    public abstract Boolean eval();
}
