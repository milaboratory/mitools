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

import cc.redberry.pipe.Processor;
import com.milaboratory.core.io.sequence.SingleRead;
import com.milaboratory.core.io.sequence.SingleReadImpl;
import com.milaboratory.util.RandomUtil;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well44497b;

public final class RandomCutter implements Processor<SingleRead, SingleRead> {
    private final CutSide side;
    private final int minLength, maxLength;
    private final long seed;

    public RandomCutter(CutSide side, int minLength, int maxLength) {
        this(side, minLength, maxLength, 0);
    }

    public RandomCutter(CutSide side, int minLength, int maxLength, long seed) {
        if (side == null)
            throw new NullPointerException();
        this.side = side;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.seed = seed;
    }

    @Override
    public SingleRead process(SingleRead input) {
        RandomGenerator rnd;
        if (seed == 0)
            rnd = RandomUtil.getThreadLocalRandom();
        else {
            Well44497b w = new Well44497b(seed);
            w.setSeed(5147 * w.nextLong() + 7549 * input.getId());
            rnd = w;
        }
        int length;
        if (minLength == maxLength)
            length = minLength;
        else
            length = minLength + rnd.nextInt(maxLength - minLength + 1);
        if (length > input.getData().size())
            length = input.getData().size();

        int startPosition;

        switch (side) {
            case Left:
                startPosition = 0;
                break;
            case Right:
                startPosition = input.getData().size() - length;
                break;
            case Center:
                startPosition = length == input.getData().size() ? 0 : rnd.nextInt(input.getData().size() - length + 1);
                break;
            default:
                // Will never be thrown
                throw new IllegalArgumentException();
        }

        return new SingleReadImpl(input.getId(), input.getData().getRange(startPosition, startPosition + length), input.getDescription());
    }
}
