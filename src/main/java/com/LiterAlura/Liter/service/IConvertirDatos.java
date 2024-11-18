package com.LiterAlura.Liter.service;

import java.util.List;

public interface IConvertirDatos {


    //metodo para convertir datos
    public <T> List<T> convertirLista(String json, Class<T> clase);

}
