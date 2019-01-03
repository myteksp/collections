package com.gf.collections;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import sun.misc.SharedSecrets;


@SuppressWarnings("restriction")
public class FastList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable{
	private static final long serialVersionUID = -3666416526000312135L;

	private static final int DEFAULT_CAPACITY = GfCollections.DEFAULT_ALLOCATION;

	private static final Object[] EMPTY_ELEMENTDATA = {};

	private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

	private Object[] elementData;

	private int size;

	public FastList(final int initialCapacity) {
		if (initialCapacity > 0) {
			this.elementData = new Object[initialCapacity];
		} else if (initialCapacity == 0) {
			this.elementData = EMPTY_ELEMENTDATA;
		} else {
			this.elementData = new Object[GfCollections.MIN_ALLOCATION];
		}
	}

	public FastList() {
		this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
	}

	public FastList(final Collection<? extends E> c) {
		elementData = c.toArray();
		if ((size = elementData.length) != 0) {
			if (elementData.getClass() != Object[].class)
				elementData = Arrays.copyOf(elementData, size, Object[].class);
		} else {
			this.elementData = EMPTY_ELEMENTDATA;
		}
	}

	public void trimToSize() {
		if (size < elementData.length) {
			elementData = (size == 0)
					? EMPTY_ELEMENTDATA
							: Arrays.copyOf(elementData, size);
		}
	}

	public void ensureCapacity(int minCapacity) {
		int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
				// any size if not default element table
				? 0
						// larger than default for default empty table. It's already
						// supposed to be at default size.
						: DEFAULT_CAPACITY;

		if (minCapacity > minExpand) {
			ensureExplicitCapacity(minCapacity);
		}
	}

