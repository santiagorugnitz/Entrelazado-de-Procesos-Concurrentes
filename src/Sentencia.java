public class Sentencia {
    private enum Tipo  {
        IF,
        ERROR,
        NADA, // estilo p_p_a
        ASIGNACION,
        WHILE,

    };
    private Tipo t;
    private Variable ppal;
    private Variable sec;
}
