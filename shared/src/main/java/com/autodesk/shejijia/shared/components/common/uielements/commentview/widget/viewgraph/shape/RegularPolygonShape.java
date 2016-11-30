package com.autodesk.shejijia.shared.components.common.uielements.commentview.widget.viewgraph.shape;

/**
 * @author Malidong .
 * @version 1.0 .
 *          created at: 2016/1/21 0021 15:21 .
 *          # update （update log） .
 *          <p/>
 *          + v1.0 .
 * @file user  .
 * @brief info .
 */
public class RegularPolygonShape extends BasePolygonShape {

    @Override
    protected void addEffect(float currentX, float currentY, float nextX, float nextY) {
        getPath().lineTo(nextX, nextY);
    }
}
