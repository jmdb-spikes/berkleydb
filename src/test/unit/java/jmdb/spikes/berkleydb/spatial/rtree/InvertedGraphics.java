package jmdb.spikes.berkleydb.spatial.rtree;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

import static java.lang.Math.abs;

/**
 * The coordinate system of the standard Graphics2D means that the 0,0 is in the top left This is normally in the lower
 * left, so this class wraps a Graphics2D and inverts everything
 */
public class InvertedGraphics extends Graphics2D {

    private final Graphics2D delegate;
    private final int widthInPixels;
    private final int heightInPixels;

    public InvertedGraphics(Graphics2D delegate, int widthInPixels, int heightInPixels) {
        this.delegate = delegate;
        this.widthInPixels = widthInPixels;
        this.heightInPixels = heightInPixels;
    }

    private int invert(int y) {
        return heightInPixels - y;
    }

    private float invert(float y) {
        return heightInPixels - y;
    }

    private double invert(double y) {
        return heightInPixels - y;
    }


    @Override public void draw(Shape s) {
        delegate.draw(s);
    }

    @Override public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
        return delegate.drawImage(img, xform, obs);
    }

    @Override public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
        delegate.drawImage(img, op, x, invert(y));
    }

    @Override public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
        delegate.drawRenderedImage(img, xform);
    }



    @Override public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
        delegate.drawRenderableImage(img, xform);
    }

    @Override public void drawString(String str, int x, int y) {
        delegate.drawString(str, x, invert(y));
    }

    @Override public void drawString(String str, float x, float y) {
        delegate.drawString(str, x, invert(y));
    }

    @Override public void drawString(AttributedCharacterIterator iterator, int x, int y) {
        delegate.drawString(iterator, x, invert(y));
    }

    @Override public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
        return delegate.drawImage(img, x, invert(y), observer);
    }

    @Override public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
        return delegate.drawImage(img, x, invert(y), width, height, observer);
    }

    @Override public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
        return delegate.drawImage(img, x, invert(y), bgcolor, observer);
    }

    @Override
    public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
        return delegate.drawImage(img, x, invert(y), width, height, bgcolor, observer);
    }

    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
        return delegate.drawImage(img, dx1, invert(dy1), dx2, invert(dy2), sx1, sy1, sx2, sy2, observer);
    }

    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2,
                             int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
        return delegate.drawImage(img, dx1, invert(dy1), dx2, invert(dy2), sx1, sy1, sx2, sy2, bgcolor, observer);
    }

    @Override public void dispose() {
        delegate.dispose();
    }

    @Override public void drawString(AttributedCharacterIterator iterator, float x, float y) {
        delegate.drawString(iterator, x, invert(y));
    }

    @Override public void drawGlyphVector(GlyphVector g, float x, float y) {
        delegate.drawGlyphVector(g, x, invert(y));
    }

    @Override public void fill(Shape s) {
        delegate.fill(s);
    }

    @Override public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
        return delegate.hit(rect, s, onStroke);
    }

    @Override public GraphicsConfiguration getDeviceConfiguration() {
        return delegate.getDeviceConfiguration();
    }

    @Override public void setComposite(Composite comp) {
        delegate.setComposite(comp);
    }

    @Override public void setPaint(Paint paint) {
        delegate.setPaint(paint);
    }

    @Override public void setStroke(Stroke s) {
        delegate.setStroke(s);
    }

    @Override public void setRenderingHint(RenderingHints.Key hintKey, Object hintValue) {
        delegate.setRenderingHint(hintKey, hintValue);
    }

    @Override public Object getRenderingHint(RenderingHints.Key hintKey) {
        return delegate.getRenderingHint(hintKey);
    }

    @Override public void setRenderingHints(Map<?, ?> hints) {
        delegate.setRenderingHints(hints);
    }

    @Override public void addRenderingHints(Map<?, ?> hints) {
        delegate.addRenderingHints(hints);
    }

    @Override public RenderingHints getRenderingHints() {
        return delegate.getRenderingHints();
    }

    @Override public Graphics create() {
        return delegate.create();
    }

    @Override public void translate(int x, int y) {
        delegate.translate(x, invert(y));
    }

    @Override public Color getColor() {
        return delegate.getColor();
    }

    @Override public void setColor(Color c) {
        delegate.setColor(c);
    }

    @Override public void setPaintMode() {
        delegate.setPaintMode();
    }

    @Override public void setXORMode(Color c1) {
        delegate.setXORMode(c1);
    }

    @Override public Font getFont() {
        return delegate.getFont();
    }

    @Override public void setFont(Font font) {
        delegate.setFont(font);
    }

    @Override public FontMetrics getFontMetrics(Font f) {
        return delegate.getFontMetrics(f);
    }

    @Override public Rectangle getClipBounds() {
        return delegate.getClipBounds();
    }

    @Override public void clipRect(int x, int y, int width, int height) {
        delegate.clipRect(x, invert(y), width, height);
    }

    @Override public void setClip(int x, int y, int width, int height) {
        delegate.setClip(x, invert(y), width, height);
    }

    @Override public Shape getClip() {
        return delegate.getClip();
    }

    @Override public void setClip(Shape clip) {
        delegate.setClip(clip);
    }

    @Override public void copyArea(int x, int y, int width, int height, int dx, int dy) {
        delegate.copyArea(x, invert(y), width, height, dx, dy);
    }

    @Override public void drawLine(int x1, int y1, int x2, int y2) {
        int invertedY1 = heightInPixels - y1;
        int invertedY2 = heightInPixels - y2;

        delegate.drawLine(x1, invertedY1, x2, invertedY2);
    }

    @Override public void fillRect(int x, int y, int width, int height) {
        int invertedY = heightInPixels - (y + height);
        delegate.fillRect(x, invertedY, width, height);
    }

    @Override public void drawRect(int x, int y, int width, int height) {
        //int invertedY = heightInPixels - (y + height);
        super.drawRect(x, y, width, height);
    }

    @Override public void clearRect(int x, int y, int width, int height) {
        delegate.clearRect(x, invert(y), width, height);
    }

    @Override public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        delegate.drawRoundRect(x, invert(y), width, height, arcWidth, arcHeight);
    }

    @Override public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        delegate.fillRoundRect(x, invert(y), width, height, arcWidth, arcHeight);
    }

    @Override public void drawOval(int x, int y, int width, int height) {
        delegate.drawOval(x, invert(y), width, height);
    }

    @Override public void fillOval(int x, int y, int width, int height) {
        delegate.fillOval(x, invert(y), width, height);
    }

    @Override public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        delegate.drawArc(x, invert(y), width, height, startAngle, arcAngle);
    }

    @Override public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        delegate.fillArc(x, invert(y), width, height, startAngle, arcAngle);
    }

    @Override public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
        throw new RuntimeException("Cannot draw a polyline inverted yet!");
        //delegate.drawPolyline(xPoints, yPoints, nPoints);
    }

    @Override public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        throw new RuntimeException("Cannot draw a polygon inverted yet!");
        //delegate.drawPolygon(xPoints, yPoints,  nPoints);
    }

    @Override public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        throw new RuntimeException("Cannot fill a polygon inverted yet!");
        //delegate.fillPolygon(xPoints, yPoints,  nPoints);
    }

    @Override public void translate(double tx, double ty) {
        delegate.translate(tx, invert(ty));
    }

    @Override public void rotate(double theta) {
        delegate.rotate(theta);
    }

    @Override public void rotate(double theta, double x, double y) {
        delegate.rotate(theta, x, invert(y));
    }

    @Override public void scale(double sx, double sy) {
        delegate.scale(sx, invert(sy));
    }

    @Override public void shear(double shx, double shy) {
        delegate.shear(shx, invert(shy));
    }

    @Override public void transform(AffineTransform Tx) {
        delegate.transform(Tx);
    }

    @Override public void setTransform(AffineTransform Tx) {
        delegate.setTransform(Tx);
    }

    @Override public AffineTransform getTransform() {
        return delegate.getTransform();
    }

    @Override public Paint getPaint() {
        return delegate.getPaint();
    }

    @Override public Composite getComposite() {
        return delegate.getComposite();
    }

    @Override public void setBackground(Color color) {
        delegate.setBackground(color);
    }

    @Override public Color getBackground() {
        return delegate.getBackground();
    }

    @Override public Stroke getStroke() {
        return delegate.getStroke();
    }

    @Override public void clip(Shape s) {
        delegate.clip(s);
    }

    @Override public FontRenderContext getFontRenderContext() {
        return delegate.getFontRenderContext();
    }
}