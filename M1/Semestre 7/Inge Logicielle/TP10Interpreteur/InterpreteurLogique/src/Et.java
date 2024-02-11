public class Et extends Binaire{
    public Et(Form f1, Form f2){super(f1,f2);}

    @Override
    public Form simplif() {
        if(isTop(f1) && isTop(f2)){return top;}
        if(isBot(f1) || isBot(f2)){return bot;}
        else {return this;}
    }

    @Override
    public Boolean eval() {
        return f1.eval() && f2.eval();
    }
}
