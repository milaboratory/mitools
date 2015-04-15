package com.milaboratory.mitools.merger;

import com.milaboratory.core.io.sequence.PairedRead;
import com.milaboratory.core.io.sequence.SingleReadImpl;
import com.milaboratory.core.io.util.TestUtil;
import com.milaboratory.core.sequence.NSequenceWithQuality;
import com.milaboratory.core.sequence.NucleotideSequence;
import com.milaboratory.core.sequence.SequenceQuality;
import org.junit.Test;

/**
 * Created by poslavsky on 15/04/15.
 */
public class PairedReadMergingResultTest {
    @Test
    public void test1() throws Exception {
        PairedReadMergingResult se = new PairedReadMergingResult(new PairedRead(
                new SingleReadImpl(12, new NSequenceWithQuality(new NucleotideSequence("atgc"), new SequenceQuality("++++")), "x"),
                new SingleReadImpl(12, new NSequenceWithQuality(new NucleotideSequence("atgc"), new SequenceQuality("++++")), "x")),
                new NSequenceWithQuality(new NucleotideSequence("atgc"), new SequenceQuality("++++")), 12, 3);
        TestUtil.assertJavaSerialization(se);
    }
}