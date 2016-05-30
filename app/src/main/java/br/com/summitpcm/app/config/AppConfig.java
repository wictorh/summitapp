package br.com.summitpcm.app.config;

public class AppConfig {
    public static String URL_LOGIN = "http://www.icode.eng.br/summitpcm/api/usuario/login?login=%s&senha=%s";

    public static String URL_PRODUTOS = "http://www.icode.eng.br/summitpcm/api/produto/todos/";
    public static String URL_PRODUTOS_ESPECIFICACOES = "http://icode.eng.br/summitpcm/api/produto/%s/atributos";

    public static String URL_CANAL = "http://www.icode.eng.br/summitpcm/api/canal/%s/";
    public static String URL_CANAIS = "http://www.icode.eng.br/summitpcm/api/canal/todos/";
    public static String URL_CANAIS_VENDAS = "http://www.icode.eng.br/summitpcm/api/canal/%s/vendas";
    public static String URL_CANAIS_VENDAS_PRODUTOS = "http://www.icode.eng.br/summitpcm/api/canal/%s/vendas/produtos";
    public static String URL_CANAIS_INFO = "http://www.icode.eng.br/summitpcm/api/canal/todos/";

    public static String URL_VENDA = "http://www.icode.eng.br/summitpcm/api/venda/%s/";

    public static String URL_ATRIBUTOS = "http://www.icode.eng.br/summitpcm/api/atributo/todos/";
}
