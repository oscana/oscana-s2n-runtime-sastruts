package oscana.s2n.seasar.framework.beans;

/**
 * テスト用クラス
 *
 */
public class TestBeanForObject extends TestBean2 {

    /**
     * fieldのみ
     */
    public TestBean3 testBean1;

    /**
     * 通常形式
     */
    public TestBean3 testBean2;

    /**
     * @param testBean1
     * @param testBean2
     */
    public TestBeanForObject(TestBean3 testBean1, TestBean3 testBean2){
        this.testBean1 = testBean1;
        this.testBean2 = testBean2;
    }

    /**
     * @return testBean2
     */
    public TestBean3 getTestBean2() {
        return testBean2;
    }

    /**
     * @param testBean2
     */
    public void setTestBean2(TestBean3 testBean2) {
        this.testBean2 = testBean2;
    }

    /**
     * getterのみ
     *
     * @return test
     */
    public TestBean3 getCcc() {
        return new TestBean3("test");
    }


}
