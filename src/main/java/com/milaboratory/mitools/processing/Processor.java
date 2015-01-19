package com.milaboratory.mitools.processing;

import com.milaboratory.core.io.sequence.SequenceRead;
import com.milaboratory.mitools.parsers.FilterLexer;
import com.milaboratory.mitools.parsers.FilterParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.List;
import java.util.function.Consumer;

public class Processor implements SequenceProcessor {
    final List<SequenceProcessor> processors;
    int currentProcessor = 0;

    private Processor(List<SequenceProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public boolean apply(SequenceRead read) {
        // TODO concurrent
        while (true) {
            if (currentProcessor >= processors.size())
                return false;
            if (processors.get(currentProcessor).apply(read))
                return true;
            else
                ++currentProcessor;
        }
    }

    @Override
    public void setReadConsumer(Consumer<SequenceRead> readConsumer) {
        for (SequenceProcessor block : processors)
            block.setReadConsumer(readConsumer);
    }

    @Override
    public void setMessageConsumer(Consumer<String> messageConsumer) {
        for (SequenceProcessor block : processors)
            block.setMessageConsumer(messageConsumer);
    }

    public static Processor build(String expression) {
        ANTLRInputStream is = new ANTLRInputStream(expression);
        FilterLexer lexer = new FilterLexer(is);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        FilterParser parser = new FilterParser(tokenStream);
        FilterParser.ActionSetContext actionSetContext = parser.actionSet();
        return new Processor(actionSetContext.processors);
    }
}
