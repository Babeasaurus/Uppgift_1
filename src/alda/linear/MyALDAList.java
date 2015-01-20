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
					remove(head, null);
					lastReturned = null;
				}else{
					throw new IllegalStateException("The item has already been removed!");
				}
			}
			private void remove(Node<T> current, Node<T> prev){
				if(current.data.equals(lastReturned.data)){
					if(prev == null){
						head = head.next;
					}else{
						prev.next = current.next;
					}
				}else{
					if(current.next != null){
						remove(current.next, current);
					}
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
			toRemove = getNode(index);
			getNode(index -1).next = toRemove.next;
		}
		return toRemove.data;
	}

	@Override
	public boolean remove(T element) {
		return remove(element, head, null);
	}
	private boolean remove(T element, Node<T> currentNode, Node<T> prevNode){
		if(currentNode == null){
			return false;
		} else if(currentNode.data.equals(element)){
			if(prevNode == null){
				head = currentNode.next;
			} else{
				prevNode.next = currentNode.next;
			}
			return true;
		}
		else{
			return remove(element, currentNode.next, currentNode);
		}
	}

	private Node<T> getNode(int index){
		if(index < 0 || index > size() -1)
			throw new IndexOutOfBoundsException("Index: " + index + " is not part of the list");
		return getNode(head, 0, index);
	}
	private Node<T> getNode(Node<T> currentNode, int count, int index){
		if(currentNode == null)
			return null;
		else if(count == index)
			return currentNode;
		else
			return getNode(currentNode.next, ++count, index);
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
		return indexOf(element, head, 0);
	}
	private int indexOf(T element, Node<T> nextNode, int nextIndex){
		if(element.equals(nextNode.data))
			return nextIndex;
		else if(nextNode.next != null)
			return indexOf(element, nextNode.next, ++nextIndex);
		else
			return -1;
	}

	@Override
	public void clear() {
		head = null;
	}	

	@Override
	public int size() {
		return countNodes(head, 0);
	}	
	private int countNodes(Node<T> current, int count){
		if(current == null)
			return count;
		else if(current.next == null)
			return ++count;
		else
			return countNodes(current.next, ++count);
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

	private static class Node<T>{
		T data;
		Node<T> next;

		public Node(T data){
			this.data = data;
		}
	}

	

}
