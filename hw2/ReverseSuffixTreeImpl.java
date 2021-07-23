package Assignment2;

public class ReverseSuffixTreeImpl extends ReverseSuffixTree{

	public ReverseSuffixTreeImpl(String word)
	{
		super(word);
	}
	
	@Override
	public String longestPalindrome() {

		String maxEvenPal=this.longestEvenPalindrome();
		String maxOddPal=this.longestOddPalindrome();
		if (maxEvenPal.length()>maxOddPal.length())
			return maxEvenPal;
		return maxOddPal;
	}
	
	
	public String longestEvenPalindrome()
	{	
		String maxPal="";
		int index;
		for (int i=1;i<this.word.length-1;i++)
		{
			
			char[] A_Reversed=new char[i];
			char[] B=new char[word.length-i-1];
			index=0;
			for (int j=i-1;j>=0;j--)
			{
				A_Reversed[index]=this.word[j];
				index++;
			}
			index=0;
			for (int j=i;j<this.word.length-1;j++)
			{
				B[index]=this.word[j];
				index++;
			}
			
			SuffixTreeNode ALeaf=this.getRoot().findSuffixLeaf(A_Reversed, 0);
			SuffixTreeNode BLeaf=this.getRoot().findSuffixLeaf(B, 0);
			if (ALeaf==null || BLeaf==null)
				continue;
			SuffixTreeNode LCP=ALeaf.findLCA(BLeaf);
			
			if ( (LCP !=null) && ( (LCP.restoreStringAlongPath().length()*2) > maxPal.length() ) )
			{
				String StringLCP=LCP.restoreStringAlongPath();
				String LCP_String_Reversed=new StringBuilder(StringLCP).reverse().toString();
				maxPal=LCP_String_Reversed + StringLCP;
			}
		}
		return maxPal;
	}
	
	public String longestOddPalindrome()
	{
		String maxPal="X";
		
		int index;
		for (int i=2;i<word.length;i++)
		{
			char[] A_Reversed=new char[i-1];
			char[] B=new char[word.length-i-1];
			
			index=0;
			for (int j=i-2;j>=0;j--)
			{
				A_Reversed[index]=this.word[j];
				index++;
			}
			
			index=0;
			
			for (int j=i;j<this.word.length-1;j++)
			{
				B[index]=this.word[j];
				index++;
			}
			
			SuffixTreeNode ALeaf=this.getRoot().findSuffixLeaf(A_Reversed, 0);
			SuffixTreeNode BLeaf=this.getRoot().findSuffixLeaf(B, 0);
			
			if (ALeaf==null || BLeaf==null)
				continue;
			SuffixTreeNode LCP=ALeaf.findLCA(BLeaf);
			
			if ( (LCP!=null) && ( (LCP.getTotalWordLength()*2 + 1) > maxPal.length() ) )
			{
				String StringLCP=LCP.restoreStringAlongPath();
				String LCP_String_Reversed=new StringBuilder(StringLCP).reverse().toString();
				maxPal=LCP_String_Reversed + "X" + StringLCP;
			}
		}
		return maxPal;
	}
	

}
