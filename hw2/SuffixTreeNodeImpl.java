package Assignment2;

public class SuffixTreeNodeImpl extends SuffixTreeNode {

	
    public SuffixTreeNodeImpl() {
        super();
    }

    public SuffixTreeNodeImpl(CharLinkedList chars, SuffixTreeNode parent) {
        super(chars,parent);
    }
	
	
	@Override
	public SuffixTreeNode search(char c) {
		return binarySearch(c, 0, this.getNumOfChildren());
	}

	@Override
	public SuffixTreeNode binarySearch(char target, int left, int right) {
		if (left>right)
			return null;
		
		int middle=(left+right)/2;
		if (this.getChildren()[middle]==null)
			return null;
		
		char curr=this.getChildren()[middle].getChars().getFirst().getChar();
		if (curr==target)
			return this.getChildren()[middle];
		if (curr<target)
			return binarySearch(target, middle+1, right);
		return binarySearch(target, left, middle-1);
	}

	@Override
	public void shiftChildren(int until) {
		
		SuffixTreeNode[] A=new SuffixTreeNode[ALPHABET_LENGTH];
		A[0]=null;
		for (int i=1;i<until;i++)
		{
			A[i]=this.getChildren()[i-1];
		}
		this.setChildren(A, this.getNumOfChildren());		
		
	}

	@Override
	public void addChild(SuffixTreeNode node) {
		
		char nodeChar=node.getChars().getFirst().getChar();		
		if (nodeChar=='$' || nodeChar=='#')
		{
			this.children[this.getNumOfChildren()]=node;
			this.setChildren(this.children, this.getNumOfChildren()+1);
			if (this.descendantLeaves==0)
				this.descendantLeaves++;
			node.descendantLeaves++;
		}
		else
		{
			int counter=0;
			
			for (int i=0;i<this.getNumOfChildren();i++)
			{
				char curr=this.getChildren()[i].getChars().getFirst().getChar();
				if (curr!='$' && curr!='#' && curr<node.getChars().getFirst().getChar())
					counter++;
				else
					break;
			}
			SuffixTreeNode[] A=new SuffixTreeNode[this.getChildren().length];
			for (int i=0;i<counter;i++)
			{
				A[i]=this.getChildren()[i];
			}
			A[counter]=node;
			for (int i=counter+1;i<A.length;i++)
				A[i]=this.getChildren()[i-1];
			this.setChildren(A, this.getNumOfChildren()+1);
		}
		
		SuffixTreeNode parentNode=this.getParent();
		if (this.getNumOfChildren()>1)
		{
			this.descendantLeaves++;
			while (parentNode!=null)
			{
				parentNode.descendantLeaves++;
				parentNode=parentNode.parent;
			}
		}
		else
			this.descendantLeaves=1;
			
		
		
	}

	
	@Override
	public void addSuffix(char[] word, int from) {
		
		/*
		SuffixTreeNode a = new SuffixTreeNodeImpl();
		if (from == word.length) a=null;                   // HELP EXIT CONDITION
		else a = search(word[from]);
		//else a = search(word[from]);
		if(a!=null) a.addSuffix(word, from+1);
		else if (word.length!=from) {                      // EXIT CONDITION (a==null && word.length == from)
			CharLinkedList b = new CharLinkedListImpl();
			b.add(word[from]);
			a = new SuffixTreeNodeImpl(b, this);
			a.totalWordLength = totalWordLength + 1;
			addChild(a);
			a.addSuffix(word, from+1);
		}*/
		if (from==word.length)
			return;
		char c = word[from];
		SuffixTreeNode p=this.search(c);
		if (p==null)
		{
			CharLinkedList listWord=new CharLinkedListImpl();
			listWord.add(word[from]);
			p=new SuffixTreeNodeImpl(listWord, this);
			//p.totalWordLength = totalWordLength + 1;
			this.addChild(p);
		}
		p.addSuffix(word, from+1);
		
		
	}

	@Override
	public void compress() {
		
		
		if (this.getNumOfChildren()==0)
			return;
		
		if ( (this.getChars()==null) || (this.getNumOfChildren()>1))
		{
			for (int i=0;i<this.getNumOfChildren();i++)
				this.getChildren()[i].compress();
			return;
		}
		
		//in case this have only 1 child
		this.getChars().append(this.getChildren()[0].getChars());
		totalWordLength = totalWordLength + children[0].chars.size();
		this.setChildren(this.getChildren()[0].getChildren(), this.getChildren()[0].getNumOfChildren());
		this.compress();
		
	}

	//@Override
	public int numOfOccurrences(char[] subword, int from) {
		
		if (from==subword.length)
			return this.getDescendantLeaves();
		ICharLinkedListNode p;
		if (this.getChars()!=null)
			p=this.getChars().getFirst();
		else
			p=null;
		char curr;
		while (p!=null)
		{
			curr=p.getChar();
			if (from==subword.length)
				return this.getDescendantLeaves();
			
			if (curr!=subword[from])
				return 0;
			p=p.getNext();
			from++;
		}
		if (from==subword.length)
			return this.getDescendantLeaves();
		
		SuffixTreeNodeImpl child=(SuffixTreeNodeImpl)this.search(subword[from]);
		if (child==null)
			return 0;
		
		return child.numOfOccurrences(subword, from);
		
		
	}
	
	

	@Override
	public SuffixTreeNode findSuffixLeaf(char[] word, int from) {
		if (from>=word.length)
			return this;
		
		CharLinkedList chars=this.getChars();
		ICharLinkedListNode p;

		if (chars!=null)
			p=chars.getFirst();
		else
			p=null;
		char curr;
		while(p!=null)
		{
			curr=p.getChar();
			if ( (curr=='$' || curr=='#') && (from>=word.length) )
				return this;
			if (from==word.length || curr!=word[from])
				return null;
			from++;
			p=p.getNext();
		}
		if (from>=word.length)
		{
			return this;
		}
		SuffixTreeNode child=this.search(word[from]);
		if (child==null)
			return null;
		
		return child.findSuffixLeaf(word, from);
		
		
	}

	public SuffixTreeNode findLCA(SuffixTreeNode node2) {
		
		if (this==null || node2==null)
			return null;
		if(node2.getTotalWordLength()==this.getTotalWordLength() && node2==this) return this;
		if(node2.getTotalWordLength()>this.getTotalWordLength()) return node2.getParent().findLCA(this);
		else return this.getParent().findLCA(node2);
		
		
	}
}
