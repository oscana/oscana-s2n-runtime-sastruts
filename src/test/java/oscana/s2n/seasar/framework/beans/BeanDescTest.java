package oscana.s2n.seasar.framework.beans;

import static org.junit.Assert.*;

import org.junit.Test;

import oscana.s2n.seasar.framework.beans.factory.BeanDescFactory;

/**
 * {@link BeanDesc}のテスト。
 *
 */
public class BeanDescTest {

    /**
     * 指定したオブジェクトから、特定のプロパティの値を取得できること
     */
    @Test
    public void testGetPropertyDesc() {
        String str = "2222";
        MyBean bean = new MyBean();
        bean.setGgg(str);
        MyBean bean2 = new MyBean();
        bean2.setGgg("3333");

        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(bean.getClass());
        BeanDesc beanDesc2 = BeanDescFactory.getBeanDesc(bean2.getClass());
        PropertyDesc propertyDesc = null;
        propertyDesc = beanDesc.getPropertyDesc("ggg");
        PropertyDesc propertyDesc2 = beanDesc2.getPropertyDesc("ggg");
        assertEquals(str, propertyDesc.getValue(bean));
        assertEquals("3333", propertyDesc2.getValue(bean2));
    }

    /**
     * 存在してないプロパティの場合、BeansExceptionの例外を発生すること
     */
    @Test(expected = PropertyNotFoundRuntimeException.class)
    public void testGetPropertyDesc_throwException() {
        String str = "2222";
        MyBean bean = new MyBean();
        bean.setGgg(str);

        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(bean.getClass());
        beanDesc.getPropertyDesc("cds");
    }


    /**
     * 通常形式 field+getterの場合、String型のフィールドを取得する
     */
    @Test
    public void testGetValue_String() throws Exception {
        TestBeanForString myBean = new TestBeanForString("test1", "test2");
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(TestBeanForString.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("str2");
        assertEquals("test2", propDesc.getValue(myBean));
    }

    /**
     * getterのみの場合、String型のフィールドを取得する
     */
    @Test
    public void testGetValue_stringWithOnlyGetter() throws Exception {
        TestBeanForString myBean = new TestBeanForString("test1", "test2");
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(TestBeanForString.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("ccc");
        assertEquals("test1", propDesc.getValue(myBean));
    }

    /**
     * 通常形式 field+getterの場合、int型のフィールドを取得する
     */
    @Test
    public void testGetValue_int() throws Exception {
        TestBeanForInt myBean = new TestBeanForInt(123, 345);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(TestBeanForInt.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("age2");
        assertEquals(new Integer(345), propDesc.getValue(myBean));
    }

    /**
     * getterのみの場合、int型のフィールドを取得する
     */
    @Test
    public void testGetValue_intWithOnlyGetter() throws Exception {
        TestBeanForInt myBean = new TestBeanForInt(123, 345);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(TestBeanForInt.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("ccc");
        assertEquals(new Integer(111), propDesc.getValue(myBean));
    }

    /**
     * 通常形式 field+getterの場合、long型のフィールドを取得する
     */
    @Test
    public void testGetValue_long() throws Exception {
        TestBeanForLong myBean = new TestBeanForLong(1234567891, 1234567892);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(TestBeanForLong.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("price2");
        assertEquals(new Long(1234567892), propDesc.getValue(myBean));
    }

    /**
     * getterのみの場合、long型のフィールドを取得する
     */
    @Test
    public void testGetValue_longWithOnlyGetter() throws Exception {
        TestBeanForLong myBean = new TestBeanForLong(1234567891, 1234567892);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(TestBeanForLong.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("ccc");
        assertEquals(new Long(1234567893), propDesc.getValue(myBean));
    }

    /**
     * 通常形式 field+getterの場合、boolean型のフィールドを取得する
     */
    @Test
    public void testGetValue_boolean() throws Exception {
        TestBeanForBoolean myBean = new TestBeanForBoolean(true, false);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(TestBeanForBoolean.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("isFieldFlag2");
        assertEquals(new Boolean(false), propDesc.getValue(myBean));
    }

    /**
     * getterのみの場合、boolean型のフィールドを取得する
     */
    @Test
    public void testGetValue_booleanWithOnlyGetter() throws Exception {
        TestBeanForBoolean myBean = new TestBeanForBoolean(true, false);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(TestBeanForBoolean.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("ccc");
        assertEquals(new Boolean(true), propDesc.getValue(myBean));
    }

    /**
     * 通常形式 field+getterの場合、object型のフィールドを取得する
     */
    @Test
    public void testGetValue_object() throws Exception {
        TestBean3 testObject1 = new TestBean3("test1");
        TestBean3 testObject2 = new TestBean3("test2");
        TestBeanForObject myBean = new TestBeanForObject(testObject1, testObject2);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(TestBeanForObject.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("testBean2");
        assertEquals(testObject2, propDesc.getValue(myBean));
    }

    /**
     * getterのみの場合、object型のフィールドを取得する
     */
    @Test
    public void testGetValue_objectWithOnlyGetter() throws Exception {
        TestBean3 testObject1 = new TestBean3("test1");
        TestBean3 testObject2 = new TestBean3("test2");
        TestBeanForObject myBean = new TestBeanForObject(testObject1, testObject2);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(TestBeanForObject.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("ccc");
        assertNotNull(propDesc.getValue(myBean));
    }

    /**
     * 親クラスのプロパティに対して、String型のフィールドを取得する
     */
    @Test
    public void testGetValue_stringWithExtendsClassFiled() throws Exception {
        TestBeanForString myBean = new TestBeanForString("test1", "test2");
        myBean.setStr3("test3");
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(TestBeanForString.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("str3");
        assertEquals("test3", propDesc.getValue(myBean));
    }

    /**
     * 親クラスのプロパティに対して、int型のフィールドを取得する
     */
    @Test
    public void testGetValue_intWithExtendsClassFiled() throws Exception {
        TestBeanForString myBean = new TestBeanForString("test1", "test2");
        myBean.setAge3(1);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(TestBeanForString.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("age3");
        assertEquals(new Integer(1), propDesc.getValue(myBean));
    }

    /**
     * 親クラスのプロパティに対して、long型のフィールドを取得する
     */
    @Test
    public void testGetValue_longWithExtendsClassFiled() throws Exception {
        TestBeanForString myBean = new TestBeanForString("test1", "test2");
        myBean.setPrice3(1234567890);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(TestBeanForString.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("price3");
        assertEquals(new Long(1234567890), propDesc.getValue(myBean));
    }

    /**
     * 親クラスのプロパティに対して、boolean型のフィールドを取得する
     */
    public void testGetValue_booleanWithExtendsClassFiled() throws Exception {
        TestBeanForString myBean = new TestBeanForString("test1", "test2");
        myBean.setFieldFlag3(false);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(TestBeanForString.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("fieldFlag3");
        assertEquals(new Boolean(false), propDesc.getValue(myBean));
    }

    /**
     * 親クラスのプロパティに対して、object型のフィールドを取得する
     */
    @Test
    public void testGetValue_objectWithExtendsClassFiled() throws Exception {
        TestBeanForString myBean = new TestBeanForString("test1", "test2");
        TestBean3 testObject = new TestBean3("test");
        myBean.setTestBean3(testObject);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(TestBeanForString.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("testBean3");
        assertEquals(testObject, propDesc.getValue(myBean));
    }

    /**
     * テスト用クラス
     */
    public static class MyBean {

        public String ggg;

        /**
         * @return ggg
         */
        public String getGgg() {
            return ggg;
        }

        public void setGgg(String ggg) {
            this.ggg = ggg;
        }
    }
}
