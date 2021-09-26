//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package br.com.vfsilvacore.annotation;

import br.com.vfsilvacore.annotation.log.LogFormater;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;

public class SimpleFormatter implements LogFormater {
    private final String separator;

    public SimpleFormatter(String separator) {
        this.separator = separator;
    }

    public String format(SortedMap<LogFormater.FormatField, Object> vaues) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (Iterator var4 = vaues.entrySet().iterator(); var4.hasNext(); first = false) {
            Entry<LogFormater.FormatField, Object> entry = (Entry) var4.next();
            FormatField field = (FormatField) entry.getKey();
            Object value = entry.getValue();
            if (field == FormatField.MethodParameter && value instanceof Collection) {
                Collection<?> values = (Collection) value;
                Iterator var9 = values.iterator();

                while (var9.hasNext()) {
                    Object item = var9.next();
                    if (!first) {
                        sb.append(this.separator);
                    }

                    if (item instanceof MethodParameter) {
                        MethodParameter mp = (MethodParameter) item;
                        sb.append(mp.getName()).append("=").append(mp.getValue());
                    } else {
                        sb.append(item);
                    }
                }
            } else {
                if (!first) {
                    sb.append(this.separator);
                }

                sb.append(field.getKey()).append("=");
                sb.append(entry.getValue());
            }
        }

        return sb.toString();
    }
}
