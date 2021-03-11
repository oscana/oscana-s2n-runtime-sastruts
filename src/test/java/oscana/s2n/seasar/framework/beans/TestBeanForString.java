package oscana.s2n.seasar.framework.beans;

/**
 * テスト用クラス
 *
 */
public class TestBeanForString extends TestBean2 {

    /**
     * fieldのみ
     */
    public String str1;

    /**
     * 通常形式
     */
    public String str2;

    /**
     * @param str1
     * @param str2
     */
    public TestBeanForString(String str1, String str2){
        this.str1 = str1;
        this.str2 = str2;
    }

    /**
     * @return str2
     */
    public String getStr2() {
        return str2;
    }

    /**
     * @param str2
     */
    public void setStr2(String str2) {
        this.str2 = str2;
    }

    /**
     * getterのみ
     *
     * @return test
     */
    public String getCcc() {
        return "test1";
    }


}
