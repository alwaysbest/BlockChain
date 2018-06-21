package cn.edu.nju.software.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ReflectionUtil {

    /**
     * Sets the value of a field on an object
     *
     * @param o         The object that contains the field
     * @param fieldName The name of the field
     * @param value     The new value
     * @return The previous value of the field
     */
    public static Object setField(Object o, String fieldName, Object value) {
        Object oldVal = null;
        try {
            final Field field = o.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            oldVal = field.get(o);
            field.set(o, value);
        } catch (Exception e) {
            throw new RuntimeException("Cannot get value of field " + fieldName, e);
        }
        return oldVal;
    }

    /**
     * Invokes method on object.
     * Used to access private methods.
     *
     * @param o          The object that contains the field
     * @param methodName The name of the field
     * @param args       The arguments.
     * @return Result of method.
     */
    public static Object invokeMethod(Object o, String methodName, Object... args) throws Throwable {

        Method[] methods = o.getClass().getDeclaredMethods();
        List<Method> reduce = new ArrayList<>(Arrays.asList(methods));
        for (Iterator<Method> i = reduce.iterator(); i.hasNext();
                ) {
            Method m = i.next();
            if (!methodName.equals(m.getName())) {
                i.remove();
                continue;
            }
            Class<?>[] parameterTypes = m.getParameterTypes();
            if (parameterTypes.length != args.length) {
                i.remove();
                continue;
            }
        }
        if (reduce.isEmpty()) {
            throw new RuntimeException(String.format("TEST ISSUE Could not find method %s on %s with %d arguments.",
                    methodName, o.getClass().getName(), args.length));
        }
        if (reduce.size() > 1) {
            throw new RuntimeException(String.format("TEST ISSUE Could not find unique method %s on %s. Found with %d matches.",
                    methodName, o.getClass().getName(), reduce.size()));
        }

        Method method = reduce.iterator().next();
        method.setAccessible(true);
        try {
            return method.invoke(o, args);
        } catch (IllegalAccessException e) {
            throw e;
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }

    }

    /**
     * Gets the value of a field on an object
     *
     * @param o         The object that contains the field
     * @param fieldName The name of the field
     * @return The value of the field
     */
    public static Object getField(Object o, String fieldName) {

        try {
            final Field field = o.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(o);
        } catch (Exception e) {
            throw new RuntimeException("Cannot get value of field " + fieldName, e);
        }
    }
}
