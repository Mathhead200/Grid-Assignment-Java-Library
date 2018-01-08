package com.mathhead200.logic;

import java.util.Map;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;


public class SymbolicStatement
{
	private String statement;
	private List<Character> subjects;

	public SymbolicStatement(String s) {
		statement = s.replaceAll("\\s+", "");
		subjects = new ArrayList<Character>();
		for( char c : statement.toCharArray() )
			if( Character.getType(c) == Character.UPPERCASE_LETTER )
				subjects.add(c);
	}


	private static boolean getBooleanBefore(String str, int pos) {
		for( int i = pos - 1; i >= 0; i-- )
			if( Boolean.parseBoolean( str.substring(i, pos) ) )
				return true;
		return false;
	}

	private static boolean getBooleanAfter(String str, int pos) {
		return Boolean.parseBoolean( str.substring(pos + 1, pos + 5) );
	}


	public static boolean evaluate(String s) {
		//() parenthesis
		//...

		//~ negation
		for( int i = 0; i < s.length(); i++ ) {
			if( s.charAt(i) == '~' || s.charAt(i) == '!' || s.charAt(i) == '-' ) {
				boolean b = getBooleanAfter(s, i);
				int offset = (b + "").length();
				s = s.substring(0, i) + (!b + "").toUpperCase() + s.substring(i + 1 + offset);
				i += offset;
			}
		}

		//* v > = and or conditional equivalence
		for( int i = 0; i < s.length(); i++ ) {
			if( s.charAt(i) == '*' || s.charAt(i) == '&' ) {

			} else if( s.charAt(i) == 'v' || s.charAt(i) == '|' ) {

			} else if( s.charAt(i) == '>' || s.charAt(i) == '?' ) {

			} else if( s.charAt(i) == '=' ) {

			}
		}
		//TEMP RETURN...!
		return false;
	}

	public Map<String, Boolean> getTruthTable() {
		Hashtable<String, Boolean> tt = new Hashtable<String, Boolean>();
		for( int i = 0; i < Math.pow(subjects.size(), 2); i++ ) {
			String key = Integer.toBinaryString(i);
			while( key.length() < subjects.size() )
				key = "0" + key;

			String newStatement = statement + ""; //to avoid making "newStatement" change "statement"
			for( int z = 0; z < subjects.size(); z++ )
				newStatement.replaceAll( subjects.get(z) + "", key.charAt(z) == '1' ? "TRUE" : "FALSE" );
			tt.put( key, evaluate(newStatement) );
		}
		return tt;
	}

	public String toString() {
		return statement;
	}
}
