package org.andrea.data;

import java.io.Serializable;

/**
 * BestAlbum class
 */
public class BestAlbum implements Validatable, Serializable {
    private final String fullName; //Длина строки не должна быть больше 1237, Строка не может быть пустой, Поле может быть null
    private final AlbumType type; //Поле не может быть null

    public BestAlbum(String name, AlbumType t) {
        fullName = name;
        type = t;
    }


    /**
     * @return String
     */
    public String getFullName() {
        return fullName;
    }


    /**
     * @return AlbumType
     */
    public AlbumType getType() {
        return type;
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        String s = "";
        s += "{";
        if (fullName != null) s += "\"fullName\" : " + "\"" + getFullName() + "\"" + ", ";
        s += "\"type\" : " + "\"" + getType().toString() + "\"" + "}";
        return s;
    }

    public boolean validate() {
        return (
                (fullName == null || (!fullName.equals("") && !(fullName.length() > 1237))) &&
                        type != null
        );
    }
}
