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
