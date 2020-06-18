package br.com.marco.cadeacerva.testcommons.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonPayloadProvider {

    public static String from(final Class<?> aClass, final String methodName) throws IOException {
        Path path = new ClassPathResource(String.format("%s/%s.json", aClass.getCanonicalName().replace(".", "/"), methodName)).getFile().toPath();
        return new String(Files.readAllBytes(path));
    }
}
