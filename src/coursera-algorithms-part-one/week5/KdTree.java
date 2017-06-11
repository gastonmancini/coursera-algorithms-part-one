import java.util.LinkedList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {

    private static class Node {
        private Point2D point;
        private Node leftBottom;
        private Node rightTop;
        public Node(Point2D point, Node leftBottom, Node rightTop) {
            this.point = point;
            this.leftBottom = leftBottom;
            this.rightTop = rightTop;
        }
    }
    
    private Node root;
    private int size;
   
    public KdTree() {
        root = null;
        size = 0;
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public int size() {
        return size;
    }
    
    public void insert(Point2D point) {
        if (point == null) throw new NullPointerException("The point cannot be null.");
        root = insert(point, root, true);
    }
    
    private Node insert(Point2D point, Node currentNode, boolean isVerticalLevel) {
        
        if (currentNode != null && currentNode.point.equals(point)) {
            return currentNode;
        }
            
        if (currentNode == null) {
            size++;
            return new Node(point, null, null);
        } else {
            if (isVerticalLevel) {
                if (currentNode.point.x() > point.x()) {
                    currentNode.leftBottom = insert(point, currentNode.leftBottom, false);
                } else {
                    currentNode.rightTop = insert(point, currentNode.rightTop, false);
                }
            } else {
                if (currentNode.point.y() > point.y()) {
                    currentNode.leftBottom = insert(point, currentNode.leftBottom, true);
                } else {
                    currentNode.rightTop = insert(point, currentNode.rightTop, true);
                }
            }
            return currentNode;
        }
    }

    public boolean contains(Point2D point) {
        if (point == null) throw new NullPointerException("The point cannot be null.");
        return contains(point, root, true);
    }
    
    private boolean contains(Point2D point, Node currentNode, boolean isVerticalLevel) {
        if (currentNode == null) {
            return false;
        }
        if (currentNode.point.equals(point)) {
            return true;
        }        
        boolean contains = false;       
        if (isVerticalLevel) {
            if (currentNode.point.x() > point.x()) {
                contains = contains(point, currentNode.leftBottom, false);
            } else {
                contains = contains(point, currentNode.rightTop, false);
            }
        } else {
            if (currentNode.point.y() > point.y()) {
                contains = contains(point, currentNode.leftBottom, true);
            } else {
                contains = contains(point, currentNode.rightTop, true);
            }
        }
        return contains;
    }

    public void draw() {
        draw(root);
    }
    
    private void draw(Node node) {
        if (node == null) return;
        draw(node.leftBottom);
        node.point.draw();  
        draw(node.rightTop);
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException("The rect cannot be null.");
        LinkedList<Point2D> containedPoints = new LinkedList<Point2D>();        
        populateContainedPoints(containedPoints, root, rect, true);
        return containedPoints;
    }

    private void populateContainedPoints(LinkedList<Point2D> contained, Node node, RectHV rect, boolean isVerticalLevel) {
        if (node == null) return;      
        if (isVerticalLevel) {
            if (node.point.x() > rect.xmax()) {
                populateContainedPoints(contained, node.leftBottom, rect, false);               
            } else if (node.point.x() < rect.xmin()) {
                populateContainedPoints(contained, node.rightTop, rect, false);
            } else {
                populateContainedPoints(contained, node.leftBottom, rect, false);
                populateContainedPoints(contained, node.rightTop, rect, false);  
                if (rect.contains(node.point)) {
                    contained.add(node.point);
                }
            }            
        } else {
            if (node.point.y() > rect.ymax()) {
                populateContainedPoints(contained, node.leftBottom, rect, true);          
            } else if (node.point.y() < rect.ymin()) {
                populateContainedPoints(contained, node.rightTop, rect, true);           
            } else {
                populateContainedPoints(contained, node.leftBottom, rect, true);
                populateContainedPoints(contained, node.rightTop, rect, true);            
                if (rect.contains(node.point)) {
                    contained.add(node.point);
                }
            }   
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException("The point cannot be null.");
        Point2D nearestPoint = populateNearest(root, p, root.point, true);
        return nearestPoint;
    }
    
    private Point2D populateNearest(Node currentNode, Point2D originPoint, Point2D nearestPoint, boolean isVerticalLevel) {
        if (currentNode == null) return nearestPoint;       
        if (originPoint.distanceTo(nearestPoint) > nearestPoint.distanceTo(currentNode.point)) {
            nearestPoint = currentNode.point;
        }
        if (originPoint.distanceTo(nearestPoint) > originPoint.distanceTo(currentNode.point)) {
            if (isVerticalLevel) {
                    if (currentNode.point.x() > originPoint.x()) {
                        nearestPoint = 
                                populateNearest(currentNode.leftBottom, originPoint, nearestPoint, false);                 
                    } else if (currentNode.point.x() < originPoint.x()) {
                        nearestPoint = populateNearest(currentNode.rightTop, originPoint, nearestPoint, false);         
                    } else {
                        nearestPoint = populateNearest(currentNode.leftBottom, originPoint, nearestPoint, false);
                        nearestPoint = populateNearest(currentNode.rightTop, originPoint, nearestPoint, false);
                    }
            } else {
                    if (currentNode.point.y() > originPoint.y()) {
                        nearestPoint = populateNearest(currentNode.leftBottom, originPoint, nearestPoint, true);               
                    } else if (currentNode.point.y() < originPoint.y()) {
                        nearestPoint = populateNearest(currentNode.rightTop, originPoint, nearestPoint, true);              
                    } else {
                        nearestPoint = populateNearest(currentNode.leftBottom, originPoint, nearestPoint, true);
                        nearestPoint = populateNearest(currentNode.rightTop, originPoint, nearestPoint, true);
                    }
            }
        }
        return nearestPoint;
    }
}