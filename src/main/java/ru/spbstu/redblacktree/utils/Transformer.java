package ru.spbstu.redblacktree.utils;

/**
 * interface for transforming action
 *
 * @param <I> value to transform
 * @param <O> transformed value
 */
public interface Transformer<I, O> {
    /**
     * transforms input value into another
     *
     * @param input value to transform
     * @return transformed value
     */
    O transform(I input);
}