package com.base.framework.bean.page;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Page
 * @author zhouyw
 * @date 2016.09.10
 */
public class Page<T> implements Serializable{

    private static final long serialVersionUID = 1L;

    private int pageSize = 10; //默认 单页 查询数
    private int totalRecord = 0;//默认 总记录数
    private int currentPage = 1;//默认 首页   当前页
    private int totalPage;      //总页数
    private List<T> records = Collections.emptyList();

    public Page() {
    }

    public Page(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public Page(int currentPage, int totalRecord, int pageSize) {
        this.currentPage = currentPage;
        this.totalRecord = totalRecord;
        this.pageSize = pageSize;
    }

    public int getRecordStart() {
        return this.currentPage > 0?(this.currentPage - 1) * this.pageSize + 1:0;
    }

    public int getRecordEnd() {
        return this.currentPage > 0?this.currentPage * this.pageSize:0;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return this.totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;

        this.totalPage = (int)Math.floor((double)this.totalRecord * 1.0D / (double)this.pageSize);
        if(this.totalRecord % this.pageSize != 0) {
            ++this.totalPage;
        }
        if(this.totalPage== 0){
            this.totalPage = 1;
        }

    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public List<T> getRecords() {
        return this.records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public static class FieldDomain {
        public static final String RECORD_START = "recordStart";
        public static final String RECORD_END = "recordEnd";
        public static final String PAGE_SIZE = "pageSize";
        public static final String TOTAL_RECORD = "totalRecord";
        public static final String CURRENT_PAGE = "currentPage";
        public static final String TOTAL_PAGE = "totalPage";
        public static final String RECORDS = "records";

        public FieldDomain() {
        }
    }
}