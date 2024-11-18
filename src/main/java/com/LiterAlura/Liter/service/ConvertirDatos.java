package com.LiterAlura.Liter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class ConvertirDatos implements  IConvertirDatos{

    private ObjectMapper objectMapper =new ObjectMapper();

    //convierte el Json recibido a un objeto tipo arrays porque el JSON nos devuelve los datos en "results tipo arrays"
    @Override
    public <T> List<T> convertirLista(String json, Class<T> clase) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode resultsNode = rootNode.get("results");

            List<T> lista = new ArrayList<>();
            if (resultsNode != null && resultsNode.isArray()) {
                for (JsonNode node : resultsNode) {
                    T item = objectMapper.treeToValue(node, clase);
                    lista.add(item);
                }
            }
            return lista;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
