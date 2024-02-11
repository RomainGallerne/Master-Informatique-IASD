public class Not extends ExpressionNonTerminal{
    Form f;
    public Not(Form f){
        super();
        this.f = f;
    }

    @Override
    public Form simplif() {
        if(isTop(f)){return bot;}
        if(isBot(f)){return top;}
        else return this;
    }

    @Override
    public Boolean eval() {
        return !(f.eval());
    }
}
