package servletPackage;

/**
 * Class to represent Tweets for sentiment analysis.
 * 
 * @author Archer Murray
 */
public class Tweet
{
	private int polarity;
	private long id;
	private String user;
	private String text;

	/**
	 * Constructs a Tweet with the given parameters.
	 * 
	 * @param polarity The polarity of the Tweet. 0 = negative, 2 = neutral, 4 =
	 * positive.
	 * @param id The Tweet ID.
	 * @param user The name of the user that tweeted the Tweet.
	 * @param text The text of the Tweet.
	 */
	public Tweet(int polarity, long id, String user, String text)
	{
		this.polarity = polarity;
		this.id = id;
		this.user = user;
		this.text = text;
	}

	/**
	 * Getter and Setter of the polarity of this Tweet.
	 */
	public int getPolarity()
	{
		return this.polarity;
	}
	public void setPolarity(int polarity) {
		this.polarity = polarity;
	}

	/**
	 *  Getter and Setter of the ID of this Tweet.
	 */
	public long getId()
	{
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}

	/**
	 *  Getter and Setter of the username who posted this Tweet.
	 */
	public String getUser()
	{
		return this.user;
	}
	public void setUser(String user) {
		this.user = user;
	}	

	/**
	 *  Getter and Setter of the text of this Tweet.
	 */
	public String getText()
	{
		return this.text;
	}
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Test Tweets for equality based on Ids
	 */
	public boolean equals (Object rhs) {
		Tweet rhsT = (Tweet) rhs;
		return this.getId() == rhsT.getId();
	}
	
	/**
	 * Compares two Tweets base on Ids
	 */
	public int compareTo (Object rhs) {
		Tweet rhsT = (Tweet) rhs;
		if (this.getId() == rhsT.getId()) {
			return 0;
		}
		if (this.getId() > rhsT.getId()) {
			return 1;
		}
		return -1;
	}	
	
	/**
	 * Returns a String representation of this Tweet.
	 * 
	 * @return A String representation of this Tweet.
	 */
	public String toString()
	{
		return "[ID: " + this.id + ", polarity: " + this.polarity + "] "
				+ this.user + ": " + this.text;
	}
}