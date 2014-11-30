// Generated from com/milaboratory/mitools/parsers/Filter.g4 by ANTLR 4.3
package com.milaboratory.mitools.parsers;

import com.milaboratory.mitools.processing.SequenceProcessor;
import static com.milaboratory.mitools.processing.ProcessingBlocks.*;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FilterParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__18=1, T__17=2, T__16=3, T__15=4, T__14=5, T__13=6, T__12=7, T__11=8, 
		T__10=9, T__9=10, T__8=11, T__7=12, T__6=13, T__5=14, T__4=15, T__3=16, 
		T__2=17, T__1=18, T__0=19, MethodName=20, Int=21, Ws=22;
	public static final String[] tokenNames = {
		"<INVALID>", "'Q'", "'mean('", "'S'", "'min('", "'||'", "'>='", "';'", 
		"'&&'", "'=='", "'<'", "'>'", "'filter('", "'<='", "'D'", "'('", "')'", 
		"'+'", "'.'", "'skip('", "MethodName", "Int", "Ws"
	};
	public static final int
		RULE_actionSet = 0, RULE_description = 1, RULE_quality = 2, RULE_sequence = 3, 
		RULE_number = 4, RULE_logicalExpression = 5, RULE_sequenceProcessor = 6;
	public static final String[] ruleNames = {
		"actionSet", "description", "quality", "sequence", "number", "logicalExpression", 
		"sequenceProcessor"
	};

	@Override
	public String getGrammarFileName() { return "Filter.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public FilterParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ActionSetContext extends ParserRuleContext {
		public List<SequenceProcessor> processors;
		public SequenceProcessorContext is;
		public SequenceProcessorContext ss;
		public List<SequenceProcessorContext> sequenceProcessor() {
			return getRuleContexts(SequenceProcessorContext.class);
		}
		public TerminalNode EOF() { return getToken(FilterParser.EOF, 0); }
		public SequenceProcessorContext sequenceProcessor(int i) {
			return getRuleContext(SequenceProcessorContext.class,i);
		}
		public ActionSetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actionSet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterListener ) ((FilterListener)listener).enterActionSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterListener ) ((FilterListener)listener).exitActionSet(this);
		}
	}

	public final ActionSetContext actionSet() throws RecognitionException {
		ActionSetContext _localctx = new ActionSetContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_actionSet);

		     	((ActionSetContext)_localctx).processors =  new ArrayList<>();
		     
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(14); ((ActionSetContext)_localctx).is = sequenceProcessor(0);
			 _localctx.processors.add(_localctx.is.val); 
			setState(22);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(16); match(T__12);
					setState(17); ((ActionSetContext)_localctx).ss = sequenceProcessor(0);
					 _localctx.processors.add(_localctx.ss.val); 
					}
					} 
				}
				setState(24);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			setState(26);
			_la = _input.LA(1);
			if (_la==T__12) {
				{
				setState(25); match(T__12);
				}
			}

			setState(28); match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DescriptionContext extends ParserRuleContext {
		public RDescription val;
		public Token readIndex;
		public TerminalNode Int() { return getToken(FilterParser.Int, 0); }
		public DescriptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_description; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterListener ) ((FilterListener)listener).enterDescription(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterListener ) ((FilterListener)listener).exitDescription(this);
		}
	}

	public final DescriptionContext description() throws RecognitionException {
		DescriptionContext _localctx = new DescriptionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_description);
		try {
			setState(35);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(30); match(T__5);
				setState(31); ((DescriptionContext)_localctx).readIndex = match(Int);
				 ((DescriptionContext)_localctx).val =  d(i((((DescriptionContext)_localctx).readIndex!=null?((DescriptionContext)_localctx).readIndex.getText():null)) - 1); 
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(33); match(T__5);
				 ((DescriptionContext)_localctx).val =  d(0); 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QualityContext extends ParserRuleContext {
		public RQuality val;
		public QualityContext q1;
		public Token readIndex;
		public QualityContext q2;
		public QualityContext quality(int i) {
			return getRuleContext(QualityContext.class,i);
		}
		public List<QualityContext> quality() {
			return getRuleContexts(QualityContext.class);
		}
		public TerminalNode Int() { return getToken(FilterParser.Int, 0); }
		public QualityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quality; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterListener ) ((FilterListener)listener).enterQuality(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterListener ) ((FilterListener)listener).exitQuality(this);
		}
	}

	public final QualityContext quality() throws RecognitionException {
		return quality(0);
	}

	private QualityContext quality(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		QualityContext _localctx = new QualityContext(_ctx, _parentState);
		QualityContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_quality, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(38); match(T__18);
				setState(39); ((QualityContext)_localctx).readIndex = match(Int);
				 ((QualityContext)_localctx).val =  q(i((((QualityContext)_localctx).readIndex!=null?((QualityContext)_localctx).readIndex.getText():null)) - 1); 
				}
				break;

			case 2:
				{
				setState(41); match(T__18);
				 ((QualityContext)_localctx).val =  q(0); 
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(52);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new QualityContext(_parentctx, _parentState);
					_localctx.q1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_quality);
					setState(45);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(46); match(T__2);
					setState(47); ((QualityContext)_localctx).q2 = quality(2);
					 ((QualityContext)_localctx).val =  concat(_localctx.q1.val, _localctx.q2.val); 
					}
					} 
				}
				setState(54);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class SequenceContext extends ParserRuleContext {
		public RSequence val;
		public SequenceContext s1;
		public Token readIndex;
		public SequenceContext s2;
		public SequenceContext sequence(int i) {
			return getRuleContext(SequenceContext.class,i);
		}
		public List<SequenceContext> sequence() {
			return getRuleContexts(SequenceContext.class);
		}
		public TerminalNode Int() { return getToken(FilterParser.Int, 0); }
		public SequenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sequence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterListener ) ((FilterListener)listener).enterSequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterListener ) ((FilterListener)listener).exitSequence(this);
		}
	}

	public final SequenceContext sequence() throws RecognitionException {
		return sequence(0);
	}

	private SequenceContext sequence(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		SequenceContext _localctx = new SequenceContext(_ctx, _parentState);
		SequenceContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_sequence, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(56); match(T__16);
				setState(57); ((SequenceContext)_localctx).readIndex = match(Int);
				 ((SequenceContext)_localctx).val =  s(i((((SequenceContext)_localctx).readIndex!=null?((SequenceContext)_localctx).readIndex.getText():null)) - 1); 
				}
				break;

			case 2:
				{
				setState(59); match(T__16);
				 ((SequenceContext)_localctx).val =  s(0); 
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(70);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new SequenceContext(_parentctx, _parentState);
					_localctx.s1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_sequence);
					setState(63);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(64); match(T__2);
					setState(65); ((SequenceContext)_localctx).s2 = sequence(2);
					 ((SequenceContext)_localctx).val =  concat(_localctx.s1.val, _localctx.s2.val);; 
					}
					} 
				}
				setState(72);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class NumberContext extends ParserRuleContext {
		public Num val;
		public Token num;
		public QualityContext arg;
		public QualityContext quality() {
			return getRuleContext(QualityContext.class,0);
		}
		public TerminalNode Int() { return getToken(FilterParser.Int, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterListener ) ((FilterListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterListener ) ((FilterListener)listener).exitNumber(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_number);
		try {
			setState(85);
			switch (_input.LA(1)) {
			case Int:
				enterOuterAlt(_localctx, 1);
				{
				setState(73); ((NumberContext)_localctx).num = match(Int);
				 ((NumberContext)_localctx).val =  num(l((((NumberContext)_localctx).num!=null?((NumberContext)_localctx).num.getText():null))); 
				}
				break;
			case T__15:
				enterOuterAlt(_localctx, 2);
				{
				setState(75); match(T__15);
				setState(76); ((NumberContext)_localctx).arg = quality(0);
				setState(77); match(T__3);
				 ((NumberContext)_localctx).val =  min(_localctx.arg.val); 
				}
				break;
			case T__17:
				enterOuterAlt(_localctx, 3);
				{
				setState(80); match(T__17);
				setState(81); ((NumberContext)_localctx).arg = quality(0);
				setState(82); match(T__3);
				 ((NumberContext)_localctx).val =  mean(_localctx.arg.val); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogicalExpressionContext extends ParserRuleContext {
		public LogicalExpression val;
		public LogicalExpressionContext ll;
		public NumberContext ln;
		public NumberContext rn;
		public LogicalExpressionContext rl;
		public List<NumberContext> number() {
			return getRuleContexts(NumberContext.class);
		}
		public LogicalExpressionContext logicalExpression(int i) {
			return getRuleContext(LogicalExpressionContext.class,i);
		}
		public NumberContext number(int i) {
			return getRuleContext(NumberContext.class,i);
		}
		public List<LogicalExpressionContext> logicalExpression() {
			return getRuleContexts(LogicalExpressionContext.class);
		}
		public LogicalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterListener ) ((FilterListener)listener).enterLogicalExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterListener ) ((FilterListener)listener).exitLogicalExpression(this);
		}
	}

	public final LogicalExpressionContext logicalExpression() throws RecognitionException {
		return logicalExpression(0);
	}

	private LogicalExpressionContext logicalExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		LogicalExpressionContext _localctx = new LogicalExpressionContext(_ctx, _parentState);
		LogicalExpressionContext _prevctx = _localctx;
		int _startState = 10;
		enterRecursionRule(_localctx, 10, RULE_logicalExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(88); ((LogicalExpressionContext)_localctx).ln = number();
				setState(89); match(T__8);
				setState(90); ((LogicalExpressionContext)_localctx).rn = number();
				 ((LogicalExpressionContext)_localctx).val =  lt(_localctx.rn.val, _localctx.ln.val); 
				}
				break;

			case 2:
				{
				setState(93); ((LogicalExpressionContext)_localctx).ln = number();
				setState(94); match(T__13);
				setState(95); ((LogicalExpressionContext)_localctx).rn = number();
				 ((LogicalExpressionContext)_localctx).val =  le(_localctx.rn.val, _localctx.ln.val); 
				}
				break;

			case 3:
				{
				setState(98); ((LogicalExpressionContext)_localctx).ln = number();
				setState(99); match(T__9);
				setState(100); ((LogicalExpressionContext)_localctx).rn = number();
				 ((LogicalExpressionContext)_localctx).val =  lt(_localctx.ln.val, _localctx.rn.val); 
				}
				break;

			case 4:
				{
				setState(103); ((LogicalExpressionContext)_localctx).ln = number();
				setState(104); match(T__6);
				setState(105); ((LogicalExpressionContext)_localctx).rn = number();
				 ((LogicalExpressionContext)_localctx).val =  le(_localctx.ln.val, _localctx.rn.val); 
				}
				break;

			case 5:
				{
				setState(108); ((LogicalExpressionContext)_localctx).ln = number();
				setState(109); match(T__10);
				setState(110); ((LogicalExpressionContext)_localctx).rn = number();
				 ((LogicalExpressionContext)_localctx).val =  eq(_localctx.ln.val, _localctx.rn.val); 
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(127);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(125);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						_localctx = new LogicalExpressionContext(_parentctx, _parentState);
						_localctx.ll = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_logicalExpression);
						setState(115);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(116); match(T__11);
						setState(117); ((LogicalExpressionContext)_localctx).rl = logicalExpression(3);
						 ((LogicalExpressionContext)_localctx).val =  and(_localctx.ll.val, _localctx.rl.val); 
						}
						break;

					case 2:
						{
						_localctx = new LogicalExpressionContext(_parentctx, _parentState);
						_localctx.ll = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_logicalExpression);
						setState(120);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(121); match(T__14);
						setState(122); ((LogicalExpressionContext)_localctx).rl = logicalExpression(2);
						 ((LogicalExpressionContext)_localctx).val =  or(_localctx.ll.val, _localctx.rl.val); 
						}
						break;
					}
					} 
				}
				setState(129);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class SequenceProcessorContext extends ParserRuleContext {
		public SequenceProcessor val;
		public SequenceProcessorContext sp;
		public LogicalExpressionContext f;
		public Token n;
		public Token mn;
		public Token i;
		public SequenceProcessorContext sequenceProcessor() {
			return getRuleContext(SequenceProcessorContext.class,0);
		}
		public TerminalNode MethodName() { return getToken(FilterParser.MethodName, 0); }
		public TerminalNode Int() { return getToken(FilterParser.Int, 0); }
		public LogicalExpressionContext logicalExpression() {
			return getRuleContext(LogicalExpressionContext.class,0);
		}
		public SequenceProcessorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sequenceProcessor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FilterListener ) ((FilterListener)listener).enterSequenceProcessor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FilterListener ) ((FilterListener)listener).exitSequenceProcessor(this);
		}
	}

	public final SequenceProcessorContext sequenceProcessor() throws RecognitionException {
		return sequenceProcessor(0);
	}

	private SequenceProcessorContext sequenceProcessor(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		SequenceProcessorContext _localctx = new SequenceProcessorContext(_ctx, _parentState);
		SequenceProcessorContext _prevctx = _localctx;
		int _startState = 12;
		enterRecursionRule(_localctx, 12, RULE_sequenceProcessor, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			switch (_input.LA(1)) {
			case T__7:
				{
				setState(131); match(T__7);
				setState(132); ((SequenceProcessorContext)_localctx).f = logicalExpression(0);
				setState(133); match(T__3);
				 ((SequenceProcessorContext)_localctx).val =  new Filter(_localctx.f.val); 
				}
				break;
			case T__0:
				{
				setState(136); match(T__0);
				setState(137); ((SequenceProcessorContext)_localctx).n = match(Int);
				setState(138); match(T__3);
				 ((SequenceProcessorContext)_localctx).val =  new Skip(l((((SequenceProcessorContext)_localctx).n!=null?((SequenceProcessorContext)_localctx).n.getText():null))); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(151);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new SequenceProcessorContext(_parentctx, _parentState);
					_localctx.sp = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_sequenceProcessor);
					setState(142);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(143); match(T__1);
					setState(144); ((SequenceProcessorContext)_localctx).mn = match(MethodName);
					setState(145); match(T__4);
					setState(146); ((SequenceProcessorContext)_localctx).i = match(Int);
					setState(147); match(T__3);
					 ((SequenceProcessorContext)_localctx).val =  intMethod(_localctx.sp.val, (((SequenceProcessorContext)_localctx).mn!=null?((SequenceProcessorContext)_localctx).mn.getText():null), l((((SequenceProcessorContext)_localctx).i!=null?((SequenceProcessorContext)_localctx).i.getText():null))); 
					}
					} 
				}
				setState(153);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 2: return quality_sempred((QualityContext)_localctx, predIndex);

		case 3: return sequence_sempred((SequenceContext)_localctx, predIndex);

		case 5: return logicalExpression_sempred((LogicalExpressionContext)_localctx, predIndex);

		case 6: return sequenceProcessor_sempred((SequenceProcessorContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean sequence_sempred(SequenceContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1: return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean sequenceProcessor_sempred(SequenceProcessorContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4: return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean logicalExpression_sempred(LogicalExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2: return precpred(_ctx, 2);

		case 3: return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean quality_sempred(QualityContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\30\u009d\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\7\2\27\n\2\f\2\16\2\32\13\2\3\2\5\2\35\n\2\3\2\3\2\3\3\3\3\3\3\3\3"+
		"\3\3\5\3&\n\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4.\n\4\3\4\3\4\3\4\3\4\3\4\7\4"+
		"\65\n\4\f\4\16\48\13\4\3\5\3\5\3\5\3\5\3\5\3\5\5\5@\n\5\3\5\3\5\3\5\3"+
		"\5\3\5\7\5G\n\5\f\5\16\5J\13\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\5\6X\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7t\n\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7\u0080\n\7\f\7\16\7\u0083\13\7\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u008f\n\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\7\b\u0098\n\b\f\b\16\b\u009b\13\b\3\b\2\6\6\b\f\16\t\2\4\6\b"+
		"\n\f\16\2\2\u00a6\2\20\3\2\2\2\4%\3\2\2\2\6-\3\2\2\2\b?\3\2\2\2\nW\3\2"+
		"\2\2\fs\3\2\2\2\16\u008e\3\2\2\2\20\21\5\16\b\2\21\30\b\2\1\2\22\23\7"+
		"\t\2\2\23\24\5\16\b\2\24\25\b\2\1\2\25\27\3\2\2\2\26\22\3\2\2\2\27\32"+
		"\3\2\2\2\30\26\3\2\2\2\30\31\3\2\2\2\31\34\3\2\2\2\32\30\3\2\2\2\33\35"+
		"\7\t\2\2\34\33\3\2\2\2\34\35\3\2\2\2\35\36\3\2\2\2\36\37\7\2\2\3\37\3"+
		"\3\2\2\2 !\7\20\2\2!\"\7\27\2\2\"&\b\3\1\2#$\7\20\2\2$&\b\3\1\2% \3\2"+
		"\2\2%#\3\2\2\2&\5\3\2\2\2\'(\b\4\1\2()\7\3\2\2)*\7\27\2\2*.\b\4\1\2+,"+
		"\7\3\2\2,.\b\4\1\2-\'\3\2\2\2-+\3\2\2\2.\66\3\2\2\2/\60\f\3\2\2\60\61"+
		"\7\23\2\2\61\62\5\6\4\4\62\63\b\4\1\2\63\65\3\2\2\2\64/\3\2\2\2\658\3"+
		"\2\2\2\66\64\3\2\2\2\66\67\3\2\2\2\67\7\3\2\2\28\66\3\2\2\29:\b\5\1\2"+
		":;\7\5\2\2;<\7\27\2\2<@\b\5\1\2=>\7\5\2\2>@\b\5\1\2?9\3\2\2\2?=\3\2\2"+
		"\2@H\3\2\2\2AB\f\3\2\2BC\7\23\2\2CD\5\b\5\4DE\b\5\1\2EG\3\2\2\2FA\3\2"+
		"\2\2GJ\3\2\2\2HF\3\2\2\2HI\3\2\2\2I\t\3\2\2\2JH\3\2\2\2KL\7\27\2\2LX\b"+
		"\6\1\2MN\7\6\2\2NO\5\6\4\2OP\7\22\2\2PQ\b\6\1\2QX\3\2\2\2RS\7\4\2\2ST"+
		"\5\6\4\2TU\7\22\2\2UV\b\6\1\2VX\3\2\2\2WK\3\2\2\2WM\3\2\2\2WR\3\2\2\2"+
		"X\13\3\2\2\2YZ\b\7\1\2Z[\5\n\6\2[\\\7\r\2\2\\]\5\n\6\2]^\b\7\1\2^t\3\2"+
		"\2\2_`\5\n\6\2`a\7\b\2\2ab\5\n\6\2bc\b\7\1\2ct\3\2\2\2de\5\n\6\2ef\7\f"+
		"\2\2fg\5\n\6\2gh\b\7\1\2ht\3\2\2\2ij\5\n\6\2jk\7\17\2\2kl\5\n\6\2lm\b"+
		"\7\1\2mt\3\2\2\2no\5\n\6\2op\7\13\2\2pq\5\n\6\2qr\b\7\1\2rt\3\2\2\2sY"+
		"\3\2\2\2s_\3\2\2\2sd\3\2\2\2si\3\2\2\2sn\3\2\2\2t\u0081\3\2\2\2uv\f\4"+
		"\2\2vw\7\n\2\2wx\5\f\7\5xy\b\7\1\2y\u0080\3\2\2\2z{\f\3\2\2{|\7\7\2\2"+
		"|}\5\f\7\4}~\b\7\1\2~\u0080\3\2\2\2\177u\3\2\2\2\177z\3\2\2\2\u0080\u0083"+
		"\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082\3\2\2\2\u0082\r\3\2\2\2\u0083\u0081"+
		"\3\2\2\2\u0084\u0085\b\b\1\2\u0085\u0086\7\16\2\2\u0086\u0087\5\f\7\2"+
		"\u0087\u0088\7\22\2\2\u0088\u0089\b\b\1\2\u0089\u008f\3\2\2\2\u008a\u008b"+
		"\7\25\2\2\u008b\u008c\7\27\2\2\u008c\u008d\7\22\2\2\u008d\u008f\b\b\1"+
		"\2\u008e\u0084\3\2\2\2\u008e\u008a\3\2\2\2\u008f\u0099\3\2\2\2\u0090\u0091"+
		"\f\3\2\2\u0091\u0092\7\24\2\2\u0092\u0093\7\26\2\2\u0093\u0094\7\21\2"+
		"\2\u0094\u0095\7\27\2\2\u0095\u0096\7\22\2\2\u0096\u0098\b\b\1\2\u0097"+
		"\u0090\3\2\2\2\u0098\u009b\3\2\2\2\u0099\u0097\3\2\2\2\u0099\u009a\3\2"+
		"\2\2\u009a\17\3\2\2\2\u009b\u0099\3\2\2\2\17\30\34%-\66?HWs\177\u0081"+
		"\u008e\u0099";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}