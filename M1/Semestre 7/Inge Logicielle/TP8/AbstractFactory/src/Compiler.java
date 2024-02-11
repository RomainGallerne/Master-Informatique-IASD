public class Compiler {
    LexerAbstrait lexeur; //Point d'extension
    ParserAbstrait parseur; //Point d'extension
    GenerateurAbstrait generateur; //Point d'extension
    public Compiler(String type) throws Exception {
        CompilerFabric compilateur = null;
        switch(type){
            case "C++":
                compilateur = CompilerFabricC.getCompilateurC(); //Injection de dépendence
                break;
            case "Java":
                compilateur = CompilerFabricJava.getCompilateurJava(); //Injection de dépendence
                break;
            default:
                throw new Exception("Non supported Language : "+type+", Extend the framework to support it");
        }
        lexeur = compilateur.crerLexeur(); //Injection de dépendence
        parseur = compilateur.crerParseur(); //Injection de dépendence
        generateur = compilateur.crerGenerateur(); //Injection de dépendence
    }

    public void compile(ProgramText progText){
        String resLexeur = lexeur.scan(progText.getProgramTexte()); //Inversion de controle
        String resParseur = parseur.parse(resLexeur); //Inversion de controle
        String resGenerateur = generateur.generate(resParseur); //Inversion de controle
        System.out.println(resLexeur);
        System.out.println(resParseur);
        System.out.println(resGenerateur);
    }

}
