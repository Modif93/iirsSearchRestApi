package com.connector.iirsSearch.util;

import org.osgeo.proj4j.*;
import org.osgeo.proj4j.proj.Projection;
import org.springframework.data.projection.ProjectionFactory;

import java.awt.geom.Point2D;

public class UtilUtmToWgs84 {

    public static ProjCoordinate transform(Double x, Double y) {
        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
        CRSFactory csFactory = new CRSFactory();
        CoordinateReferenceSystem GOOGLE = csFactory.createFromParameters("EPSG:32652", "proj=utm +zone=52");
        CoordinateReferenceSystem WGS84 = csFactory.createFromParameters("WGS84", "+proj=longlat +ellps=WGS84 +datum=WGS84 +no_defs");
        CoordinateTransform trans = ctFactory.createTransform(GOOGLE, WGS84);
        ProjCoordinate p = new ProjCoordinate();
        ProjCoordinate p2 = new ProjCoordinate();
        p.x = x;
        p.y = y;

        return trans.transform(p, p2);
    }
}