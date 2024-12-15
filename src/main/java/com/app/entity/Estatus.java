package com.app.entity;

public enum Estatus {
	ABIERTO, CERRADO;
	
	public static Estatus fromString(String estado) {
        if (estado != null) {
            return Estatus.valueOf(estado.toUpperCase());
        }
        return null;
    }
}
