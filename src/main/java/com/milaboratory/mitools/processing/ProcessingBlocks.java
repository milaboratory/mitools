package com.milaboratory.mitools.processing;

import com.milaboratory.core.io.sequence.SequenceRead;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.core.sequence.SequenceQuality;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class ProcessingBlocks {
    /* Types */

    public static interface LogicalExpression {
        boolean apply(SequenceRead read);
    }

    public static interface RDescription {
        String apply(SequenceRead read);
    }

    public static interface RSequence {
        NucleotideSequence apply(SequenceRead read);
    }

    public static interface RQuality {
        SequenceQuality apply(SequenceRead read);
    }

    public static interface Num {
        long apply(SequenceRead read);
    }


    public static abstract class AbstractSequenceProcessor implements com.milaboratory.mitools.processing.SequenceProcessor {
        Consumer<SequenceRead> readConsumer;

        Consumer<String> messageConsumer = System.err::println;

        protected final void pushRead(SequenceRead read) {
            readConsumer.accept(read);
        }

        protected final void pushMessage(String msg) {
            messageConsumer.accept(msg);
        }

        @Override
        public void setReadConsumer(Consumer<SequenceRead> readConsumer) {
            this.readConsumer = readConsumer;
        }

        @Override
        public void setMessageConsumer(Consumer<String> messageConsumer) {
            this.messageConsumer = messageConsumer;
        }
    }

    /* Utility functions */

    public static long l(String text) {
        return Long.parseLong(text);
    }

    public static int i(String text) {
        return Integer.parseInt(text);
    }

    /* Building block factories */

    public static Num num(long val) {
        return s -> val;
    }

    public static Num min(RQuality val) {
        return s -> val.apply(s).minValue();
    }

    public static Num mean(RQuality val) {
        return s -> val.apply(s).meanValue();
    }

    public static RQuality concat(RQuality q1, RQuality q2) {
        return s -> q1.apply(s).concatenate(q2.apply(s));
    }

    public static RSequence concat(RSequence s1, RSequence s2) {
        return s -> s1.apply(s).concatenate(s2.apply(s));
    }

    public static RQuality q(int id) {
        return s -> s.getRead(id).getData().getQuality();
    }

    public static RSequence s(int id) {
        return s -> s.getRead(id).getData().getSequence();
    }

    public static RDescription d(int id) {
        return s -> s.getRead(id).getDescription();
    }

    public static LogicalExpression lt(Num l, Num r) {
        return s -> l.apply(s) < r.apply(s);
    }

    public static LogicalExpression le(Num l, Num r) {
        return s -> l.apply(s) <= r.apply(s);
    }

    public static LogicalExpression eq(Num l, Num r) {
        return s -> l.apply(s) == r.apply(s);
    }

    public static LogicalExpression and(LogicalExpression l, LogicalExpression r) {
        return s -> l.apply(s) && r.apply(s);
    }

    public static LogicalExpression or(LogicalExpression l, LogicalExpression r) {
        return s -> l.apply(s) || r.apply(s);
    }

    public static SequenceProcessor intMethod(SequenceProcessor proc, String methodName, long argument) {
        try {
            Class<? extends SequenceProcessor> aClass = proc.getClass();
            for (Method method : aClass.getDeclaredMethods()) {
                IntProcessorMethod declaredAnnotation = method.getDeclaredAnnotation(IntProcessorMethod.class);
                if (declaredAnnotation != null && declaredAnnotation.value().equals(methodName)) {
                    method.invoke(proc, argument);
                    return proc;
                }
            }
            throw new RuntimeException("Unknown method: " + methodName);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /* Actions */

    public static final class Skip extends AbstractSequenceProcessor {
        final AtomicLong left;

        public Skip(long skip) {
            left = new AtomicLong(skip);
        }

        @Override
        public boolean apply(SequenceRead read) {
            return left.decrementAndGet() >= 0;
        }
    }

    public static final class Filter extends AbstractSequenceProcessor {
        final AtomicLong processed = new AtomicLong();
        final AtomicLong accepted = new AtomicLong();
        long maxAccepted = -1;
        long maxProcessed = -1;
        final LogicalExpression filter;

        public Filter(LogicalExpression filter) {
            this.filter = filter;
        }

        @IntProcessorMethod("acceptMax")
        public void setMaxAccepted(long maxAccepted) {
            this.maxAccepted = maxAccepted;
        }

        @IntProcessorMethod("processMax")
        public void setMaxProcessed(long maxProcessed) {
            this.maxProcessed = maxProcessed;
        }

        @Override
        public boolean apply(SequenceRead read) {
            if (read == null)
                return false;

            if (maxAccepted != -1 && accepted.get() >= maxAccepted)
                return false;
            if (maxProcessed != -1 && processed.get() >= maxProcessed)
                return false;

            processed.incrementAndGet();

            if (filter.apply(read)) {
                accepted.incrementAndGet();
                pushRead(read);
            }

            return true;
        }
    }
}
