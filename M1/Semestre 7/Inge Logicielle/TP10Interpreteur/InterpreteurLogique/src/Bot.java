public class Bot extends Constante{
    protected static Bot bot;

    public Bot(){};

    public static Bot getBot(){
        if(bot == null){
            bot = new Bot();
        }
        return bot;
    }

    @Override
    public Boolean eval() {
        return false;
    }
}