	private static int calculateCapacity(Object[] elementData, int minCapacity) {
		if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
			return Math.max(DEFAULT_CAPACITY, minCapacity);
		}
		return minCapacity;
	}

	private void ensureCapacityInternal(int minCapacity) {
		ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
	}

	private void ensureExplicitCapacity(int minCapacity) {
		if (minCapacity - elementData.length > 0)
			grow(minCapacity);
	}

	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	private void grow(int minCapacity) {
		// overflow-conscious code
		int oldCapacity = elementData.length;
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		if (newCapacity - minCapacity < 0)
			newCapacity = minCapacity;
		if (newCapacity - MAX_ARRAY_SIZE > 0)
			newCapacity = hugeCapacity(minCapacity);
		// minCapacity is usually close to size, so this is a win:
		elementData = Arrays.copyOf(elementData, newCapacity);
	}

	private static int hugeCapacity(int minCapacity) {
		if (minCapacity < 0) // overflow
			throw new OutOfMemoryError();
		return (minCapacity > MAX_ARRAY_SIZE) ?
				Integer.MAX_VALUE :
					MAX_ARRAY_SIZE;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean contains(Object o) {
		return indexOf(o) >= 0;
	}

	public int indexOf(Object o) {
		if (o == null) {
			for (int i = 0; i < size; i++)
				if (elementData[i]==null)
					return i;
		} else {
			for (int i = 0; i < size; i++)
				if (o.equals(elementData[i]))
					return i;
		}
		return -1;
	}

	public int lastIndexOf(Object o) {
		if (o == null) {
			for (int i = size-1; i >= 0; i--)
				if (elementData[i]==null)
					return i;
		} else {
			for (int i = size-1; i >= 0; i--)
				if (o.equals(elementData[i]))
					return i;
		}
		return -1;
	}

	public Object clone() {
		try {
			final FastList<?> v = (FastList<?>) super.clone();
			v.elementData = Arrays.copyOf(elementData, size);
			return v;
		} catch (final CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}

	public Object[] toArray() {
		return Arrays.copyOf(elementData, size);
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(final T[] a) {
		if (a.length < size)
			return (T[]) Arrays.copyOf(elementData, size, a.getClass());
		System.arraycopy(elementData, 0, a, 0, size);
		if (a.length > size)
			a[size] = null;
		return a;
	}

	@SuppressWarnings("unchecked")
	E elementData(int index) {
		return (E) elementData[index];
	}


	public E get(int index) {
		rangeCheck(index);
		return elementData(index);
	}

	public E set(int index, E element) {
		rangeCheck(index);
		E oldValue = elementData(index);
		elementData[index] = element;
		return oldValue;
	}

	public boolean add(E e) {
		ensureCapacityInternal(size + 1);
		elementData[size++] = e;
		return true;
	}

	public void add(int index, E element) {
		rangeCheckForAdd(index);
		ensureCapacityInternal(size + 1);
		System.arraycopy(elementData, index, elementData, index + 1, size - index);
		elementData[index] = element;
		size++;
	}

	public E remove(int index) {
		rangeCheck(index);
		E oldValue = elementData(index);
		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(elementData, index+1, elementData, index, numMoved);
		elementData[--size] = null;
		return oldValue;
	}

	public boolean remove(Object o) {
		if (o == null) {
			for (int index = 0; index < size; index++)
				if (elementData[index] == null) {
					fastRemove(index);
					return true;
				}
		} else {
			for (int index = 0; index < size; index++)
				if (o.equals(elementData[index])) {
					fastRemove(index);
					return true;
				}
		}
		return false;
	}


	private void fastRemove(int index) {
		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(elementData, index+1, elementData, index, numMoved);
		elementData[--size] = null; 
	}

	public void clear() {
		for (int i = 0; i < size; i++)
			elementData[i] = null;

		size = 0;
	}

	public boolean addAll(Collection<? extends E> c) {
		final Object[] a = c.toArray();
		int numNew = a.length;
		ensureCapacityInternal(size + numNew); 
		System.arraycopy(a, 0, elementData, size, numNew);
		size += numNew;
		return numNew != 0;
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		rangeCheckForAdd(index);
		final Object[] a = c.toArray();
		int numNew = a.length;
		ensureCapacityInternal(size + numNew);
		int numMoved = size - index;
		if (numMoved > 0)
			System.arraycopy(elementData, index, elementData, index + numNew, numMoved);

		System.arraycopy(a, 0, elementData, index, numNew);
		size += numNew;
		return numNew != 0;
	}

	protected void removeRange(int fromIndex, int toIndex) {
		final int numMoved = size - toIndex;
		System.arraycopy(elementData, toIndex, elementData, fromIndex, numMoved);
		final int newSize = size - (toIndex-fromIndex);
		for (int i = newSize; i < size; i++) {
			elementData[i] = null;
		}
		size = newSize;
	}

	private void rangeCheck(int index) {
		if (index >= size)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	private void rangeCheckForAdd(final int index) {
		if (index > size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	private String outOfBoundsMsg(final int index) {
		return "Index: "+index+", Size: "+size;
	}

	public boolean removeAll(final Collection<?> c) {
		return batchRemove(c, false);
	}

	public boolean retainAll(final Collection<?> c) {
		return batchRemove(c, true);
	}

	private boolean batchRemove(final Collection<?> c, final boolean complement) {
		final Object[] elementData = this.elementData;
		int r = 0, w = 0;
		boolean modified = false;
		try {
			for (; r < size; r++)
				if (c.contains(elementData[r]) == complement)
					elementData[w++] = elementData[r];
		} finally {
			if (r != size) {
				System.arraycopy(elementData, r, elementData, w, size - r);
				w += size - r;
			}
			if (w != size) {
				for (int i = w; i < size; i++)
					elementData[i] = null;
				size = w;
				modified = true;
			}
		}
		return modified;
	}

	private void writeObject(final java.io.ObjectOutputStream s) throws java.io.IOException{
		s.defaultWriteObject();
		s.writeInt(size);
		for (int i=0; i<size; i++) 
			s.writeObject(elementData[i]);
	}

	private void readObject(final java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {
		elementData = EMPTY_ELEMENTDATA;
		s.defaultReadObject();
		s.readInt(); 
		if (size > 0) {
			int capacity = calculateCapacity(elementData, size);
			SharedSecrets.getJavaOISAccess().checkArray(s, Object[].class, capacity);
			ensureCapacityInternal(size);
			final Object[] a = elementData;
			for (int i=0; i<size; i++) 
				a[i] = s.readObject();
		}
	}

	public ListIterator<E> listIterator(final int index) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException("Index: "+index);
		return new ListItr(index);
	}

	public ListIterator<E> listIterator() {
		return new ListItr(0);
	}

	public Iterator<E> iterator() {
		return new Itr();
	}


	private class Itr implements Iterator<E> {
		int cursor;      
		int lastRet = -1; 

		Itr() {}

		public boolean hasNext() {
			return cursor != size;
		}

		@SuppressWarnings("unchecked")
		public E next() {
			final int i = cursor;
			if (i >= size)
				throw new NoSuchElementException();
			final Object[] elementData = FastList.this.elementData;
			if (i >= elementData.length)
				throw new ConcurrentModificationException();
			cursor = i + 1;
			return (E) elementData[lastRet = i];
		}

		public void remove() {
			if (lastRet < 0)
				throw new IllegalStateException();
			try {
				FastList.this.remove(lastRet);
				cursor = lastRet;
				lastRet = -1;
			} catch (IndexOutOfBoundsException ex) {
				throw new ConcurrentModificationException();
			}
		}

		@Override
		@SuppressWarnings("unchecked")
		public void forEachRemaining(final Consumer<? super E> consumer) {
			final int size = FastList.this.size;
			int i = cursor;
			if (i >= size) {
				return;
			}
			final Object[] elementData = FastList.this.elementData;
			if (i >= elementData.length) {
				throw new ConcurrentModificationException();
			}
			while (i != size) 
				consumer.accept((E) elementData[i++]);
			cursor = i;
			lastRet = i - 1;
		}
	}

	private class ListItr extends Itr implements ListIterator<E> {
		ListItr(int index) {
			super();
			cursor = index;
		}

		public boolean hasPrevious() {
			return cursor != 0;
		}

		public int nextIndex() {
			return cursor;
		}

		public int previousIndex() {
			return cursor - 1;
		}

		@SuppressWarnings("unchecked")
		public E previous() {
			int i = cursor - 1;
			if (i < 0)
				throw new NoSuchElementException();
			final Object[] elementData = FastList.this.elementData;
			if (i >= elementData.length)
				throw new ConcurrentModificationException();
			cursor = i;
			return (E) elementData[lastRet = i];
		}

		public void set(final E e) {
			if (lastRet < 0)
				throw new IllegalStateException();
			try {
				FastList.this.set(lastRet, e);
			} catch (final IndexOutOfBoundsException ex) {
				throw new ConcurrentModificationException();
			}
		}

		public void add(final E e) {
			try {
				final int i = cursor;
				FastList.this.add(i, e);
				cursor = i + 1;
				lastRet = -1;
			} catch (IndexOutOfBoundsException ex) {
				throw new ConcurrentModificationException();
			}
		}
	}

	//TODO: Make real no copy sub
	public List<E> subList(int fromIndex, int toIndex) {
		return GfCollections.wrapAsCollection(this).range(fromIndex, toIndex-fromIndex);
	}

	@Override
	public void forEach(final Consumer<? super E> action) {
		@SuppressWarnings("unchecked")
		final E[] elementData = (E[]) this.elementData;
		final int size = this.size;
		for (int i=0; i < size; i++) {
			action.accept(elementData[i]);
		}
	}

	@Override
	public Spliterator<E> spliterator() {
		return new FastListSpliterator<E>(this, 0, -1);
	}

	private static final class FastListSpliterator<E> implements Spliterator<E> {
		private final FastList<E> list;
		private int index; 
		private int fence;

		FastListSpliterator(final FastList<E> list, final int origin, final int fence) {
			this.list = list; 
			this.index = origin;
			this.fence = fence;
		}

		private int getFence() {
			int hi;
			FastList<E> lst;
			if ((hi = fence) < 0) {
				if ((lst = list) == null)
					hi = fence = 0;
				else {
					hi = fence = lst.size;
				}
			}
			return hi;
		}

		public FastListSpliterator<E> trySplit() {
			final int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
		return (lo >= mid) ? null : new FastListSpliterator<E>(list, lo, index = mid);
		}

		public boolean tryAdvance(final Consumer<? super E> action) {
			if (action == null)
				throw new NullPointerException();
			int hi = getFence(), i = index;
			if (i < hi) {
				index = i + 1;
				@SuppressWarnings("unchecked") E e = (E)list.elementData[i];
				action.accept(e);
				return true;
			}
			return false;
		}

		public void forEachRemaining(final Consumer<? super E> action) {
			int i, hi;
			final FastList<E> lst; 
			Object[] a;
			if (action == null)
				throw new NullPointerException();
			if ((lst = list) != null && (a = lst.elementData) != null) {
				if ((hi = fence) < 0) {
					hi = lst.size;
				}
				if ((i = index) >= 0 && (index = hi) <= a.length) {
					for (; i < hi; ++i) {
						@SuppressWarnings("unchecked") E e = (E) a[i];
						action.accept(e);
					}
				}
			}
		}

		public long estimateSize() {
			return (long) (getFence() - index);
		}

		public int characteristics() {
			return Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;
		}
	}

	@Override
	public boolean removeIf(final Predicate<? super E> filter) {
		int removeCount = 0;
		final BitSet removeSet = new BitSet(size);
		final int size = this.size;
		for (int i=0; i < size; i++) {
			@SuppressWarnings("unchecked")
			final E element = (E) elementData[i];
			if (filter.test(element)) {
				removeSet.set(i);
				removeCount++;
			}
		}

		final boolean anyToRemove = removeCount > 0;
		if (anyToRemove) {
			final int newSize = size - removeCount;
			for (int i=0, j=0; (i < size) && (j < newSize); i++, j++) {
				i = removeSet.nextClearBit(i);
				elementData[j] = elementData[i];
			}
			for (int k=newSize; k < size; k++) {
				elementData[k] = null;  // Let gc do its work
			}
			this.size = newSize;
		}
		return anyToRemove;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void replaceAll(final UnaryOperator<E> operator) {
		final int size = this.size;
		for (int i=0; i < size; i++) 
			elementData[i] = operator.apply((E) elementData[i]);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void sort(final Comparator<? super E> c) {
		Arrays.sort((E[]) elementData, 0, size, c);
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		for(final Object o : c) 
			if (!this.contains(o))
				return false;

		return true;
	}
}
