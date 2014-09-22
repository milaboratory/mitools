package com.milaboratory.mitools.merger;

import com.milaboratory.core.io.sequence.PairedRead;
import com.milaboratory.core.io.sequence.SingleReadImpl;
import com.milaboratory.core.sequence.NSequenceWithQuality;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class MismatchOnlyPairedReadMergerTest {
    @Test
    public void test1() throws Exception {
        //CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG
        //CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCC
        //                            CGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG
        //AAAAAAAAAAAAAAAAAAAAAAAAAAAAbbbbbbbbbbbbbbbbbbbbBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB
        mAssert("CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCC",
                "CGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG",
                0, 10,
                "CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG",
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAXXXXXXXXXXXXXXXXXXXXBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
    }

    @Test
    public void test2() throws Exception {
        //CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG
        //          TGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTT
        //AAAAAAAAAAbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        mAssert("CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG",
                "TGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTT",
                0, 10,
                "CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG",
                "AAAAAAAAAAXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    }

    @Test
    public void test3() throws Exception {
        //CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG
        //CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATC
        //                                                  TCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG
        //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAbbbbbbbbbbbbbbbbbbbbbbBBBBBBBBBBBBBBBBBBBBBBBBBBBB
        mAssert("CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATC",
                "TCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG",
                0, 10,
                "CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG",
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAXXXXXXXXXXXXXXXXXXXXXXBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
    }

    @Test
    public void test1mm() throws Exception {
        //CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG
        //CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGACGACCGGCC
        //                            CGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG
        //AAAAAAAAAAAAAAAAAAAAAAAAAAAAbbbbbbbbbbbBbbbbbbbbBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB
        mAssert("CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGACGACCGGCC",
                "CGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG",
                1, 10,
                "CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG",
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAXXXXXXXXXXXBXXXXXXXXBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        mAssert("CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGACGACCGGCC",
                "CGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG",
                0, 10, null, null);
    }

    @Test
    public void test2mm() throws Exception {
        //CGCACAGTGTTGTCAAAGACAACGCGTACGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG
        //          TGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTT
        //AAAAAAAAAAbbbbbbbbbBbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        mAssert("CGCACAGTGTTGTCAAAGACAACGCGTACGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG",
                "TGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTT",
                1, 10,
                "CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG",
                "AAAAAAAAAAXXXXXXXXXBXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        mAssert("CGCACAGTGTTGTCAAAGACAACGCGTACGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG",
                "TGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTT",
                0, 10, null, null);
    }

    @Test
    public void test3mm() throws Exception {
        //CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG
        //CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTACTCCTTTGACATGATTGGATC
        //                                                  TCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG
        //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABbbbbbbbbbbbbbbbbbbbbbBBBBBBBBBBBBBBBBBBBBBBBBBBBB
        mAssert("CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTACTCCTTTGACATGATTGGATC",
                "TCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG",
                1, 10,
                "CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTTCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG",
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABXXXXXXXXXXXXXXXXXXXXXBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        mAssert("CGCACAGTGTTGTCAAAGAAAACGCGTACGACATTGAGAAGACCGGCCGTACTCCTTTGACATGATTGGATC",
                "TCTCCTTTGACATGATTGGATCGGTTGCTGCCGGCCCAGAATCCTAGCAG",
                0, 10, null, null);
    }

    public static void mAssert(String seq1, String seq2, int maxMuts, int overlap,
                               String expectedSequence, String expectedQuality) {
        MismatchOnlyPairedReadMerger merger = new MismatchOnlyPairedReadMerger(overlap, 1.0 - 1.0 * maxMuts / overlap, 55, false);
        PairedReadMergingResult processed = merger.process(new PairedRead(
                new SingleReadImpl(0, new NSequenceWithQuality(seq1, lets('A', seq1.length())), "A"),
                new SingleReadImpl(0, new NSequenceWithQuality(seq2, lets('B', seq2.length())), "B")));
        if (expectedSequence == null)
            Assert.assertFalse(processed.isSuccessful());
        else {
            Assert.assertTrue(processed.isSuccessful());
            Assert.assertEquals(expectedSequence, processed.getOverlappedSequence().getSequence().toString());
            Assert.assertEquals(expectedQuality, processed.getOverlappedSequence().getQuality().toString());
        }

        merger = new MismatchOnlyPairedReadMerger(overlap, 1.0 - 1.0 * maxMuts / overlap, 55, null);
        processed = merger.process(new PairedRead(
                new SingleReadImpl(0, new NSequenceWithQuality(seq1, lets('A', seq1.length())), "A"),
                new SingleReadImpl(0, new NSequenceWithQuality(seq2, lets('B', seq2.length())).getReverseComplement(), "B")));
        if (expectedSequence == null)
            Assert.assertFalse(processed.isSuccessful());
        else {
            Assert.assertTrue(processed.isSuccessful());
            Assert.assertEquals(expectedSequence, processed.getOverlappedSequence().getSequence().toString());
            Assert.assertEquals(expectedQuality, processed.getOverlappedSequence().getQuality().toString());
        }
    }

    public static String lets(char letter, int count) {
        char[] chars = new char[count];
        Arrays.fill(chars, letter);
        return new String(chars);
    }
}