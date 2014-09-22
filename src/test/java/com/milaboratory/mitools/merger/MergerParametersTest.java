package com.milaboratory.mitools.merger;

import com.milaboratory.test.TestUtil;
import org.junit.Test;

public class MergerParametersTest {
    @Test
    public void test1() throws Exception {
        MergerParameters parameters = new MergerParameters(15, 0.8);
        TestUtil.assertJson(parameters);
    }
}