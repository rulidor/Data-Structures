package Assignment2;

public class CharLinkedListNodeImpl implements ICharLinkedListNode {

	private char data;
	private CharLinkedListNodeImpl next;
	
	
	public CharLinkedListNodeImpl()
	{
		this.data='a';
		this.next=null;
	}
	
	public CharLinkedListNodeImpl(char data)
	{
		this.data=data;
		this.next=null;
	}
	
	public CharLinkedListNodeImpl(char data, CharLinkedListNodeImpl next)
	{
		this.data=data;
		this.next=next;
	}
	
	
	@Override
	public char getChar() {
		return this.data;
	}

	@Override
	public ICharLinkedListNode getNext() {
		return this.next;
	}

	@Override
	public void setNext(ICharLinkedListNode next) {
		this.next=(CharLinkedListNodeImpl)next;		
	}
	
	public void setData(char c)
	{
		this.data=c;
	}

}
