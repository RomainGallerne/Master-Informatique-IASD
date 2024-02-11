import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        Top top = Top.getTop();
        Bot bot = Bot.getBot();
        HashMap<String,Boolean> interpretation = new HashMap<>();
        interpretation.put("A",true);
        interpretation.put("B",false);
        interpretation.put("C",true);
        Var a = new Var("A",interpretation);
        Var b = new Var("B",interpretation);
        Var c = new Var("C",interpretation);

        Expression f = new Imp(new Ou(a, new Et(b,c)),bot);
        Expression fSimple = f.simplif();
        Boolean fResultat = fSimple.eval();
        System.out.println(fResultat);

        Expression g = new Xou(new Equ(bot,new Et(b,top)),b);
        Expression gSimple = g.simplif();
        Boolean gResultat = gSimple.eval();
        System.out.println(gResultat);
    }
}