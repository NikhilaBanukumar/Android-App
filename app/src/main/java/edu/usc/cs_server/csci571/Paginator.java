package edu.usc.cs_server.csci571;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhilabanukumar on 4/22/17.
 */

public class Paginator {
    public static  int TOTAL_NUM_ITEMS = 0;
    public static final int ITEMS_PER_PAGE = 10;
    public static  int ITEM_REM;
    public static  int LAST_PAGE;
    public List<TypeDetails> list;
    public int len;
    public Paginator(List<TypeDetails> list, int length){
        Paginator.TOTAL_NUM_ITEMS = length;
        ITEM_REM = TOTAL_NUM_ITEMS % ITEMS_PER_PAGE;
        LAST_PAGE = TOTAL_NUM_ITEMS/ITEMS_PER_PAGE;

        this.list = list;
        len=list.size();

        System.out.println("DEBUG: Pagination Length of the List  length"+len);
    }


    public List<TypeDetails> generatePage(int currentPage){
        int startIndex = currentPage*ITEMS_PER_PAGE;
        int numOfData = ITEMS_PER_PAGE;
        System.out.println("DEBUG: Paginator LAST_PAGE and ITEM_REM and CurrentPage"+LAST_PAGE+" "+ITEM_REM+" "+currentPage);

        List<TypeDetails> pageData = new ArrayList<>();

        if(currentPage== LAST_PAGE && ITEM_REM > 0){
            System.out.println("spaceX"+len);
            System.out.println("start index"+startIndex);
            for(int i = startIndex;i<len;i++){
                System.out.println("paginator list:"+list.get(i)+i);
                pageData.add(list.get(i));
            }
        }
        else{
            for(int i=startIndex;i<startIndex+numOfData;i++){
                pageData.add(list.get(i));
            }
        }
        System.out.println("DEBUG: Page Data returned"+pageData.toString());

        return pageData;
    }

}
