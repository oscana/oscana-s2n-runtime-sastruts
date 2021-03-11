package oscana.s2n.seasar.framework.beans;

/**
 * テスト用クラス
 *
 */
public class TestBeanForLong extends TestBean2 {

    /**
     * fieldのみ
     */
    public long price1;

    /**
     * 通常形式
     */
    public long price2;

    /**
     * @param price1
     * @param price2
     */
    public TestBeanForLong(long price1, long price2){
        this.price1 = price1;
        this.price2 = price2;
    }

    /**
     * @return price2
     */
    public long getPrice2() {
        return price2;
    }

    /**
     * @param price2
     */
    public void setPrice2(long price2) {
        this.price2 = price2;
    }

    /**
     * getterのみ
     *
     * @return test
     */
    public long getCcc() {
        return 1234567893;
    }


}
