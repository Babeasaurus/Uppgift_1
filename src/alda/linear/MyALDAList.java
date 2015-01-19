package alda.linear;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyALDAList <T> implements ALDAList<T> {
	private Node<T> head;

	@Override
	public Iterator<T> iterator(){
		return new Iterator<T>() {
			Node<T> nextItem = head;
			Node<T> lastReturned = null;
			@Override
			public boolean hasNext() {
				return nextItem != null;
			}

			@Override
			public T next() {
				if(nextItem == null)
					throw new NoSuchElementException("The element doesn´t exist");
				T next = nextItem.data;
				lastReturned = nextItem;
				nextItem = nextItem.next;
				return next;
			}

			@Override
			public void remove(){
				if(lastReturned != null){
					Node<T> toRemove = null;
					if(head.data.equals(lastReturned.data)){
						toRemove = head;
						head = head.next;
						lastReturned = null;
					}else{
						Node<T> preToRemove = head;

						for(int i = 0; i < size(); i++){
							if(preToRemove.next != null){
								if(preToRemove.next.data.equals(lastReturned.data)){
									toRemove = preToRemove.next;
									preToRemove.next = toRemove.next;
									lastReturned = null;
									break;
								}else{
									preToRemove = preToRemove.next;
								}
							}else{
								break;
							}
						}
					}
				}else{
					throw new IllegalStateException("The item has already been removed!");
				}
			}	
		};
	}

	@Override
	public void add(T element) {
		
		if(head == null){
			head = new Node<T>(element);
		}else{
			Node<T> target = head;
			for(int i = 0; i < size() - 1; i++){
				target = target.next;
			}
			target.next = new Node<T>(element);
		}
	}

	@Override
	public void add(int index, T element) {
		if(index < 0 || index > size())
			throw new IndexOutOfBoundsException("Index: " + index + " is not part of the list");
		if(index == 0){
			Node<T> prev = head;
			head = new Node<T>(element);
			head.next = prev;
		}
		else{
			Node<T> prev = getNode(index -1);
			Node<T> newNode = new Node<T>(element);
			newNode.next = prev.next;
			prev.next = newNode;
		}
	}

	@Override
	public T remove(int index) {
		Node<T> toRemove;
		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException("Index: " + index + " is not part of the list");
		if(index == 0){
			toRemove = head;
			head = head.next;
		}else{
			Node<T> preToRemove = head;
			for(int i = 0; i < index - 1; i++){
				preToRemove = preToRemove.next;
			}
			toRemove = preToRemove.next;
			preToRemove.next = toRemove.next;
		}
		return toRemove.data;
	}

	@Override
	public boolean remove(T element) {
		Iterator<T> iter = iterator();
		while(iter.hasNext()){
			if(iter.next() == element){
				iter.remove();
				return true;
			}
		}
		return false;
	}

	private Node<T> getNode(int index){
		if(index < 0 || index > size() -1)
			throw new IndexOutOfBoundsException("Index: " + index + " is not part of the list");
		Node<T> target = head;
		for(int i = 0; i < index; i++){
			target = target.next;
		}
		return target;
	}

	@Override
	public T get(int index) {
		return getNode(index).data;
	}

	@Override
	public boolean contains(T element) {

		Iterator<T> iter = iterator();
		while(iter.hasNext()){
			if(iter.next().equals(element)){
				return true;
			}
		}
		return false;
	}

	@Override
	public int indexOf(T element) {
		int count = 0;
		Iterator<T> iter = iterator();
		while(iter.hasNext()){
			if(iter.next().equals(element)){
				return count;
			}
			count ++;
		}
		return -1;
	}

	@Override
	public void clear() {
		head = null;
	}

	@Override
	public int size() {
		int count = 0;
		Node<T> current = head;
		if(head != null){
			do{
				current = current.next;
				count++;
			}while(current != null);
		}
		return count;
	}

	private static class Node<T>{
		T data;
		Node<T> next;

		public Node(T data){
			this.data = data;
		}
	}

	@Override
	public String toString() {
		StringBuilder sr = new StringBuilder();
		sr.append("[" );
		for(int i = 0; i < size(); i++){
			sr.append(get(i).toString());
			if(i != size() -1)
				sr.append(", ");
		}
		sr.append("]");
		return sr.toString();
	}

}
