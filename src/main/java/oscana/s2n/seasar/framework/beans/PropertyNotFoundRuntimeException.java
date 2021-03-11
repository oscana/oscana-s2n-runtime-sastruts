/*
 * 取り込み元
 *    ライブラリ名：     seasar2
 *    クラス名：         org.seasar.framework.beans.PropertyNotFoundRuntimeException
 *    ソースリポジトリ： https://github.com/seasarorg/seasar2/blob/master/seasar2/s2-framework/src/main/java/org/seasar/framework/beans/PropertyNotFoundRuntimeException.java
 *
 * 上記ファイルを取り込み、修正を加えた。
 *
 * Copyright 2020 TIS Inc.
 *
 * Copyright 2004-2013 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package oscana.s2n.seasar.framework.beans;

/**
 * プロパティが見つからない場合、スローする例外クラス。<br>
 * <br>
 * 移植内容の変更点：<br>
 * <br>
 * ・PropertyNotFoundRuntimeException例外をスローするだけのため、例外のメッセージコード、プロパティ名などのパラメータを省略する。<br>
 *
 * @author higa
 */
public class PropertyNotFoundRuntimeException extends RuntimeException {

    public PropertyNotFoundRuntimeException() {
    }
}