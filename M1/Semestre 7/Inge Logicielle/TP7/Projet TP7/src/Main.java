public class Main {
    public static void main(String[] args) throws BidonExcpetion {
        int[] capacitesBidons = {200, 100, 50};
        Partie1 p = new Partie1(3,capacitesBidons,150);
        p.jouer();
    }
}