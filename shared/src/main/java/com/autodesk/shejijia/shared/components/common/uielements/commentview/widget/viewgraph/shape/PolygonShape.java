package com.autodesk.shejijia.shared.components.common.uielements.commentview.widget.viewgraph.shape;

import android.graphics.Path;

/**
 * @author Malidong .
 * @version 1.0 .
 *          created at: 2016/1/21 0021 15:18 .
 *          # update （update log） .
 *          <p/>
 *          + v1.0 .
 * @file user  .
 * @brief info .
 */
public interface PolygonShape {

    /**
     * Return a closed valid Path
     *
     * @param polygonShapeSpec polygonal specs
     * @return a Path
     */
    Path getPolygonPath(PolygonShapeSpec polygonShapeSpec);
}
