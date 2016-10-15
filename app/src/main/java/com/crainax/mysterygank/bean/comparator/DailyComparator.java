package com.crainax.mysterygank.bean.comparator;

import com.crainax.mysterygank.bean.DailyEntity;

import java.util.Comparator;

/**
 * Project: MysteryGank <br/>
 * Package: com.crainax.mysterygank.bean <br/>
 * Description: The comparator used to sort the Meizhi List by reverse order. <br/>
 * <hr/>
 *
 * @author Crainax <br/>
 * @version 1.0 <br/>
 * @since 2016/5/8 <br/>
 */
public class DailyComparator implements Comparator<DailyEntity> {

    @Override
    public int compare(DailyEntity lhs, DailyEntity rhs) {
        return -1 * lhs.getPublishedAt().compareTo(rhs.getPublishedAt());
    }
}
