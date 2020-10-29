package model.book;

public enum Estilo {
    SCIENCE_FICTION(1),
    SATIRE(2),
    DRAMA(3),
    ACTION_AND_ADVENTURE(4),
    ROMANCE(5),
    MYSTERY(6),
    HORROR(7),
    HEALTH(8),
    GUIDE(9),
    DIARIES(10),
    COMICS(11),
    JOURNALS(13),
    BIOGRAPHIES(14),
    FANTASY(15),
    HISTORY(16),
    SCIENCE(17),
    ART(18);

    private final int valor;

    Estilo(int i) {
        valor = i;
    }


    public static Estilo getById(int id) {
        for (Estilo estilo : Estilo.values()) {
            if (estilo.valor == id) {
                return estilo;
            }
        }
        return null;
    }

}
