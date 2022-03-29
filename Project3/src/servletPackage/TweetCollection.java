package servletPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TweetCollection
{
	private HashMap<Long, Tweet> tweets;
	private String filename;
	/**
	 * The maximum number of tweets to print in toString().
	 */
	private static final int MAX_TWEETS_TO_PRINT = 200;

	/**
	 * Constructs a new TweetCollection from the given input file. The
	 * collection size will be adjusted based on the passed-in initSize.
	 * 
	 * @param fn The file name to read Tweet data from.
	 * @param initSize The expected number of tweets in the input file.
	 */
	public TweetCollection(String fn, int initSize)
	{
		tweets = new HashMap<Long, Tweet>(3 * initSize / 2);
		filename = fn;
		readFile();
	}

	/**
	 * Reads Tweet data from this TweetCollection's input file.
	 */
	private void readFile()
	{
		BufferedReader lineReader = null;
		try {
			lineReader = new BufferedReader(new FileReader(filename));
			String line = null;
			int lineNum = 0;
			while ((line = lineReader.readLine()) != null) {
				lineNum++;
				try {
					String[] tokens = line.split(",");
					// note: if tokens.length < 4, an IndexOutOfBoundsException
					// will trigger the catch below
					int polarity = Integer.parseInt(tokens[0]);
					long id = Long.parseLong(tokens[1]);
					tweets.put(id,
							new Tweet(polarity, id, tokens[2], tokens[3]));
				} catch (Exception e) {
					System.err.println("Format error at line " + lineNum + ".");
				}
			}
		} catch (Exception e) {
			System.err.println(
					"Error reading file. Trying alternative read method.");
			try {
				lineReader = new BufferedReader(
						new InputStreamReader(this.getClass()
								.getResourceAsStream(filename.substring(1))));
				String line = null;
				int lineNum = 0;
				while ((line = lineReader.readLine()) != null) {
					lineNum++;
					try {
						String[] tokens = line.split(",");
						// note: if tokens.length < 4, an
						// IndexOutOfBoundsException
						// will trigger the catch below
						int polarity = Integer.parseInt(tokens[0]);
						long id = Long.parseLong(tokens[1]);
						tweets.put(id,
								new Tweet(polarity, id, tokens[2], tokens[3]));
					} catch (Exception e2) {
						System.err.println(
								"Format error at line " + lineNum + ".");
					}
				}
			} catch (Exception e2) {
				System.err.println(
						"Error reading file. File is missing or malformed.");
			} finally {
				if (lineReader != null) {
					try {
						lineReader.close();
					} catch (Exception e2) {
						System.err.println("Could not close BufferedReader.");
					}
				}
			}
		} finally {
			if (lineReader != null) {
				try {
					lineReader.close();
				} catch (Exception e) {
					System.err.println("Could not close BufferedReader.");
				}
			}
		}
	}

	/**
	 * Returns the number of tweets in this TweetCollection.
	 * 
	 * @return The number of tweets in this TweetCollection.
	 */
	public int size()
	{
		return tweets.size();
	}

	/**
	 * Adds a Tweet to this TweetCollection.
	 * 
	 * @param t The Tweet to add.
	 */
	public void addTweet(Tweet t)
	{
		tweets.put(t.getId(), t);
	}

	/**
	 * Removes the Tweet with the input ID from this TweetCollection.
	 * 
	 * @param id The Tweet ID to remove.
	 * @return The removed Tweet, or null if no tweet with the input ID exists
	 * in this TweetCollection.
	 */
	public Tweet removeTweet(long id)
	{
		return tweets.remove(id);
	}

	/**
	 * Returns the Tweet with the input ID. Or, returns null if it does not
	 * exist in this TweetCollection.
	 * 
	 * @param id The Tweet ID to search for.
	 * @return The Tweet with the input ID, or null if it does not exist in this
	 * TweetCollection.
	 */
	public Tweet getTweetById(long id)
	{
		return tweets.get(id);
	}

	/**
	 * Returns a collection of Tweet IDs by the input username.
	 * 
	 * @param username The username to search for.
	 * @return A collection of Tweet IDs by the input username.
	 */
	public Set<Long> getTweetIdsByUser(String username)
	{
		Set<Long> ret = new HashSet<Long>();
		for (Tweet t: tweets.values()) {
			if (t.getUser().equals(username)) {
				ret.add(t.getId());
			}
		}
		return ret;
	}

	/**
	 * Returns a collection of all Tweet IDs in this TweetCollection.
	 * 
	 * @return A collection of all Tweet IDs in this TweetCollection.
	 */
	public Set<Long> getAllTweetIds()
	{
		return tweets.keySet();
	}

	/**
	 * Returns the predicted polarity of the input Tweet.
	 * 
	 * @param getTweet The tweet to predict the polarity of.
	 * @return The predicted polarity of the input Tweet.
	 */
	public int predict1(String getTweet)
	{
		return 4;
	}
	public int predict(Tweet t)
	{
		// For now, just returns 4
		return 4;
	}

	/**
	 * Returns the percent accuracy of this TweetCollection's predictions. The
	 * test is performed using another TweetCollection for test data.
	 * 
	 * @param test The TweetCollection providing testing data.
	 * @return The percent accuracy of the test.
	 */
	public double collectionTest(TweetCollection test)
	{
		int correct = 0;
		for (Long l: test.getAllTweetIds()) {
			Tweet t = test.getTweetById(l);
			int pred = predict(t);
			if (pred == t.getPolarity()) {
				correct++;
			}
		}
		return (double)correct / test.size();
	}

	/**
	 * Rewrites the file this TweetCollection is based on, if any.
	 */
	public void rewriteFile()
	{
		if (filename != null) {
			writeFile(filename);
		}
	}

	/**
	 * Writes the data in this TweetCollection to the input filename.
	 * 
	 * @param fn The filename to write to.
	 */
	public void rewriteFile(String fn)
	{
		writeFile(fn);
	}

	/**
	 * Writes Tweet data to the given file.
	 * 
	 * @param fn The file name to write to.
	 */
	private void writeFile(String fn)
	{
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fn));
			for (Tweet t: tweets.values()) {
				out.write(t.getPolarity() + ",");
				out.write(t.getId() + ",");
				out.write(t.getUser() + ",");
				out.write(t.getText() + "\n");
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Failed to write to " + fn);
		}
	}

	/**
	 * Returns a String representation of this TweetCollection.
	 * 
	 * @return a String representation of this TweetCollection.
	 */
	public String toString()
	{
		String ret = tweets.size() + " tweets";
		Collection<Tweet> values = tweets.values();
		if (tweets.size() < MAX_TWEETS_TO_PRINT) {
			ret += ":\n";
			for (Tweet t: values) {
				ret += t + "\n";
			}
		} else {
			ret += " (only " + MAX_TWEETS_TO_PRINT + " are shown):\n";
			int numTweets = 0;
			for (Tweet t: values) {
				ret += t + "\n";
				numTweets++;
				if (numTweets >= MAX_TWEETS_TO_PRINT) {
					break;
				}
			}
		}
		return ret.substring(0, ret.length() - 1); // trim last \n
	}
}