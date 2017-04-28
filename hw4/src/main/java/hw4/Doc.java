package hw4;

public class Doc {
	double amount;
	String term;
	
	public Doc(String word, double num)
	{
		term = word;
		amount = num;
		
	}
	
	double getAmount()
	{
		return amount;
	}
	
	 void setAmount(double num)
	{
		amount = num;
	}
	
	 String getDoc()
	{
		return term;
	}
}
