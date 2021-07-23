package Assignment2;

public class SuffixTreeImpl extends SuffixTree{

	public SuffixTreeImpl(String word)
	{
		super(word);
	}
	
	@Override
	public boolean contains(String subword) {
		
		return this.numOfOccurrences(subword)>0;
	}

	@Override
	public int numOfOccurrences(String subword) {
		SuffixTreeNodeImpl p=(SuffixTreeNodeImpl)this.getRoot();
		char[] array_subword = subword.toCharArray();
		return p.numOfOccurrences(array_subword, 0);
	}

}
