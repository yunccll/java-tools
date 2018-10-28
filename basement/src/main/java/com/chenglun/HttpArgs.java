package com.chenglun;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class HttpArgs {
    public static class BaseParameters<T> {
        private Map<String, Object> p;
        public BaseParameters()
        {
            this.p = new LinkedHashMap<>();
        }
        public T put(final String key, final Object value){
            this.p.put(key, value);
            return (T)this;
        }
        public T clear()
        {
            this.p.clear();
            return (T)this;
        }
        public Map<String, Object> getMap(){
            return this.p;
        }

        public void forEach(BiConsumer<? super String, ? super Object> action) {
            this.p.forEach(action);
        }
    }

    public static class Parameters extends BaseParameters<Parameters>
    {
    }
    public static class Headers extends  BaseParameters<Headers>
    {
    }

    public static class Data
    {
        public static Data from(Map<String, Object> map)
        {
            return new Data(map);
        }
        private Map<String, Object> data;
        private Data(Map<String, Object> data)
        {
            this.data = data;
        }
        public Object get(final String name){
            return this.data.get(name);
        }
    }
    public static class JsonData extends  BaseParameters<JsonData>
    {
    }
}
