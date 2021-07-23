package Assignment2;

public class CharLinkedListImpl extends CharLinkedList{

	public CharLinkedListImpl() {
		super();
	}
	
	@Override
	public void add(char c) {
		CharLinkedListNodeImpl p=new CharLinkedListNodeImpl(c,null);
		if (this.getFirst()==null)
		{
			this.first=p;
			this.last=p;
		}
		else
		{
		this.last.setNext(p);
		this.last=p;
		}
	}

	@Override
	public char firstChar() {
		return this.getFirst().getChar();
	}

	@Override
	public int size() {
		ICharLinkedListNode p=this.getFirst();
		int counter=1;
		if (p==null)
			return 0;
		
		while (p.getNext()!=null)
		{
			p=p.getNext();
			counter++;
		}
		return counter;
	}

	@Override
	public void append(CharLinkedList toAppend) {
		

		if (toAppend.first==null)
			return;
		if (this.getFirst()==null)
		{
			this.first=toAppend.first;
			this.last=toAppend.last;
		}
		else
		{
		this.last.setNext(toAppend.getFirst());	
		this.last=toAppend.getLast();
		}
	}

}
