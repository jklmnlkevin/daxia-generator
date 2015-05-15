package com.daxia.generator.cmd;

import org.apache.commons.lang3.StringUtils;

public abstract class BaseCmd {

    protected abstract void run();
    protected void notNull(String value) {
        if (StringUtils.isBlank(value)) {
            throw new RuntimeException("输入不合法");
        }
    }
}
