package oscana.s2n.seasar.framework.beans.util;

/**
 * テスト用クラス
 *
 */
public class MyBean4 {

    public String value = "abc11111";

    public String xyz_value = " ";

    public String other$fff = null;

    public String exclude = "exclude11111";

    public String ab = "ab11111";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getXyz_value() {
        return xyz_value;
    }

    public void setXyz_value(String xyz_value) {
        this.xyz_value = xyz_value;
    }

    public String getOther$fff() {
        return other$fff;
    }

    public void setOther$fff(String other$fff) {
        this.other$fff = other$fff;
    }

    public String getExclude() {
        return exclude;
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    public String getAb() {
        return ab;
    }

    public void setAb(String ab) {
        this.ab = ab;
    }
}