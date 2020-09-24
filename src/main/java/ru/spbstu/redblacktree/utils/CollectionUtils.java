package ru.spbstu.redblacktree.utils;

import java.util.Collection;
import java.util.Objects;

import static java.lang.System.out;

public final class CollectionUtils {
    private CollectionUtils() {
    }

    /**
     * transforms collection of some elements to new collection of another elements by transformer's condition
     *
     * @param collection    given collection
     * @param transformator transforming condition
     * @param <E>           given collection element type
     * @param <T>           transformed collection element type
     * @return transformed collection
     */
    public static <E, T> Collection<T> transformIntoNew(Collection<E> collection, Transformer<E, T> transformator) {
        checkNull(collection, transformator);
        Collection<T> newCollection = createNewEmptyTransformed(collection);
        for (E item : collection) {
            if (item == null) {
                newCollection.add(null);
            } else {
                newCollection.add(transformator.transform(item));
            }
        }
        return newCollection;
    }


    /**
     * creates new empty collection of 'output' type elements
     *
     * @param collection given collection
     * @param <I>        given collection element type
     * @param <O>        output collection element type
     * @return new empty collection
     */
    private static <I, O> Collection<O> createNewEmptyTransformed(Collection<I> collection) {
        return (Collection<O>) createCollection(collection);
    }

    /**
     * creates new empty raw collection
     *
     * @param collection given collection
     * @return new empty given type collection
     */
    private static Collection createCollection(Collection collection) {
        checkNull(collection);
        try {
            return collection.getClass().newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            out.println(e.getMessage());
            return null;
        }
    }

    private static void checkNull(Object... ob) {
        for (Object o : ob) {
            if (Objects.isNull(o)) {
                throw new NullPointerException("can't handle null");
            }
        }
    }
}
