package app.backoffice;

import core.framework.test.module.AbstractTestModule;

/**
 * @author zoo
 */
public class TestModule extends AbstractTestModule {
    @Override
    protected void initialize() {
        load(new BackOfficeApp());
    }
}
