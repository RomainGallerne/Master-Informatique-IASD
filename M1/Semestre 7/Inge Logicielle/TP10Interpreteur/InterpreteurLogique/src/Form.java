import java.util.HashMap;


public abstract class Form implements Expression{
    protected static Top top = new Top();
    protected static Bot bot = new Bot();;

    public boolean isTop(Form f){
        if(f == top){return true;}
        else return false;
    }

    public boolean isBot(Form f){
        if(f == bot){return true;}
        else return false;
    }
}
