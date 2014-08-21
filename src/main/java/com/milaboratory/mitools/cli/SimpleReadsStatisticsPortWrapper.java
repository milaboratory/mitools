package com.milaboratory.mitools.cli;

import cc.redberry.pipe.OutputPort;
import com.milaboratory.core.io.sequence.SequenceRead;
import com.milaboratory.core.io.sequence.SingleRead;

import java.util.concurrent.atomic.AtomicLong;

public class SimpleReadsStatisticsPortWrapper<R extends SequenceRead> implements OutputPort<R> {
    final OutputPort<R> innerPort;
    final AtomicLong count = new AtomicLong();
    final AtomicLong nucleotides = new AtomicLong();

    public SimpleReadsStatisticsPortWrapper(OutputPort<R> innerPort) {
        this.innerPort = innerPort;
    }

    @Override
    public R take() {
        R read = innerPort.take();
        if (read != null) {
            count.incrementAndGet();
            for (SingleRead sRead : read)
                nucleotides.addAndGet(sRead.getData().size());
        }
        return read;
    }
}
