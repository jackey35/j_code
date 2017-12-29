package com.jack.fo.util;

public class PageUtil {
	public static final int PAGE_SIZE=10;
	/**
     * 计算分页总页数
     * @param count 总记录数
     * @return  总页数
     */
    public static int getPage(int count,int pageSize){
        int page=count/pageSize;
        int pageMod = count%pageSize;
        
        if (pageMod > 0) {
            page += 1;
        }
        
        if (page == 0) {
            page = 1;
        }
        
        return page;
    }
}
