package com.crainax.mysterygank.bean;

/**
 * Created by Crainax on 2016/4/18.
 */
public class HttpResult<T> {
    /**
     * 解析Json中的Error数据
     */
    private boolean error;
    /**
     * 封装返回数据
     */
    private T results;

    public T getResults() {
        return results;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
