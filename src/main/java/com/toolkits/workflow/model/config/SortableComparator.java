/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.model.config;

import java.util.Comparator;

/**
 * 基于Sortable接口的比机器实现
 * @author XiaokaiSun
 * @version $Id: SortableCompetor.java, v 0.1 2017-11-24 17:35 XiaokaiSun Exp $$
 */
public class SortableComparator implements Comparator<Sortable> {

    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     * 
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second.
     */
    @Override
    public int compare(Sortable o1, Sortable o2) {
        int seq1 = o1.getSeq();
        int seq2 = o2.getSeq();
        return seq1 - seq2;
    }
}