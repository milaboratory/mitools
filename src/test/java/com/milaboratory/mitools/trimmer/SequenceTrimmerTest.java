/*
 * Copyright 2015 MiLaboratory.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.milaboratory.mitools.trimmer;

import com.milaboratory.core.io.sequence.SingleRead;
import com.milaboratory.core.io.sequence.SingleReadImpl;
import com.milaboratory.core.sequence.NSequenceWithQuality;
import org.junit.Assert;
import org.junit.Test;

public class SequenceTrimmerTest {
    @Test
    public void test1() throws Exception {
        // 0 = 15
        //ATTGCTGCTAGCTAGCTACGATCGACCGATGACAGCAGTATCGTACGTATGCTGAT
        //000102020010AAAAAAAAAAAABBABBABBABBABBABABBABABBABB23211
        //System.out.println((char) (33 + 16));
        tAssert("ATTGCTGCTAGCTAGCTACGATCGACCGATGACAGCAGTATCGTACGTATGCTGAT",
                "000102020010AAAAAAAAAAAABBABBABBABBABBABABBABABBABB23211",
                20,
                "TAGCTACGATCGACCGATGACAGCAGTATCGTACGTATG");
        tAssert("ATTGCTGCTAGCTAGCTACGATCGACCGATGACAGCAGTATCGTACGTATGCTGAT",
                "000102020010AAAAAAAAAAAABBABBABBABBABBABABBABABBABBABAAB",
                20,
                "TAGCTACGATCGACCGATGACAGCAGTATCGTACGTATGCTGAT");
    }

    public static void tAssert(String seq, String qual, int threshold, String expectedSeq) {
        SequenceTrimmer trimmer = new SequenceTrimmer(threshold, true, true);
        SingleRead read = new SingleReadImpl(0, new NSequenceWithQuality(seq, qual), "Sd");
        SingleRead result = trimmer.process(read);
        Assert.assertEquals(expectedSeq, result.getData().getSequence().toString());
    }
}