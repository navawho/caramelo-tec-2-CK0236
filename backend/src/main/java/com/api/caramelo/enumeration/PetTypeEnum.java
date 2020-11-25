package com.api.caramelo.enumeration;

public enum PetTypeEnum {

    GATO,
    CACHORRO;

    private String value;

    private void PetTypeEnum(String value) {
        this.value = value;
    }

    private String getValue() {
        return value;
    }
}
