package oscana.s2n.seasar.framework.beans;

/**
 * テスト用クラス
 *
 */
public class TestBeanForInt extends TestBean2 {

    /**
     * fieldのみ
     */
    public int age1;

    /**
     * 通常形式
     */
    public int age2;

    /**
     * @param age1
     * @param age2
     */
    public TestBeanForInt(int age1, int age2){
        this.age1 = age1;
        this.age2 = age2;
    }

    /**
     * @return age2
     */
    public int getAge2() {
        return age2;
    }

    /**
     * @param age2
     */
    public void setStr2(int age2) {
        this.age2 = age2;
    }

    /**
     * getterのみ
     *
     * @return test
     */
    public int getCcc() {
        return 111;
    }


}
