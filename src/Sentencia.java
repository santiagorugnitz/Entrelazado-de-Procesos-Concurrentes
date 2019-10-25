public class Sentencia {
     enum Tipo  {
        IF,
        ERROR,
        NADA, // estilo p_p_a
        ASIGNACION,
        WHILE,
        ENDWHILE,
        ENDIF,

    };
    Tipo t;
    Variable ppal;
    Variable sec;
}
