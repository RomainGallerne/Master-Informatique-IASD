public class CompilerFabricC implements CompilerFabric {
    private static CompilerFabricC compilateurC = null;
    private CompilerFabricC(){}
    public static CompilerFabricC getCompilateurC(){
        if(compilateurC != null){return compilateurC;}
        compilateurC = new CompilerFabricC();
        return compilateurC;
    }

    @Override
    public LexerAbstrait crerLexeur() {
        return new LexerC();
    }
    @Override
    public ParserAbstrait crerParseur() {
        return new ParserC();
    }
    @Override
    public GenerateurAbstrait crerGenerateur() {
        return new GenerateurC();
    }
}
