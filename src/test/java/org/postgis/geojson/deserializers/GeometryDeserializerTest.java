package org.postgis.geojson.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.postgis.Geometry;
import org.postgis.GeometryCollection;
import org.postgis.LineString;
import org.postgis.MultiLineString;
import org.postgis.MultiPoint;
import org.postgis.MultiPolygon;
import org.postgis.Point;
import org.postgis.Polygon;

/**
 *
 * @author mayconbordin
 * @author Sebastien Deleuze
 */
public class GeometryDeserializerTest {
    protected ObjectMapper mapper;
    
    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("MyModule");
        module.addDeserializer(Geometry.class, new GeometryDeserializer());
        module.addDeserializer(Geometry.class, new GeometryDeserializer());
        module.addDeserializer(Point.class, new GeometryDeserializer());
        module.addDeserializer(Polygon.class, new GeometryDeserializer());
        module.addDeserializer(LineString.class, new GeometryDeserializer());
        module.addDeserializer(MultiPolygon.class, new GeometryDeserializer());
        module.addDeserializer(MultiPoint.class, new GeometryDeserializer());
        module.addDeserializer(MultiLineString.class, new GeometryDeserializer());
        module.addDeserializer(GeometryCollection.class, new GeometryDeserializer());
        mapper.registerModule(module);
    }
    
    @Test
    public void testDeserializePoint() throws Exception {
        System.out.println("deserializePoint");
        
        String json = "{\"type\": \"Point\",\"coordinates\": [125.6, 10.1]}";
        
        Point p = mapper.readValue(json, Point.class);
        
        assertNotNull(p);
        assertEquals(125.6, p.getX(), 0);
        assertEquals(10.1, p.getY(), 0);
        assertEquals(0.0, p.getZ(), 0);
    }
    
    @Test
    public void testDeserializeLineString() throws Exception {
        System.out.println("deserializeLineString");
        
        String json = "{\"type\": \"LineString\",\"coordinates\": [ [100.0, 0.0], [101.0, 1.0] ]}";
        
        LineString p = mapper.readValue(json, LineString.class);
        
        assertNotNull(p);
        assertEquals(2, p.numPoints());
        assertEquals(100.0, p.getPoint(0).getX(), 0.0);
        assertEquals(0.0, p.getPoint(0).getY(), 0.0);
        assertEquals(101.0, p.getPoint(1).getX(), 0.0);
        assertEquals(1.0, p.getPoint(1).getY(), 0.0);
    }
    
    @Test
    public void testDeserializePolygon() throws Exception {
        System.out.println("deserializePolygon");

        String json = "{\"type\": \"Polygon\",\"coordinates\": "
                + "[[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]]]}";
        
        Polygon p = mapper.readValue(json, Polygon.class);
        
        assertNotNull(p);
        assertEquals(1, p.numRings());
        assertEquals(5, p.numPoints());
        
        assertEquals(100.0, p.getRing(0).getPoint(0).getX(), 0.0);
        assertEquals(0.0, p.getRing(0).getPoint(0).getY(), 0.0);
        
        assertEquals(101.0, p.getRing(0).getPoint(1).getX(), 0.0);
        assertEquals(0.0, p.getRing(0).getPoint(1).getY(), 0.0);
        
        assertEquals(101.0, p.getRing(0).getPoint(2).getX(), 0.0);
        assertEquals(1.0, p.getRing(0).getPoint(2).getY(), 0.0);
        
        assertEquals(100.0, p.getRing(0).getPoint(3).getX(), 0.0);
        assertEquals(1.0, p.getRing(0).getPoint(3).getY(), 0.0);
        
        assertEquals(100.0, p.getRing(0).getPoint(4).getX(), 0.0);
        assertEquals(0.0, p.getRing(0).getPoint(4).getY(), 0.0);
    }
    
    @Test
    public void testDeserializeMultiLineString() throws Exception {
        System.out.println("deserializeMultiLineString");

        String json = "{\"type\": \"MultiLineString\",\"coordinates\": "
                + "[[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]]]}";
        
        MultiLineString p = mapper.readValue(json, MultiLineString.class);
        
        assertNotNull(p);
        assertEquals(1, p.numLines());
        assertEquals(5, p.numPoints());
        
        assertEquals(100.0, p.getLine(0).getPoint(0).getX(), 0.0);
        assertEquals(0.0, p.getLine(0).getPoint(0).getY(), 0.0);
        
        assertEquals(101.0, p.getLine(0).getPoint(1).getX(), 0.0);
        assertEquals(0.0, p.getLine(0).getPoint(1).getY(), 0.0);
        
        assertEquals(101.0, p.getLine(0).getPoint(2).getX(), 0.0);
        assertEquals(1.0, p.getLine(0).getPoint(2).getY(), 0.0);
        
        assertEquals(100.0, p.getLine(0).getPoint(3).getX(), 0.0);
        assertEquals(1.0, p.getLine(0).getPoint(3).getY(), 0.0);
        
        assertEquals(100.0, p.getLine(0).getPoint(4).getX(), 0.0);
        assertEquals(0.0, p.getLine(0).getPoint(4).getY(), 0.0);
    }
    
    @Test
    public void testDeserializeMultiPoint() throws Exception {
        System.out.println("deserializeMultiPoint");
        
        String json = "{\"type\": \"MultiPoint\",\"coordinates\": [ [100.0, 0.0], [101.0, 1.0] ]}";
        
        MultiPoint p = mapper.readValue(json, MultiPoint.class);
        
        assertNotNull(p);
        assertEquals(2, p.numPoints());
        assertEquals(100.0, p.getPoint(0).getX(), 0.0);
        assertEquals(0.0, p.getPoint(0).getY(), 0.0);
        assertEquals(101.0, p.getPoint(1).getX(), 0.0);
        assertEquals(1.0, p.getPoint(1).getY(), 0.0);
    }
    
    @Test
    public void testDeserializeMultiPolygon() throws Exception {
        System.out.println("deserializeMultiPolygon");

        String json = "{\"type\": \"MultiPolygon\",\"coordinates\": "
                + "[[[[102.0, 2.0], [103.0, 2.0], [103.0, 3.0], [102.0, 3.0], [102.0, 2.0]]]," 
                + "[[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]],"
                + "[[100.2, 0.2], [100.8, 0.2], [100.8, 0.8], [100.2, 0.8], [100.2, 0.2]]]"
                + "]}";
        
        MultiPolygon p = mapper.readValue(json, MultiPolygon.class);
        
        assertNotNull(p);
        assertEquals(2, p.numPolygons());
        assertEquals(1, p.getPolygon(0).numRings());
        assertEquals(2, p.getPolygon(1).numRings());
        assertEquals(15, p.numPoints());
        
        assertEquals(102.0, p.getPolygon(0).getRing(0).getPoint(0).getX(), 0.0);
        assertEquals(2.0, p.getPolygon(0).getRing(0).getPoint(0).getY(), 0.0);
        
        assertEquals(103.0, p.getPolygon(0).getRing(0).getPoint(1).getX(), 0.0);
        assertEquals(2.0, p.getPolygon(0).getRing(0).getPoint(1).getY(), 0.0);
        
        assertEquals(103.0, p.getPolygon(0).getRing(0).getPoint(2).getX(), 0.0);
        assertEquals(3.0, p.getPolygon(0).getRing(0).getPoint(2).getY(), 0.0);
        
        assertEquals(102.0, p.getPolygon(0).getRing(0).getPoint(3).getX(), 0.0);
        assertEquals(3.0, p.getPolygon(0).getRing(0).getPoint(3).getY(), 0.0);
        
        assertEquals(102.0, p.getPolygon(0).getRing(0).getPoint(4).getX(), 0.0);
        assertEquals(2.0, p.getPolygon(0).getRing(0).getPoint(4).getY(), 0.0);
    }
    
    @Test
    public void testDeserializegGeometryCollection() throws Exception {
        System.out.println("deserializeGeometryCollection");
        
        String json = "{\"type\": \"GeometryCollection\",\"geometries\": ["
                + "{ \"type\": \"Point\", \"coordinates\": [100.0, 0.0]},"
                + "{ \"type\": \"LineString\", \"coordinates\": [ [101.0, 0.0], [102.0, 1.0] ] }"
                + "]}";
        
        GeometryCollection p = mapper.readValue(json, GeometryCollection.class);
        
        assertNotNull(p);
        assertEquals(2, p.numGeoms());
        
        assertEquals("Point", p.getGeometries()[0].getClass().getSimpleName());
        assertEquals(100.0, ((Point)p.getGeometries()[0]).getX(), 0);
        assertEquals(0.0, ((Point)p.getGeometries()[0]).getY(), 0);
        
        assertEquals("LineString", p.getGeometries()[1].getClass().getSimpleName());
        assertEquals(101.0, ((LineString)p.getGeometries()[1]).getPoint(0).getX(), 0);
        assertEquals(0.0, ((LineString)p.getGeometries()[1]).getPoint(0).getY(), 0);
        assertEquals(102.0, ((LineString)p.getGeometries()[1]).getPoint(1).getX(), 0);
        assertEquals(1.0, ((LineString)p.getGeometries()[1]).getPoint(1).getY(), 0);
    }
    
}
