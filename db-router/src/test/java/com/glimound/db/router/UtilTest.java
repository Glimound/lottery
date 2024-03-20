package com.glimound.db.router;

import com.glimound.db.router.util.PropertyUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class UtilTest {
    public static class testObj {
        private Integer k1;
        private String name;

        public testObj(Integer k1, String name) {
            this.k1 = k1;
            this.name = name;
        }
    }
    @Test
    public void PropertyUtilsTest() throws Exception {
        String str = "hah";
        log.debug(PropertyUtils.getAttrValue("name", new Object[]{str, new testObj(1500, "tony")}));
    }

}
