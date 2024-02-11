public interface CompilerFabric {
    public LexerAbstrait crerLexeur();
    public ParserAbstrait crerParseur();
    public GenerateurAbstrait crerGenerateur();
}
