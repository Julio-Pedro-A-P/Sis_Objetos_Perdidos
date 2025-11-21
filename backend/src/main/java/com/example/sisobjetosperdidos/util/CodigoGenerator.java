package com.example.sisobjetosperdidos.util;

import java.util.Random;

public class CodigoGenerator {

    private static final Random RANDOM = new Random();

    public static String generarCodigoNumerico(int longitud) {
        StringBuilder codigo = new StringBuilder(longitud);

        for (int i = 0; i < longitud; i++) {
            codigo.append(RANDOM.nextInt(10)); // 0–9
        }

        return codigo.toString();
    }
}
