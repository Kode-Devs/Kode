package org.kodedevs.kode.common;

public interface IBuffer<E> {
    boolean offer(E element);

    E poll();

    E get(int i);

    boolean isEmpty();

    boolean isFull();

    int capacity();

    int size();
}
