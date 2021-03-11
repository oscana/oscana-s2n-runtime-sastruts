package oscana.s2n.seasar.framework.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * {@link StringUtil}のテスト。
 *
 */
public class StringUtilTest {

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testReplace() throws Exception {
        assertEquals("1", "1bc45", StringUtil.replace("12345", "23", "bc"));
        assertEquals("2", "1234ef", StringUtil.replace("12345", "5", "ef"));
        assertEquals("3", "ab2345", StringUtil.replace("12345", "1", "ab"));
        assertEquals("4", "a234a", StringUtil.replace("12341", "1", "a"));
        assertEquals("5", "ab234abab234ab", StringUtil.replace("1234112341",
                "1", "ab"));
        assertEquals("6", "a\\nb", StringUtil.replace("a\nb", "\n", "\\n"));

        assertNull(StringUtil.replace(null, "\n", "\\n"));
        assertNull(StringUtil.replace("a\nb", null, "\\n"));
        assertNull(StringUtil.replace("a\nb", "\n", null));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testSplit() throws Exception {
        String[] array = StringUtil.split("aaa\nbbb", "\n");
        assertEquals("1", 2, array.length);
        assertEquals("2", "aaa", array[0]);
        assertEquals("3", "bbb", array[1]);
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testSplit2() throws Exception {
        String[] array = StringUtil.split("aaa, bbb", ", ");
        assertEquals("1", 2, array.length);
        assertEquals("2", "aaa", array[0]);
        assertEquals("3", "bbb", array[1]);
    }

   /**
    * 指定した文字列がnullもしく空の場合、空の文字列の配列を戻すこと
    * @throws Exception
    */
    @Test
    public void testSplit3() throws Exception {
        String[] arraySetEmpty = StringUtil.split("", "\n");
        assertEquals(0, arraySetEmpty.length);

        String[] arraySetNull = StringUtil.split(null, "\n");
        assertEquals(0, arraySetNull.length);
    }

   /**
    * 指定した文字列の左側に空白がある場合、空白を削除できること
    * @throws Exception
    */
    @Test
    public void testLtrim_deleteSpace() throws Exception {
        assertEquals("xzytrim", StringUtil.ltrim(" xzytrim"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testLtrim() throws Exception {
        assertEquals("1", "trim", StringUtil.ltrim("zzzytrim", "xyz"));
        assertEquals("2", "", StringUtil.ltrim("xyz", "xyz"));
        assertNull(StringUtil.ltrim(null, "xyz"));
    }

    /**
     * 指定した文字列の右側に空白がある場合、空白を削除できること
     * @throws Exception
     */
    @Test
    public void testRtrim_deleteSpace() throws Exception {
        assertEquals("xzytrim", StringUtil.rtrim("xzytrim "));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testRtrim() throws Exception {
        assertEquals("trim", StringUtil.rtrim("trimxxxx", "x"));
        assertEquals("", StringUtil.rtrim("xyz", "xyz"));
        assertEquals("trimxxxx", StringUtil.rtrim("trimxxxx", "y"));
        assertNull(StringUtil.rtrim(null, "xyz"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testTrimSuffix() throws Exception {
        assertEquals("aaa", StringUtil.trimSuffix("aaaLogic", "Logic"));
        assertEquals("aaaLogic", StringUtil.trimSuffix("aaaLogic", null));
        assertNull(StringUtil.trimSuffix(null, "Logic"));
        assertEquals("aaaLogic", StringUtil.trimSuffix("aaaLogic", "test"));

    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testTrimPrefix() throws Exception {
        assertEquals("AAA", StringUtil.trimPrefix("T_AAA", "T_"));
        assertEquals("T_AAA", StringUtil.trimPrefix("T_AAA", null));
        assertNull(StringUtil.trimPrefix(null, "T_"));
        assertEquals("T_AAA", StringUtil.trimPrefix("T_AAA", "S_"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testIsBlank() throws Exception {
        assertEquals("1", true, StringUtil.isBlank(" "));
        assertEquals("2", true, StringUtil.isBlank(""));
        assertEquals("3", false, StringUtil.isBlank("a"));
        assertEquals("4", false, StringUtil.isBlank(" a "));
        assertTrue(StringUtil.isBlank(null));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testIsNotBlank() throws Exception {
        assertEquals("1", false, StringUtil.isNotBlank(" "));
        assertEquals("2", false, StringUtil.isNotBlank(""));
        assertEquals("3", true, StringUtil.isNotBlank("a"));
        assertEquals("4", true, StringUtil.isNotBlank(" a "));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testContains() throws Exception {
        assertEquals("1", true, StringUtil.contains("a", 'a'));
        assertEquals("2", true, StringUtil.contains("abc", 'b'));
        assertEquals("3", false, StringUtil.contains("abc", 'd'));
        assertFalse(StringUtil.contains(null, 'a'));
        assertFalse(StringUtil.contains("", 'a'));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testContains2() throws Exception {
        assertEquals("1", true, StringUtil.contains("a", "a"));
        assertEquals("2", true, StringUtil.contains("abc", "b"));
        assertEquals("3", false, StringUtil.contains("abc", "d"));
        assertFalse(StringUtil.contains(null, "a"));
        assertFalse(StringUtil.contains("", "a"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testEquals() throws Exception {
        assertEquals("1", true, StringUtil.equals("a", "a"));
        assertEquals("2", true, StringUtil.equals(null, null));
        assertEquals("3", false, StringUtil.equals("a", null));
        assertEquals("4", false, StringUtil.equals(null, "a"));
        assertEquals("5", false, StringUtil.equals("a", "b"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testEqualsIgnoreCase() throws Exception {
        assertEquals("1", true, StringUtil.equalsIgnoreCase("a", "a"));
        assertEquals("2", true, StringUtil.equalsIgnoreCase("a", "A"));
        assertEquals("3", true, StringUtil.equalsIgnoreCase("A", "a"));
        assertEquals("4", true, StringUtil.equalsIgnoreCase(null, null));
        assertEquals("5", false, StringUtil.equalsIgnoreCase("a", null));
        assertEquals("6", false, StringUtil.equalsIgnoreCase(null, "a"));
        assertEquals("7", false, StringUtil.equalsIgnoreCase("a", "b"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testDecapitalize() throws Exception {
        assertEquals("abc", StringUtil.decapitalize("abc"));
        assertEquals("abc", StringUtil.decapitalize("Abc"));
        assertEquals("ABC", StringUtil.decapitalize("ABC"));
        assertNull(StringUtil.decapitalize(null));
        assertEquals("", StringUtil.decapitalize(""));
        assertEquals("a", StringUtil.decapitalize("a"));
        assertEquals("a", StringUtil.decapitalize("A"));
    }

    /**
     * 指定した文字列がnull、空の場合、そのまま戻ること
     * @throws Exception
     */
    @Test
    public void testCapitalize() throws Exception {
        assertNull(StringUtil.capitalize(null));
        assertEquals("", StringUtil.capitalize(""));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testEndsWithIgnoreCase() throws Exception {
        assertTrue(StringUtil.endsWithIgnoreCase("setHogeAaa", "Aaa"));
        assertTrue(StringUtil.endsWithIgnoreCase("setHogeAaa", "aaa"));
        assertTrue(StringUtil.endsWithIgnoreCase("aaa_hoge", "HOge"));
        assertFalse(StringUtil.endsWithIgnoreCase("setHogeaa", "Aaa"));
        assertFalse(StringUtil.endsWithIgnoreCase("aa", "Aaa"));
        assertFalse(StringUtil.endsWithIgnoreCase(null, "Aaa"));
        assertFalse(StringUtil.endsWithIgnoreCase("aa", null));
        assertFalse(StringUtil.endsWithIgnoreCase(null, null));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testStartsWithIgnoreCase() throws Exception {
        assertTrue(StringUtil.startsWithIgnoreCase("isHoge", "is"));
        assertTrue(StringUtil.startsWithIgnoreCase("isHoge", "IS"));
        assertTrue(StringUtil.startsWithIgnoreCase("ISHoge", "is"));
        assertFalse(StringUtil.startsWithIgnoreCase("isHoge", "iss"));
        assertFalse(StringUtil.startsWithIgnoreCase("is", "iss"));
        assertFalse(StringUtil.startsWithIgnoreCase(null, "Aaa"));
        assertFalse(StringUtil.startsWithIgnoreCase("aa", null));
        assertFalse(StringUtil.startsWithIgnoreCase(null, null));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testSubstringFromLast() throws Exception {
        assertEquals("ab", StringUtil.substringFromLast("abc", "c"));
        assertEquals("abcab", StringUtil.substringFromLast("abcabc", "c"));
        assertEquals("abc", StringUtil.substringFromLast("abc", ""));
        assertEquals("abc", StringUtil.substringFromLast("abc", null));
        assertEquals("abc", StringUtil.substringFromLast("abc", "dddd"));
        assertEquals("", StringUtil.substringFromLast("", "dddd"));
        assertNull(StringUtil.substringFromLast(null, "dddd"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testSubstringToLast() throws Exception {
        assertEquals("", StringUtil.substringToLast("abc", "c"));
        assertEquals("c", StringUtil.substringToLast("abc", "b"));
        assertEquals("c", StringUtil.substringToLast("abcbc", "b"));
        assertEquals("abc", StringUtil.substringToLast("abc", ""));
        assertEquals("abc", StringUtil.substringToLast("abc", null));
        assertEquals("abc", StringUtil.substringToLast("abc", "dddd"));
        assertEquals("", StringUtil.substringToLast("", "dddd"));
        assertNull(StringUtil.substringToLast(null, "dddd"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testToHex() throws Exception {
        assertEquals("010203", StringUtil.toHex(new byte[] { 1, 2, 3 }));
        byte[] bytes = null;
        assertEquals("", StringUtil.toHex(bytes));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testToHex2() throws Exception {
        assertEquals("0001", StringUtil.toHex(1));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testAppendHex() throws Exception {
        StringBuffer buf = new StringBuffer();
        StringUtil.appendHex(buf, (byte) 1);
        assertEquals("01", buf.toString());
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testCamelize() throws Exception {
        assertNull(StringUtil.camelize(null));
        assertEquals("Emp", StringUtil.camelize("EMP"));
        assertEquals("AaaBbb", StringUtil.camelize("AAA_BBB"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testDecamelize() throws Exception {
        assertNull(StringUtil.decamelize(null));
        assertEquals("EMP", StringUtil.decamelize("Emp"));
        assertEquals("AAA_BBB", StringUtil.decamelize("aaaBbb"));
        assertEquals("AAA_BBB", StringUtil.decamelize("AaaBbb"));
        assertEquals("AAA_BBB_C", StringUtil.decamelize("aaaBbbC"));
        assertEquals("E", StringUtil.decamelize("e"));
        assertEquals("E", StringUtil.decamelize("E"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testIsNumver() throws Exception {
        assertFalse(StringUtil.isNumber(null));
        assertTrue(StringUtil.isNumber("0123456789"));
        assertFalse(StringUtil.isNumber("aaaBBBccc"));
        assertFalse(StringUtil.isNumber("０１２３４５６７８９"));
        assertFalse(StringUtil.isNumber(""));
        assertFalse(StringUtil.isNumber("01234abcdef"));
        assertFalse(StringUtil.isNumber("abcdef01234"));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(StringUtil.isEmpty(null));
        assertTrue(StringUtil.isEmpty(""));
        assertFalse(StringUtil.isEmpty(" "));
    }

    /**
     * （移植元FM付属テストケース）
     * @throws Exception
     */
    @Test
    public void testIsNotEmpty() throws Exception {
        assertFalse(StringUtil.isNotEmpty(null));
        assertFalse(StringUtil.isNotEmpty(""));
        assertTrue(StringUtil.isNotEmpty(" "));
    }

}