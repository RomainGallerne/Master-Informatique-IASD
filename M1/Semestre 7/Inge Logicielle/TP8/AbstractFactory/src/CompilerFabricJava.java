public class CompilerFabricJava implements CompilerFabric {
    private static CompilerFabricJava compilateurJava = null;
    private CompilerFabricJava(){}
    public static CompilerFabricJava getCompilateurJava(){
        if(compilateurJava != null){return compilateurJava;}
        compilateurJava = new CompilerFabricJava();
        return compilateurJava;
    }

    @Override
    public LexerAbstrait crerLexeur() {
        return new LexerJava();
    }
    @Override
    public ParserAbstrait crerParseur() {
        return new ParserJava();
    }
    @Override
    public GenerateurAbstrait crerGenerateur() {
        return new GenerateurJava();
    }
}
