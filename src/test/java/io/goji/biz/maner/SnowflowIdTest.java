package io.goji.biz.maner;

import io.goji.biz.maner.utility.SnowflakeId;
import org.junit.jupiter.api.Test;


public class SnowflowIdTest {

    @Test
    public void test() {
        System.out.println("test");
    }

    @Test
    public void tetGenerateId() {
        SnowflakeId snowflakeId = new SnowflakeId(1, 1);
        System.out.println("testGenerateId: " + snowflakeId.generateId(1));

    }

}
