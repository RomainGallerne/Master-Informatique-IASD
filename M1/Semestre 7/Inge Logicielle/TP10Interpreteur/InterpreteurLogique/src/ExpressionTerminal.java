import java.util.Stack;

public abstract class ExpressionTerminal implements Expression{
    private boolean atome;

    public ExpressionTerminal(Boolean atome){this.atome = atome;}
}
