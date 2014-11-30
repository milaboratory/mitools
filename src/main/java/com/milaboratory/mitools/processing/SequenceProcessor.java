package com.milaboratory.mitools.processing;

import com.milaboratory.core.io.sequence.SequenceRead;

import java.util.function.Consumer;

/**
 * Created by dbolotin on 29/11/14.
 */
public interface SequenceProcessor {
    boolean apply(SequenceRead read);

    void setReadConsumer(Consumer<SequenceRead> readConsumer);

    void setMessageConsumer(Consumer<String> messageConsumer);
}
