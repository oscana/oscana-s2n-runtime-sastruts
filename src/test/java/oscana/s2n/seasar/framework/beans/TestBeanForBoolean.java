package oscana.s2n.seasar.framework.beans;

/**
 * テスト用クラス
 *
 */
public class TestBeanForBoolean extends TestBean2 {

    /**
     * fieldのみ
     */
    public boolean isFieldFlag1;

    /**
     * 通常形式
     */
    public boolean isFieldFlag2;

    /**
     * @param isFieldFlag1
     * @param isFieldFlag2
     */
    public TestBeanForBoolean(boolean isFieldFlag1, boolean isFieldFlag2){
        this.isFieldFlag1 = isFieldFlag1;
        this.isFieldFlag2 = isFieldFlag2;
    }

    /**
     * @return isFieldFlag2
     */
    public boolean getIsFieldFlag2() {
        return isFieldFlag2;
    }

    /**
     * @param isFieldFlag2
     */
    public void setIsFieldFlag(boolean isFieldFlag2) {
        this.isFieldFlag2 = isFieldFlag2;
    }

    /**
     * getterのみ
     *
     * @return test
     */
    public boolean getCcc() {
        return true;
    }


}
