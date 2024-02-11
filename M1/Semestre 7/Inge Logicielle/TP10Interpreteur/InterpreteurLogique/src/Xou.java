public class Xou extends Binaire{
    public Xou(Form f1, Form f2){super(f1,f2);}

    @Override
    public Form simplif() {
        if(isTop(f1) && isTop(f2)){return bot;}
        if(isBot(f1) && isBot(f2)){return bot;}
        if(isBot(f1) && isTop(f2)){return top;}
        if(isTop(f1) && isBot(f2)){return top;}
        else {return this;}
    }

    @Override
    public Boolean eval() {
        return (f1.eval()||f2.eval()) && !(f1.eval()&&f2.eval());
    }
}
