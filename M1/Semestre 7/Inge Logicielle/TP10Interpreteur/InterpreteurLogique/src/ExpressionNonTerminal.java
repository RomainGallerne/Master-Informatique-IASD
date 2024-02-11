import java.util.Stack;

public class ExpressionNonTerminal extends Form{
    Stack<Form> f;

    ExpressionNonTerminal(){
        this.f = f;
    }

    @Override
    public Form simplif() {
        return f.pop().simplif();
    }

    @Override
    public Boolean eval() {
        return f.pop().eval();
    }
}
