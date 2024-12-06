package com.example.proyecto_final.Logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una cola de prioridad utilizando un heap.
 * @param <T> El tipo de elementos que se almacenarán en la cola de prioridad.
 */
public class PriorityQueueHeap<T extends Comparable<T>> {
    private List<T> heap;

    /**
     * Constructor que inicializa la cola de prioridad.
     */
    public PriorityQueueHeap() {
        heap = new ArrayList<>();
    }

    /**
     * Inserta un elemento en la cola de prioridad.
     * @param item El elemento a insertar.
     */
    public void insert(T item) {
        heap.add(item);
        siftUp(heap.size() - 1);
    }

    /**
     * Devuelve el elemento con la mayor prioridad sin eliminarlo.
     * @return El elemento con la mayor prioridad.
     * @throws IllegalStateException Si la cola está vacía.
     */
    public T peek() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("La cola está vacía.");
        }
        return heap.get(0);
    }

    /**
     * Elimina y devuelve el elemento con la mayor prioridad.
     * @return El elemento con la mayor prioridad.
     * @throws IllegalStateException Si la cola está vacía.
     */
    public T poll() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("La cola está vacía.");
        }
        T result = heap.get(0);
        T lastItem = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, lastItem);
            siftDown(0);
        }
        return result;
    }

    /**
     * Elimina un elemento específico de la cola de prioridad.
     * @param item El elemento a eliminar.
     * @return true si el elemento fue eliminado, false si no se encontró.
     */
    public boolean remove(T item) {
        int index = heap.indexOf(item);
        if (index == -1) {
            return false;
        }
        T lastItem = heap.remove(heap.size() - 1);
        if (index < heap.size()) {
            heap.set(index, lastItem);
            siftDown(index);
            siftUp(index);
        }
        return true;
    }

    /**
     * Verifica si la cola de prioridad está vacía.
     * @return true si la cola está vacía, false en caso contrario.
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Realiza el sift-up para mantener la propiedad del heap.
     * @param index El índice del elemento a ajustar.
     */
    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index).compareTo(heap.get(parentIndex)) < 0) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    /**
     * Realiza el sift-down para mantener la propiedad del heap.
     * @param index El índice del elemento a ajustar.
     */
    private void siftDown(int index) {
        int leftChildIndex, rightChildIndex, minIndex;

        while (index < heap.size()) {
            leftChildIndex = 2 * index + 1;
            rightChildIndex = 2 * index + 2;
            minIndex = index;

            if (leftChildIndex < heap.size() && heap.get(leftChildIndex).compareTo(heap.get(minIndex)) < 0) {
                minIndex = leftChildIndex;
            }

            if (rightChildIndex < heap.size() && heap.get(rightChildIndex).compareTo(heap.get(minIndex)) < 0) {
                minIndex = rightChildIndex;
            }

            if (minIndex == index) {
                break;
            }

            swap(index, minIndex);
            index = minIndex;
        }
    }

    /**
     * Intercambia dos elementos en el heap.
     * @param index1 El índice del primer elemento.
     * @param index2 El índice del segundo elemento.
     */
    private void swap(int index1, int index2) {
        T temp = heap.get(index1);
        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);
    }

    /**
     * Devuelve el tamaño de la cola de prioridad.
     * @return El número de elementos en la cola de prioridad.
     */
    public int size() {
        return heap.size();
    }

    /**
     * Devuelve la lista interna que representa el heap.
     * @return La lista interna del heap.
     */
    public List<T> getHeap() {
        return heap;
    }

    public void put(T item) {
        heap.add(item);
        siftUp(heap.size() - 1);
    }
}