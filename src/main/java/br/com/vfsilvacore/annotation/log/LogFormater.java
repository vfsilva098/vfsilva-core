//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package br.com.vfsilvacore.annotation.log;

import java.util.SortedMap;

public interface LogFormater {
    String format(SortedMap<FormatField, Object> var1);

    class MethodParameter {
        private Object value;
        private String name;

        public MethodParameter(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return this.name;
        }

        public Object getValue() {
            return this.value;
        }

        public String toString() {
            return "MethodParameter [value=" + this.value + ", name=" + this.name + "]";
        }

        public int hashCode() {
            int result = 1;
            result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
            result = 31 * result + (this.value == null ? 0 : this.value.hashCode());
            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            } else if (obj == null) {
                return false;
            } else if (this.getClass() != obj.getClass()) {
                return false;
            } else {
                MethodParameter other = (MethodParameter)obj;
                if (this.name == null) {
                    if (other.name != null) {
                        return false;
                    }
                } else if (!this.name.equals(other.name)) {
                    return false;
                }

                if (this.value == null) {
                    return other.value == null;
                } else return this.value.equals(other.value);
            }
        }
    }

    enum FormatField implements Comparable<FormatField> {
        ClassName("c"),
        MethodName("m"),
        RetunrValue("r"),
        ExceptionClass("e"),
        ExceptionMessage("msg"),
        MethodParameter((String)null);

        private String key;

        private FormatField(String key) {
            this.key = key;
        }

        public String getKey() {
            return this.key;
        }
    }
}
