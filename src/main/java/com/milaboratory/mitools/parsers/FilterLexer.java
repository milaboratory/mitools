// Generated from com/milaboratory/mitools/parsers/Filter.g4 by ANTLR 4.3
package com.milaboratory.mitools.parsers;

import com.milaboratory.mitools.processing.SequenceProcessor;
import static com.milaboratory.mitools.processing.ProcessingBlocks.*;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FilterLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__18=1, T__17=2, T__16=3, T__15=4, T__14=5, T__13=6, T__12=7, T__11=8, 
		T__10=9, T__9=10, T__8=11, T__7=12, T__6=13, T__5=14, T__4=15, T__3=16, 
		T__2=17, T__1=18, T__0=19, MethodName=20, Int=21, Ws=22;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", 
		"'\\u0013'", "'\\u0014'", "'\\u0015'", "'\\u0016'"
	};
	public static final String[] ruleNames = {
		"T__18", "T__17", "T__16", "T__15", "T__14", "T__13", "T__12", "T__11", 
		"T__10", "T__9", "T__8", "T__7", "T__6", "T__5", "T__4", "T__3", "T__2", 
		"T__1", "T__0", "MethodName", "Int", "Ws"
	};


	public FilterLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Filter.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\30|\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\3\2\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3"+
		"\b\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\25\6\25m\n\25\r\25\16\25n\3\26\6\26r"+
		"\n\26\r\26\16\26s\3\27\6\27w\n\27\r\27\16\27x\3\27\3\27\2\2\30\3\3\5\4"+
		"\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22"+
		"#\23%\24\'\25)\26+\27-\30\3\2\5\4\2C\\c|\3\2\62;\5\2\13\f\17\17\"\"~\2"+
		"\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2"+
		"\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2"+
		"\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2"+
		"\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\3/\3\2\2\2"+
		"\5\61\3\2\2\2\7\67\3\2\2\2\t9\3\2\2\2\13>\3\2\2\2\rA\3\2\2\2\17D\3\2\2"+
		"\2\21F\3\2\2\2\23I\3\2\2\2\25L\3\2\2\2\27N\3\2\2\2\31P\3\2\2\2\33X\3\2"+
		"\2\2\35[\3\2\2\2\37]\3\2\2\2!_\3\2\2\2#a\3\2\2\2%c\3\2\2\2\'e\3\2\2\2"+
		")l\3\2\2\2+q\3\2\2\2-v\3\2\2\2/\60\7S\2\2\60\4\3\2\2\2\61\62\7o\2\2\62"+
		"\63\7g\2\2\63\64\7c\2\2\64\65\7p\2\2\65\66\7*\2\2\66\6\3\2\2\2\678\7U"+
		"\2\28\b\3\2\2\29:\7o\2\2:;\7k\2\2;<\7p\2\2<=\7*\2\2=\n\3\2\2\2>?\7~\2"+
		"\2?@\7~\2\2@\f\3\2\2\2AB\7@\2\2BC\7?\2\2C\16\3\2\2\2DE\7=\2\2E\20\3\2"+
		"\2\2FG\7(\2\2GH\7(\2\2H\22\3\2\2\2IJ\7?\2\2JK\7?\2\2K\24\3\2\2\2LM\7>"+
		"\2\2M\26\3\2\2\2NO\7@\2\2O\30\3\2\2\2PQ\7h\2\2QR\7k\2\2RS\7n\2\2ST\7v"+
		"\2\2TU\7g\2\2UV\7t\2\2VW\7*\2\2W\32\3\2\2\2XY\7>\2\2YZ\7?\2\2Z\34\3\2"+
		"\2\2[\\\7F\2\2\\\36\3\2\2\2]^\7*\2\2^ \3\2\2\2_`\7+\2\2`\"\3\2\2\2ab\7"+
		"-\2\2b$\3\2\2\2cd\7\60\2\2d&\3\2\2\2ef\7u\2\2fg\7m\2\2gh\7k\2\2hi\7r\2"+
		"\2ij\7*\2\2j(\3\2\2\2km\t\2\2\2lk\3\2\2\2mn\3\2\2\2nl\3\2\2\2no\3\2\2"+
		"\2o*\3\2\2\2pr\t\3\2\2qp\3\2\2\2rs\3\2\2\2sq\3\2\2\2st\3\2\2\2t,\3\2\2"+
		"\2uw\t\4\2\2vu\3\2\2\2wx\3\2\2\2xv\3\2\2\2xy\3\2\2\2yz\3\2\2\2z{\b\27"+
		"\2\2{.\3\2\2\2\6\2nsx\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}