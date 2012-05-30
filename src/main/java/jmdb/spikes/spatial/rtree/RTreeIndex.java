package jmdb.spikes.spatial.rtree;

import java.util.List;

/**
 * See Guttman http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.131.7887
 * And Beckmann http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.129.3731
 */
public class RTreeIndex {

    private Node rootNode = null;

    public List<String> findObjectsInRectangle(float x1, float y1, float x2, float y2) {
        return null;
    }

    public RTreeInsertion insertObjectWithKey(String key) {
        return new RTreeInsertion(this, key);
    }

    private void insert(String objectKey, Rectangle rectangle) {

    }

    static class RTreeInsertion {
        private RTreeIndex parent;
        public final String objectKey;
        public Rectangle rectangle;

        public RTreeInsertion(RTreeIndex parent, String objectKey) {
            this.parent = parent;
            this.objectKey = objectKey;
        }

        public RTreeIndex atPoint(float x, float y) {
            this.rectangle = new Rectangle(x, y, x, y);

            parent.insert(objectKey, rectangle);

            return parent;
        }
    }

}