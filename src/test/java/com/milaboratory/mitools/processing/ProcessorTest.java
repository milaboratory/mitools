package com.milaboratory.mitools.processing;

import cc.redberry.pipe.CUtils;
import com.milaboratory.core.io.sequence.SingleRead;
import com.milaboratory.core.io.sequence.SingleReader;
import com.milaboratory.core.io.sequence.fastq.SingleFastqReader;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class ProcessorTest {
    @Test
    public void testName() throws Exception {
        Processor build = Processor.build("skip(100); filter(min(Q1+Q2+Q1)>0 || mean(Q1)<=min(Q2)); ");
        int i = 0;
    }

    @Test
    public void test2() throws Exception {
        Processor processor = Processor.build("skip(1); filter(min(Q)==0).acceptMax(1); ");
        AtomicInteger i = new AtomicInteger();
        processor.setReadConsumer(r -> i.incrementAndGet());
        try (SingleReader reader = new SingleFastqReader(ProcessorTest.class.getResourceAsStream("/sequences/sample_r1.fastq"))) {
            for (SingleRead singleRead : CUtils.it(reader)) {
                processor.apply(singleRead);
            }
        }
        System.out.println(i);
    }
}