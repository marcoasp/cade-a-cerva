package br.com.marco.cadeacerva.testcommons.utils.mockito;

import org.mockito.stubbing.Answer;

import java.lang.reflect.Field;

public class AnswerUtils {

    public static <T> Answer<T> argWithId(Object value) {
        return invocationOnMock -> {
            T target = invocationOnMock.getArgument(0);
            Class clazz = target.getClass();
            Field f = clazz.getDeclaredField("id");
            f.setAccessible(true);
            f.set(target, value);
            f.setAccessible(false);
            return target;
        };
    }
}
