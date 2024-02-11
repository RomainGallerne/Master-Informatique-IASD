public class Top extends Constante{
    protected static Top top;

    public Top(){};

    public static Top getTop(){
        if(top == null){
            top = new Top();
        }
        return top;
    }

    @Override
    public Boolean eval() {
        return true;
    }
}
