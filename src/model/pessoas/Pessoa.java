package model.pessoas;

import java.io.Serializable;

public abstract class Pessoa implements Serializable {
    protected String name;
    protected String surname;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
