package com.milaboratory.mitools.processing;

import com.milaboratory.mitools.parsers.FilterLexer;
import com.milaboratory.mitools.parsers.FilterParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FilterParserTest {
    @Test
    public void test1() throws Exception {
        //assertEquals(0, 0);
        //ANTLRInputStream is = new ANTLRInputStream("skip(100); filter(min(Q1+Q2+Q1)>0 || mean(Q1)<=min(Q2)); ");
        //FilterLexer lexer = new FilterLexer(is);
        //CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        //FilterParser parser = new FilterParser(tokenStream);
        //FilterParser.ActionContext action = parser.action();
        //int i = 0;
        ////FilterParser parser = new FilterParser()
    }
}