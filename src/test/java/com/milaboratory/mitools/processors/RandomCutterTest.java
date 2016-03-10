/*
 * Copyright 2016 MiLaboratory.com
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
package com.milaboratory.mitools.processors;

import com.milaboratory.core.io.sequence.SingleRead;
import com.milaboratory.core.io.sequence.SingleReadImpl;
import com.milaboratory.core.sequence.NSequenceWithQuality;
import gnu.trove.set.hash.TIntHashSet;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by dbolotin on 10/03/16.
 */
public class RandomCutterTest {
    @Test
    public void test1() throws Exception {
        RandomCutter cutter = new RandomCutter(CutSide.Center, 10, 20);
        SingleReadImpl read = new SingleReadImpl(1, new NSequenceWithQuality("ATTAGACA"), "Hi!");
        SingleRead processed = cutter.process(read);
        Assert.assertEquals(processed, read);

        TIntHashSet vals = new TIntHashSet();

        for (int i = 0; i < 1000; i++) {
            read = new SingleReadImpl(1, new NSequenceWithQuality("ATTAGACAATTAGACAATTAGACAATTAGACA"), "Hi!");
            processed = cutter.process(read);
            Assert.assertTrue(processed.getData().size() >= 10 && processed.getData().size() <= 20);
            vals.add(processed.getData().size());
        }
        Assert.assertTrue(vals.contains(20));
        Assert.assertTrue(vals.contains(10));

        cutter = new RandomCutter(CutSide.Left, 10, 20);
        vals = new TIntHashSet();
        for (int i = 0; i < 1000; i++) {
            read = new SingleReadImpl(1, new NSequenceWithQuality("ATTAGACAATTAGACAATTAGACAATTAGACA"), "Hi!");
            processed = cutter.process(read);
            Assert.assertTrue(processed.getData().size() >= 10 && processed.getData().size() <= 20);
            Assert.assertTrue(read.getData().getSequence().toString().startsWith(processed.getData().getSequence().toString()));
            vals.add(processed.getData().size());

        }
        Assert.assertTrue(vals.contains(20));
        Assert.assertTrue(vals.contains(10));

        cutter = new RandomCutter(CutSide.Right, 10, 20);
        vals = new TIntHashSet();
        for (int i = 0; i < 1000; i++) {
            read = new SingleReadImpl(1, new NSequenceWithQuality("ATTAGACAATTAGACAATTAGACAATTAGACA"), "Hi!");
            processed = cutter.process(read);
            Assert.assertTrue(processed.getData().size() >= 10 && processed.getData().size() <= 20);
            Assert.assertTrue(read.getData().getSequence().toString().endsWith(processed.getData().getSequence().toString()));
            vals.add(processed.getData().size());
        }
        Assert.assertTrue(vals.contains(20));
        Assert.assertTrue(vals.contains(10));
    }
}